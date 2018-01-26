import {Component, OnDestroy, OnInit} from '@angular/core';
import {Market} from '../../entities/market/market.model';
import {AlertService, EventManager, JhiLanguageService} from 'ng-jhipster';
import {PriceListDTO} from './price-list-dto';
import {Variety} from '../../entities/variety/variety.model';
import {isUndefined} from 'ngx-bootstrap/bs-moment/utils/type-checks';
import {PriceList, PriceListType} from '../../entities/price-list/price-list.model';
import {SeasonsPriceListService} from './seasons-price-list.service';
import {PriceListService} from './price-list.service';
import {MarketSeason} from '../../entities/market-season/market-season.model';
import {ShippingPolicy} from '../../entities/shipping-policy/shipping-policy.model';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {PriceListTranslation} from './price-list-translation';
import {PriceListTranslationService} from './price-list-translation-service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {Subscription} from 'rxjs/Subscription';
import {MarketSeasonVarietyProperty} from '../../entities/market-season-variety-property/market-season-variety-property.model';
import {Observable} from 'rxjs/Observable';
import {isNull} from 'util';
import {SelectItem} from 'primeng/primeng';
import {Length} from '../../shared/constants/length.constants';

@Component({
    selector: 'jhi-flowers-seasons-price-list',
    templateUrl: './seasons-price-list.component.html',
    styleUrls: ['./seasons-price-list.scss'],
    providers: [PriceListService, SeasonsPriceListService, PriceListTranslationService]
})

export class SeasonsPriceListComponent implements OnInit, OnDestroy {

    priceListTranslation: PriceListTranslation;
    cols: any[];

    typeOfFlowers: Array<string> = [];
    fileFormats: SelectItem[];
    typeOfFlowersByCompany: TypeOfFlower[] = [];

    market: Market;
    markets: Market[];

    marketSeason: MarketSeason;
    marketSeasons: MarketSeason[];
    marketSeasonsAll: MarketSeason[];

    shippingPolicy: ShippingPolicy;
    shippingPolicies: ShippingPolicy[];

    variety: Variety;
    varieties: Variety[];

    marketSeasonVarietiesSelected: PriceListDTO[];
    marketSeasonVarietiesSelectedAll: PriceListDTO[];
    marketSeasonVarietiesSelectedTmp: PriceListDTO[];
    marketSeasonVarietyPropertyForSave: MarketSeasonVarietyProperty;

    isAllTypeOfFlowers = false;
    isAllButton = false;
    showHeaderRightBlock = false;
    validation = false;
    loadingRightBlock = false;
    showMarkets = false;
    showVariety = false;
    checkSelectedMarket = false;
    loadComponent = false;
    isDownloadingFile = false;
    extension: string;

    index = 0;
    marketId: number;
    typeOfFlowersId: number;

    dataForm: FormGroup;
    priceList: PriceList;
    season = 'SEASON';
    languageSubscriber: Subscription;

    private subscriptions: Subscription[] = [];

    constructor(private priceListService: PriceListService,
                private seasonsPriceListService: SeasonsPriceListService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private priceListTranslationService: PriceListTranslationService,
                private fb: FormBuilder) {
        this.languageHelper.addListener(this.priceListTranslationService);
    }

    ngOnInit() {
        this.marketSeasonVarietyPropertyForSave = new MarketSeasonVarietyProperty;
        this.marketSeasonVarietiesSelected = [];
        this.marketSeasonVarietiesSelectedAll = [];
        this.marketSeasonVarietiesSelectedTmp = [];
        this.marketSeasonsAll = [];
        this.markets = [];
        this.subscriptions.push(this.priceListService.getFileExtensions().subscribe((formats) => {
            this.fileFormats = formats;
            this.extension = this.fileFormats[0].value;
        }));
        this.subscriptions.push(this.seasonsPriceListService.getColumns().subscribe((columns) => {
            this.cols = columns;
        }));
        this.getCurrentLanguageInformation();
        this.createForm();
        this.loadAll();
        this.registerLangChange();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.languageSubscriber);
        this.subscriptions.forEach((subscription) => subscription.unsubscribe());
    }

    loadAll() {
        const fillShippingPolicy = this.seasonsPriceListService.getAllShippingPolicies();
        const fillMarketSeasons = this.seasonsPriceListService.queryMarketSeasons();
        const fillMarkets = this.seasonsPriceListService.queryMarkets();
        const fillAllMarketSeasonVarietyPropertyByType = this.seasonsPriceListService.getMarketSeasonVarietyPropertyByType(this.season);
        const fillPriceList = this.seasonsPriceListService.getPriceList(this.season);
        const fillTypeOfFlowers = this.seasonsPriceListService.findAllTypeOfFlowerByIdCompany();
        this.subscriptions.push(Observable.forkJoin(
            fillShippingPolicy,
            fillMarketSeasons,
            fillMarkets,
            fillAllMarketSeasonVarietyPropertyByType,
            fillPriceList,
            fillTypeOfFlowers
        ).subscribe((result: Array<any>) => {
            this.prepareShippingPolicy(result[0]);
            this.prepareMarketSeasons(result[1]);
            this.prepareMarketsResult(result[2].json);
            this.prepareMarketSeasonVarietyPropertiesResult(result[3]);
            this.preparePriceListResult(result[4]);
            this.prepareTypeOfFlowers(result[5]);

        }));
    }

    prepareShippingPolicy(shippingPolicies: ShippingPolicy[]) {
        this.shippingPolicies = shippingPolicies;
    }

    prepareMarketSeasons(marketSeasons: MarketSeason[]) {
        this.marketSeasonsAll = marketSeasons;
    }

    prepareMarketsResult(markets: Market[]) {
        this.markets = markets;
    }

    prepareMarketSeasonVarietyPropertiesResult(priceListDefaults: PriceListDTO[]) {
        this.marketSeasonVarietiesSelectedAll.push(...priceListDefaults);
    }

    preparePriceListResult(priceList: PriceList) {
        if (!this.isEmptyObject(priceList)) {
            this.priceList = priceList;
        }
    }

    prepareTypeOfFlowers(typeOfFlowers: TypeOfFlower[]) {
        typeOfFlowers.forEach((tof) => {
            this.typeOfFlowers.push(tof.name);
        });
        this.typeOfFlowersByCompany = typeOfFlowers;
        if (!isNull(this.typeOfFlowersByCompany)) {
            this.initFormValues();
            this.subscriptions.push(this.seasonsPriceListService.getVarietiesByTypeOfFlowers(this.typeOfFlowersByCompany[0].id).subscribe((res) => {
                this.typeOfFlowersId = this.typeOfFlowersByCompany[0].id;
                this.varieties = res;
            }));
        }

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
                this.subscriptions.push(this.seasonsPriceListService.getVarietiesByTypeOfFlowers(typeOfFlowers.id).subscribe((v) => {
                    this.typeOfFlowersId = typeOfFlowers.id;
                    this.varieties = v;
                    this.showVariety = true;
                    this.refreshMarketSeasonVarietyProperty();

                }));
            }
        });
    }

    selectShippingPolicy(shipping: ShippingPolicy) {
        if (!isUndefined(shipping)) {
            this.shippingPolicy = shipping;
            this.showVariety = false;
            this.showHeaderRightBlock = false;
            this.marketSeasonVarietiesSelected = [];
            this.marketSeasons = [];
            this.market = {};
            this.marketSeason = {};
            this.showMarkets = true;
            this.markets = this.markets.slice();
            this.checkSelectedMarket = false;
        }
    }

    selectMarket(market: Market) {
        if (!isUndefined(market)) {
            this.marketSeasonVarietiesSelected = [];
            this.marketId = market.id;
            this.marketSeasons = [];
            this.marketSeasonsAll.forEach((s) => {
                if (s.market.id === market.id) {
                    this.marketSeasons.push(s);
                }
            });
            this.marketSeason = {};
            this.marketSeasons = this.marketSeasons.slice();
            this.checkSelectedMarket = true;
            this.showHeaderRightBlock = false;
            this.showVariety = false;
        }
    }

    selectSeason(marketSeason: MarketSeason) {
        if (!isUndefined(marketSeason) && this.checkSelectedMarket) {
            this.showVariety = true;
            this.showHeaderRightBlock = true;
            this.refreshMarketSeasonVarietyProperty();
            this.activeAll();
        }

    }

    onSelect(variety: Variety) {
        let isRemovedVariety: boolean;
        const index: number = this.checkIndexForMarketVarietyProperty(this.marketSeasonVarietiesSelected, variety);
        const indexAll: number = this.checkIndexForMarketVarietyProperty(this.marketSeasonVarietiesSelectedAll, variety);

        if (index !== -1 && indexAll !== -1) {
            this.marketSeasonVarietiesSelectedAll.splice(indexAll, 1);
            this.marketSeasonVarietiesSelected.splice(index, 1);
            this.marketSeasonVarietiesSelected = this.marketSeasonVarietiesSelected.slice();
            isRemovedVariety = true;
            this.isAllButton = false;
        }
        if (!isRemovedVariety) {
            this.getMarketVarietyProperty(variety.id);
        }
    }

    filterMarketSeasonVarietyProperty() {
        this.marketSeasonVarietiesSelectedAll = this.marketSeasonVarietiesSelectedAll
            .filter((m) => m.shippingPolicy.id !== this.shippingPolicy.id
                || m.marketSeason.id !== this.marketSeason.id
                || m.variety.typeOfFlower.id !== this.typeOfFlowersId);
    }

    filterMarketSeasonVarietyPropertyTMP() {
        this.marketSeasonVarietiesSelectedTmp = this.marketSeasonVarietiesSelectedTmp
            .filter((m) => m.shippingPolicy.id !== this.shippingPolicy.id
                || m.marketSeason.id !== this.marketSeason.id
                || m.variety.typeOfFlower.id !== this.typeOfFlowersId);
    }

    useAllButton() {
        if (!this.isAllButton) {
            this.loadingRightBlock = true;
            this.showVariety = false;
            this.isAllButton = true;
            this.filterMarketSeasonVarietyProperty();
            this.filterMarketSeasonVarietyPropertyTMP();
            this.marketSeasonVarietiesSelectedTmp.push(...this.marketSeasonVarietiesSelected);
            this.marketSeasonVarietiesSelected = [];
            this.subscriptions.push(this.seasonsPriceListService.getMarketSeasonVarietyPropertyAll(this.marketSeason.id, this.season, this.typeOfFlowersId, this.shippingPolicy.id)
                .finally(() => {
                    this.loadingRightBlock = false;
                    this.showVariety = true;
                })
                .subscribe((res) => {
                    this.marketSeasonVarietiesSelected = res;
                    this.marketSeasonVarietiesSelectedAll.push(...res);
                    this.activeAll();
                }));
        }
    }

    useVarButton() {
        if (this.isAllButton) {
            this.filterMarketSeasonVarietyProperty();
            this.marketSeasonVarietiesSelected = [];
            this.isAllButton = false;
            this.marketSeasonVarietiesSelectedTmp.filter((msvp) => msvp.marketSeason.id === this.marketSeason.id
                && msvp.shippingPolicy.id === this.shippingPolicy.id
                && msvp.variety.typeOfFlower.id === this.typeOfFlowersId)
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
                this.subscriptions.push(this.priceListService.getPriceListFile(this.marketSeasonVarietiesSelectedAll, this.season, this.extension)
                    .subscribe((status) => this.isDownloadingFile = status));
            } else {
                this.subscriptions.push(this.seasonsPriceListService
                    .getMarketSeasonVarietyPropertyAll(this.marketSeason.id, this.season, this.typeOfFlowersId, this.shippingPolicy.id)
                    .subscribe((res) => {
                        this.subscriptions.push(this.priceListService.getPriceListFile(res, this.season, this.extension)
                            .subscribe((status) => this.isDownloadingFile = status));
                    }));
            }
        } else {
            const flowersPriceLists: PriceListDTO[] = [];
            this.subscriptions.push(Observable.forkJoin(
                ...this.typeOfFlowersByCompany.map((typeOfFlowers) =>
                    this.seasonsPriceListService.getMarketSeasonVarietyPropertyAll(this.marketSeason.id, this.season, typeOfFlowers.id, this.shippingPolicy.id))
            ).subscribe((result: Array<any>) => {
                result.forEach((priceLists) => flowersPriceLists.push(...priceLists));
                this.subscriptions.push(this.priceListService.getPriceListFile(flowersPriceLists, this.season, this.extension)
                    .subscribe((status) => this.isDownloadingFile = status));
            }));
        }
    }

    checkSelectedVarieties(variety: Variety) {
        for (let i = 0; i < this.marketSeasonVarietiesSelectedAll.length; i++) {
            if (this.marketSeasonVarietiesSelectedAll[i].marketSeason.id === this.marketSeason.id
                && this.marketSeasonVarietiesSelectedAll[i].shippingPolicy.id === this.shippingPolicy.id
                && this.marketSeasonVarietiesSelectedAll[i].variety.id === variety.id) {
                return true;
            }
        }
        return false;
    }

    save(item: PriceListDTO, event, i) {
        let isSave = true;
        if (isUndefined(this.priceList)) {
            isSave = false;
            this.priceList = new PriceList();
            this.priceList.type = PriceListType.SEASON;
            this.subscriptions.push(this.seasonsPriceListService.createPriceList(this.priceList).subscribe((res) => {
                this.priceList = res;
                this.updateAndSaveMarketSeasonVarietyProperty(item, event, i);
            }));
        }

        if (isSave) {
            this.updateAndSaveMarketSeasonVarietyProperty(item, event, i);
        }

    }

    updateAndSaveMarketSeasonVarietyProperty(item, event, i) {
        this.marketSeasonVarietyPropertyForSave.length = Length[Length[i]];
        this.marketSeasonVarietyPropertyForSave.shippingPolicy = item.shippingPolicy;
        this.marketSeasonVarietyPropertyForSave.price = event;
        this.marketSeasonVarietyPropertyForSave.marketSeason = item.marketSeason;
        this.marketSeasonVarietyPropertyForSave.variety = item.variety;
        this.marketSeasonVarietyPropertyForSave.priceList = this.priceList;
        this.subscriptions.push(this.seasonsPriceListService.updateMarketSeasonVarietyProperty(this.marketSeasonVarietyPropertyForSave).subscribe());
    }

    getMarketVarietyProperty(varietyId: number) {
        this.subscriptions.push(this.seasonsPriceListService.getMarketSeasonVarietyProperty(this.marketSeason.id, varietyId, this.shippingPolicy.id).subscribe((res) => {
            this.marketSeasonVarietiesSelectedAll.push(res);
            this.marketSeasonVarietiesSelected.unshift(res);
            this.marketSeasonVarietiesSelected = this.marketSeasonVarietiesSelected.slice();
            this.activeAll();
        }));
    }

    private activeAll() {
        this.isAllButton = this.marketSeasonVarietiesSelected.length === this.varieties.length;
    }

    private refreshMarketSeasonVarietyProperty() {
        this.marketSeasonVarietiesSelected = this.marketSeasonVarietiesSelectedAll
            .filter((m) => m.marketSeason.id === this.marketSeason.id
                && m.shippingPolicy.id === this.shippingPolicy.id
                && m.variety.typeOfFlower.id === this.typeOfFlowersId)
            .map((m) => m);
        this.activeAll();
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
                && priceListDefault[i].marketSeason.id === this.marketSeason.id
                && priceListDefault[i].shippingPolicy.id === this.shippingPolicy.id
                && priceListDefault[i].variety.typeOfFlower.id === this.typeOfFlowersId) {
                return i;
            }
        }
        return -1;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
