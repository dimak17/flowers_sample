import {Component, OnDestroy, OnInit} from '@angular/core';
import {PriceListService} from './price-list.service';
import {Market} from '../../entities/market/market.model';
import {PriceListDTO} from './price-list-dto';
import {Variety} from '../../entities/variety/variety.model';
import {isUndefined} from 'ngx-bootstrap/bs-moment/utils/type-checks';
import {PriceList, PriceListType} from '../../entities/price-list/price-list.model';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {isNull} from 'util';
import {ShippingPolicy} from '../../entities/shipping-policy/shipping-policy.model';
import {PriceListTranslationService} from './price-list-translation-service';
import {PriceListTranslation} from './price-list-translation';
import {Subscription} from 'rxjs/Subscription';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {Observable} from 'rxjs/Observable';
import {MarketVarietyProperty} from '../../entities/market-variety-property/market-variety-property.model';
import {SelectItem} from 'primeng/primeng';
import {Length} from '../../shared/constants/length.constants';

@Component({
    selector: 'jhi-flowers-default-price-list',
    templateUrl: './default-price-list.component.html',
    styleUrls: ['./price-list.scss'],
    providers: [PriceListService, PriceListTranslationService]
})

export class DefaultPriceListComponent implements OnInit, OnDestroy {

    cols: any[];
    priceListTranslation: PriceListTranslation;

    typeOfFlowers: Array<string> = [];
    fileFormats: SelectItem[];
    typeOfFlowersByCompany: TypeOfFlower[] = [];
    market: Market;
    markets: Market[];

    variety: Variety;
    varieties: Variety[];

    shippingPolicies: ShippingPolicy[];
    shippingPolicy: ShippingPolicy;

    marketVarietyPropertiesSelected: PriceListDTO[];
    marketVarietyPropertiesSelectedAll: PriceListDTO[];
    marketVarietyPropertiesSelectedTmp: PriceListDTO[];
    marketVarietyPropertiesSelectedTmp2: PriceListDTO[];
    marketVarietyPropertyForSave: MarketVarietyProperty;

    isAllTypeOfFlowers = false;
    isAllButton = false;
    showButtons = false;
    validation = false;
    showVariety = false;
    loadingRightBlock = false;
    showMarkets = false;
    isDownloadingFile = false;
    extension: string;

    index = 0;
    marketId: number;
    typeOfFlowersId: number;

    priceList: PriceList;
    default = 'DEFAULT';
    dataForm: FormGroup;
    loadComponent = false;

    languageSubscriber: Subscription;

    private subscriptions: Subscription[] = [];

    constructor(private priceListService: PriceListService,
                private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private priceListTranslationService: PriceListTranslationService,
                private fb: FormBuilder) {
        this.languageHelper.addListener(this.priceListTranslationService);
        this.getCurrentLanguageInformation();

    }

    ngOnInit() {
        this.marketVarietyPropertyForSave = new MarketVarietyProperty;
        this.varieties = [];
        this.marketVarietyPropertiesSelected = [];
        this.marketVarietyPropertiesSelectedAll = [];
        this.marketVarietyPropertiesSelectedTmp = [];
        this.marketVarietyPropertiesSelectedTmp2 = [];
        this.subscriptions.push(this.priceListService.getFileExtensions().subscribe((formats) => {
            this.fileFormats = formats;
            this.extension = this.fileFormats[0].value;
        }));
        this.subscriptions.push(this.priceListService.getColumns().subscribe((columns) => {
            this.cols = columns;
        }));
        this.loadAll();
        this.createForm();
        this.registerLangChange();

    }

    ngOnDestroy() {
        this.eventManager.destroy(this.languageSubscriber);
        this.subscriptions.forEach((subscription) => subscription.unsubscribe());
    }

    public getCurrentLanguageInformation() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.subscriptions.push(this.priceListTranslationService.getTranslation(currentLang, 'priceList').subscribe((res) => {
                this.priceListTranslation = res;
                this.loadComponent = true;
            }));
        });
    }

    registerLangChange() {
        this.languageSubscriber = this.eventManager.subscribe('languageChangedPriceList', () => {
            this.getCurrentLanguageInformation();
        });
    }

    loadAll() {
        const fillMarkets = this.priceListService.getMarketsByCurrentCompany();
        const fillAllMarketVarietyPropertyByType = this.priceListService.getMarketVarietyPropertiesByType(this.default);
        const fillPriceList = this.priceListService.getPriceList(this.default);
        const fillShippingPolicy = this.priceListService.getAllShippingPolicies();
        const fillTypeOfFlowers = this.priceListService.findAllTypeOfFlowerByIdCompany();
        this.subscriptions.push(Observable.forkJoin(
            fillMarkets,
            fillAllMarketVarietyPropertyByType,
            fillPriceList,
            fillShippingPolicy,
            fillTypeOfFlowers
        ).subscribe((result: Array<any>) => {
            this.prepareMarketsResult(result[0].json);
            this.prepareMarketVarietyPropertiesResult(result[1]);
            this.preparePriceListResult(result[2]);
            this.prepareShippingPoliciesResult(result[3]);
            this.prepareTypeOfFlowers(result[4]);

        }));

    }

    prepareMarketsResult(markets: Market[]) {
        this.markets = markets;
    }

    prepareMarketVarietyPropertiesResult(priceList: PriceListDTO[]) {
        this.marketVarietyPropertiesSelectedAll.push(...priceList);
    }

    preparePriceListResult(priceList: PriceList) {
        if (!this.isEmptyObject(priceList)) {
            this.priceList = priceList;
        }
    }

    prepareShippingPoliciesResult(shippingPolicies: ShippingPolicy[]) {
        this.shippingPolicies = shippingPolicies;

    }

    prepareTypeOfFlowers(typeOfFlowers: TypeOfFlower[]) {
        typeOfFlowers.forEach((typeOfFlower) => {
            this.typeOfFlowers.push(typeOfFlower.name);
        });
        this.typeOfFlowersByCompany = typeOfFlowers;
        if (!isNull(this.typeOfFlowersByCompany)) {
            this.initFormValues();
            this.subscriptions.push(this.priceListService.getVarietiesByTypeOfFlowers(this.typeOfFlowersByCompany[0].id).subscribe((res) => {
                this.typeOfFlowersId = this.typeOfFlowersByCompany[0].id;
                this.varieties = res;
            }));
        }
    }

    createForm() {
        this.dataForm = this.fb.group({
            typeOfFlowers: new FormControl()
        });
    }

    isEmptyObject(obj) {
        return (obj && (Object.keys(obj).length === 0));
    }

    openNext() {
        this.index = (this.index === 1) ? 0 : this.index + 1;
    }

    openPrev() {
        this.index = (this.index === 0) ? 1 : this.index - 1;
    }

    sizeScroll(): any {
        if (this.index === 0) {
            return '300px';
        }
        return '700px';
    }

    public selectedTypeOfFlower(value: any): void {
        this.typeOfFlowersByCompany.forEach((typeOfFlowers) => {
            if (typeOfFlowers.name === value.text) {
                this.subscriptions.push(this.priceListService.getVarietiesByTypeOfFlowers(typeOfFlowers.id).subscribe((v) => {
                    this.typeOfFlowersId = typeOfFlowers.id;
                    this.varieties = v;
                    this.showVariety = true;
                    this.refreshMarketVarietyProperty();

                }));
            }
        });
    }

    selectShippingPolicy(shipping: ShippingPolicy) {
        if (!isUndefined(shipping)) {
            this.shippingPolicy = shipping;
            this.showButtons = false;
            this.showVariety = false;
            this.marketVarietyPropertiesSelected = [];
            this.market = {};
            this.showMarkets = true;
        }
    }

    selectMarket(market: Market) {
        if (!this.isEmptyObject(market)) {
            this.showButtons = true;
            this.showVariety = true;
            this.marketId = market.id;
            this.refreshMarketVarietyProperty();
        }
    }

    selectVariety(variety: Variety) {
        let isRemovedVariety: boolean;
        const index: number = this.checkIndexForMarketVarietyProperty(this.marketVarietyPropertiesSelected, variety);
        const indexAll: number = this.checkIndexForMarketVarietyProperty(this.marketVarietyPropertiesSelectedAll, variety);

        if (index !== -1 && indexAll !== -1) {
            this.marketVarietyPropertiesSelected.splice(index, 1);
            this.marketVarietyPropertiesSelectedAll.splice(indexAll, 1);
            this.marketVarietyPropertiesSelected = this.marketVarietyPropertiesSelected.slice();
            isRemovedVariety = true;
            this.isAllButton = false;
        }
        if (!isRemovedVariety) {
            this.getMarketVarietyProperty(variety.id);
        }
    }

    filterMarketVarietyProperty() {
        this.marketVarietyPropertiesSelectedAll = this.marketVarietyPropertiesSelectedAll
            .filter((m) => m.shippingPolicy.id !== this.shippingPolicy.id
                || m.market.id !== this.marketId
                || m.variety.typeOfFlower.id !== this.typeOfFlowersId);
    }

    filterMarketVarietyPropertyTMP() {
        this.marketVarietyPropertiesSelectedTmp = this.marketVarietyPropertiesSelectedTmp
            .filter((m) => m.shippingPolicy.id !== this.shippingPolicy.id
                || m.market.id !== this.marketId
                || m.variety.typeOfFlower.id !== this.typeOfFlowersId);
    }

    useAllButton() {
        if (!this.isAllButton) {
            this.loadingRightBlock = true;
            this.showVariety = false;
            this.isAllButton = true;
            this.filterMarketVarietyProperty();
            this.filterMarketVarietyPropertyTMP();
            this.marketVarietyPropertiesSelectedTmp.push(...this.marketVarietyPropertiesSelected);
            this.marketVarietyPropertiesSelected = [];
            this.subscriptions.push(this.priceListService.getMarketVarietyPropertyAll(this.marketId, this.typeOfFlowersId, this.shippingPolicy.id, this.default)
                .subscribe((res) => {
                    this.marketVarietyPropertiesSelectedAll.push(...res);
                    this.marketVarietyPropertiesSelected = res;
                    this.loadingRightBlock = false;
                    this.showVariety = true;
                }));
        }
    }

    useVarButton() {
        if (this.isAllButton) {
            this.filterMarketVarietyProperty();
            this.marketVarietyPropertiesSelected = [];
            this.isAllButton = false;
            this.marketVarietyPropertiesSelectedTmp.filter((mvp) => mvp.market.id === this.marketId
                && mvp.shippingPolicy.id === this.shippingPolicy.id
                && mvp.variety.typeOfFlower.id === this.typeOfFlowersId)
                .forEach((m) => {
                    this.getMarketVarietyProperty(m.variety.id);
                });
        }
    }

    downloadFile() {
        this.isDownloadingFile = true;
        this.getPriceLists();
    }

    getPriceLists() {
        if (!this.isAllTypeOfFlowers) {
            if (this.isAllButton) {
                this.subscriptions.push(this.priceListService.getPriceListFile(this.marketVarietyPropertiesSelectedAll, this.default, this.extension)
                    .subscribe((status) => this.isDownloadingFile = status));
            } else {
                this.subscriptions.push(this.priceListService.getMarketVarietyPropertyAll(this.marketId, this.typeOfFlowersId, this.shippingPolicy.id, this.default)
                    .subscribe((res) => {
                        this.subscriptions.push(this.priceListService.getPriceListFile(res, this.default, this.extension)
                            .subscribe((status) => this.isDownloadingFile = status));
                    }));
            }
        } else {
            const flowersPriceLists: PriceListDTO[] = [];
            this.subscriptions.push(Observable.forkJoin(
                ...this.typeOfFlowersByCompany.map((typeOfFlowers) =>
                    this.priceListService.getMarketVarietyPropertyAll(this.marketId, typeOfFlowers.id, this.shippingPolicy.id, this.default))
            ).subscribe((result: Array<any>) => {
                result.forEach((priceLists) => flowersPriceLists.push(...priceLists));
                this.subscriptions.push(this.priceListService.getPriceListFile(flowersPriceLists, this.default, this.extension)
                    .subscribe((status) => this.isDownloadingFile = status));
            }));
        }
    }

    refreshMarketVarietyProperty() {
        this.marketVarietyPropertiesSelected = this.marketVarietyPropertiesSelectedAll
            .filter((m) => m.market.id === this.marketId && m.shippingPolicy.id === this.shippingPolicy.id
                && m.variety.typeOfFlower.id === this.typeOfFlowersId)
            .map((m) => m);
        this.isAllButton = this.marketVarietyPropertiesSelected.length === this.varieties.length;
    }

    checkSelectedVarieties(variety: Variety) {
        for (let i = 0; i < this.marketVarietyPropertiesSelectedAll.length; i++) {
            if (this.marketVarietyPropertiesSelectedAll[i].variety.id === variety.id
                && this.marketVarietyPropertiesSelectedAll[i].market.id === this.marketId
                && this.marketVarietyPropertiesSelectedAll[i].shippingPolicy.id === this.shippingPolicy.id
                && this.marketVarietyPropertiesSelectedAll[i].variety.typeOfFlower.id === this.typeOfFlowersId) {
                return true;
            }
        }
        return false;
    }

    save(item: PriceListDTO, event, cellIndex) {
        let isSave = true;

        if (isUndefined(this.priceList)) {
            isSave = false;
            this.priceList = new PriceList();
            this.priceList.type = PriceListType.DEFAULT;
            this.subscriptions.push(this.priceListService.createPriceList(this.priceList).subscribe((res) => {
                this.priceList = res;
                this.updateAndSaveMarketVarietyProperty(item, event, cellIndex);
            }));
        }

        if (isSave) {
            this.updateAndSaveMarketVarietyProperty(item, event, cellIndex);
        }
    }

    updateAndSaveMarketVarietyProperty(item, event, cellIndex) {
        this.marketVarietyPropertyForSave.length = Length[Length[cellIndex]];
        this.marketVarietyPropertyForSave.price = event;
        this.marketVarietyPropertyForSave.variety = item.variety;
        this.marketVarietyPropertyForSave.market = item.market;
        this.marketVarietyPropertyForSave.shippingPolicy = item.shippingPolicy;
        this.marketVarietyPropertyForSave.priceList = this.priceList;
        this.subscriptions.push(this.priceListService.updateMarketVarietyProperty(this.marketVarietyPropertyForSave).subscribe());
    }

    getMarketVarietyProperty(varietyId: number) {
        this.subscriptions.push(this.priceListService.getMarketVarietyProperty(this.marketId, varietyId, this.shippingPolicy.id, this.default).subscribe((res) => {
            this.marketVarietyPropertiesSelectedAll.push(res);
            this.marketVarietyPropertiesSelected.unshift(res);
            this.marketVarietyPropertiesSelected = this.marketVarietyPropertiesSelected.slice();
            this.isAllButton = this.marketVarietyPropertiesSelected.length === this.varieties.length;
        }));
    }

    private initFormValues() {
        this.dataForm.get('typeOfFlowers').setValue([{
            id: this.typeOfFlowersByCompany[0].name,
            text: this.typeOfFlowersByCompany[0].name
        }]);
    }

    private checkIndexForMarketVarietyProperty(priceListDefault: PriceListDTO[], variety: Variety): number {
        for (let i = 0; i < priceListDefault.length; i++) {
            if (priceListDefault[i].variety.id === variety.id
                && priceListDefault[i].shippingPolicy.id === this.shippingPolicy.id
                && priceListDefault[i].market.id === this.marketId
                && priceListDefault[i].variety.typeOfFlower.id === this.typeOfFlowersId) {
                return i;
            }
        }
        return -1;
    }
}
