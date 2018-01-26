import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import {EventManager, AlertService, JhiLanguageService} from 'ng-jhipster';

import { PinchService } from './pinch.service';
import { Principal, ResponseWrapper } from '../../shared';
import {Pinch} from '../../entities/pinch/pinch.model';
import {PinchListTranslation} from './pinch-list-translation';
import {Market} from '../../entities/market/market.model';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import {PinchListDTO} from './pinch-list-dto';
import {Season} from '../../entities/season/season.model';
import {Variety} from '../../entities/variety/variety.model';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {TranslationService} from '../../shared/language/translation-service';
import {Observable} from 'rxjs/Observable';
import {isNull, isUndefined} from 'util';
import {
    Length,
    MarketSeasonVarietyProperty
} from '../../entities/market-season-variety-property/market-season-variety-property.model';

@Component({
    selector: 'jhi-pinch',
    templateUrl: './pinch.component.html'
})
export class PinchComponent implements OnInit, OnDestroy {

    cols: any[];
    pinchListTranslation: PinchListTranslation;

    typeOfFlowers: Array<string> = [];
    typeOfFlowersByCompany: TypeOfFlower[] = [];
    market: Market;
    markets: Market[];

    variety: Variety;
    varieties: Variety[];

    season: Season;
    seasons: Season[];

    marketSeasonVarietyPropertiesSelected: PinchListDTO[];
    marketSeasonVarietyPropertiesSelectedAll: PinchListDTO[];
    marketSeasonVarietyPropertiesSelectedTmp: PinchListDTO[];
    marketSeasonVarietyPropertyForSave: MarketSeasonVarietyProperty;

    isAllButton = false;
    showButtons = false;
    validation = false;
    showVariety = false;
    loadingRightBlock = false;
    showMarkets = false;

    index = 0;
    marketId: number;
    typeOfFlowersId: number;

    pinch: Pinch;
    dataForm: FormGroup;
    loadComponent = false;

    pinches: Pinch[];
    currentAccount: any;
    eventSubscriber: Subscription;
    languageSubscriber: Subscription;

    constructor(
        private pinchService: PinchService,
        private alertService: AlertService,
        private jhiLanguageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private eventManager: EventManager,
        private translationService: TranslationService,
        private principal: Principal,
        private fb: FormBuilder) {
        this.languageHelper.addListener(this.translationService);
        this.getCurrentLanguageInformation();
    }

    ngOnInit() {
        this.marketSeasonVarietyPropertyForSave = new MarketSeasonVarietyProperty;
        this.varieties = [];
        this.marketSeasonVarietyPropertiesSelected = [];
        this.marketSeasonVarietyPropertiesSelectedAll = [];
        this.marketSeasonVarietyPropertiesSelectedTmp = [];
        this.pinchService.getColumns().subscribe((columns) => {
            this.cols = columns;
        });
        this.loadAll();
        this.createForm();
        this.registerLangChange();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    loadAll() {
        const fillMarkets = this.pinchService.getMarketsByCurrentCompany();
        const fillAllMarketSeasonVarietyPropertyByType = this.pinchService.getMarketSeasonVarietyPropertiesByCompany();
        const fillPinch = this.pinchService.getPinchByCompany();
        const fillSeasons = this.pinchService.getAllSeasons();
        const fillTypeOfFlowers = this.pinchService.findAllTypeOfFlowerByIdCompany();
        Observable.forkJoin(
            fillMarkets,
            fillAllMarketSeasonVarietyPropertyByType,
            fillPinch,
            fillSeasons,
            fillTypeOfFlowers
        ).subscribe((result: Array<any>) => {
            this.prepareMarketsResult(result[0].json);
            this.prepareMarketSeasonVarietyPropertiesResult(result[1]);
            this.preparePinchResult(result[2]);
            this.prepareSeasonsResult(result[3]);
            this.prepareTypeOfFlowers(result[4]);
        });
    }

    prepareMarketsResult(markets: Market[]) {
        this.markets = markets;
    }

    prepareMarketSeasonVarietyPropertiesResult(pinchList: PinchListDTO[]) {
        this.marketSeasonVarietyPropertiesSelectedAll.push(...pinchList);
    }

    preparePinchResult(pinch: Pinch) {
        if (!this.isEmptyObject(pinch)) {
            this.pinch = pinch;
        }
    }

    prepareSeasonsResult(seasons: Season[]) {
        this.seasons = seasons;
    }

    prepareTypeOfFlowers(typeOfFlowers: TypeOfFlower[]) {
        typeOfFlowers.forEach((typeOfFlower) => {
            this.typeOfFlowers.push(typeOfFlower.name);
        });
        this.typeOfFlowersByCompany = typeOfFlowers;
        if (!isNull(this.typeOfFlowersByCompany)) {
            this.initFormValues();
            this.pinchService.getVarietiesByTypeOfFlowers(this.typeOfFlowersByCompany[0].id).subscribe((res) => {
                this.typeOfFlowersId = this.typeOfFlowersByCompany[0].id;
                this.varieties = res;
            });
        }
    }

    createForm() {
        this.dataForm = this.fb.group({
            typeOfFlowers: new FormControl()
        });
    }

    registerLangChange() {
        this.languageSubscriber = this.eventManager.subscribe('languageChanged', (response) => {
            this.getCurrentLanguageInformation();
        });
    }

    trackId(index: number, item: Pinch) {
        return item.id;
    }
    registerChangeInPinches() {
        this.eventSubscriber = this.eventManager.subscribe('pinchListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
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

    public getCurrentLanguageInformation() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.translationService.getTranslation(currentLang, 'pinchList').subscribe((res) => {
                this.translationService = res;
                this.loadComponent = true;
            });
        });
    }

    private initFormValues() {
        this.dataForm.get('typeOfFlowers').setValue([{
            id: this.typeOfFlowersByCompany[0].name,
            text: this.typeOfFlowersByCompany[0].name
        }]);
    }

    public selectedTypeOfFlower(value: any): void {
        this.typeOfFlowersByCompany.forEach((typeOfFlowers) => {
            if (typeOfFlowers.name === value.text) {
                this.pinchService.getVarietiesByTypeOfFlowers(typeOfFlowers.id).subscribe((v) => {
                    this.typeOfFlowersId = typeOfFlowers.id;
                    this.varieties = v;
                    this.showVariety = true;
                    this.refreshMarketSeasonVarietyProperty();

                });
            }
        });
    }

    selectSeason(season: Season) {
        if (!isUndefined(season)) {
            this.season = season;
            this.showButtons = false;
            this.showVariety = false;
            this.marketSeasonVarietyPropertiesSelected = [];
            this.market = {};
            this.showMarkets = true;
        }
    }

    selectMarket(market: Market) {
        if (!this.isEmptyObject(market)) {
            this.showButtons = true;
            this.showVariety = true;
            this.marketId = market.id;
            this.refreshMarketSeasonVarietyProperty();
        }
    }

    selectVariety(variety: Variety) {
        let isRemovedVariety: boolean;
        const index: number = this.checkIndexForMarketSeasonVarietyProperty(this.marketSeasonVarietyPropertiesSelected, variety);
        const indexAll: number = this.checkIndexForMarketSeasonVarietyProperty(this.marketSeasonVarietyPropertiesSelectedAll, variety);

        if (index !== -1 && indexAll !== -1) {
            this.marketSeasonVarietyPropertiesSelected.splice(index, 1);
            this.marketSeasonVarietyPropertiesSelectedAll.splice(indexAll, 1);
            this.marketSeasonVarietyPropertiesSelected = this.marketSeasonVarietyPropertiesSelected.slice();
            isRemovedVariety = true;
            this.isAllButton = false;
        }
        if (!isRemovedVariety) {
            this.getMarketSeasonVarietyProperty(variety.id);
        }
    }

    //TODO USE ONLY ONE METHOD
    filterMarketSeasonVarietyProperty() {
        this.marketSeasonVarietyPropertiesSelectedAll = this.marketSeasonVarietyPropertiesSelectedAll
            .filter((m) => m.season.id !== this.season.id
            || m.market.id !== this.marketId
            || m.variety.typeOfFlower.id !== this.typeOfFlowersId);
    }

    filterMarketSeasonVarietyPropertyTMP() {
        this.marketSeasonVarietyPropertiesSelectedTmp = this.marketSeasonVarietyPropertiesSelectedTmp
            .filter((m) => m.season.id !== this.season.id
            || m.market.id !== this.marketId
            || m.variety.typeOfFlower.id !== this.typeOfFlowersId);
    }

    useAllButton() {
        if (!this.isAllButton) {
            this.loadingRightBlock = true;
            this.showVariety = false;
            this.isAllButton = true;
            this.filterMarketSeasonVarietyProperty();
            this.filterMarketSeasonVarietyPropertyTMP();
            this.marketSeasonVarietyPropertiesSelectedTmp.push(...this.marketSeasonVarietyPropertiesSelected);
            this.marketSeasonVarietyPropertiesSelected = [];
            this.pinchService.getMarketSeasonVarietyPropertyAll(this.marketId, this.typeOfFlowersId, this.season.id)
                .subscribe((res) => {
                    this.marketSeasonVarietyPropertiesSelectedAll.push(...res);
                    this.marketSeasonVarietyPropertiesSelected = res;
                    this.loadingRightBlock = false;
                    this.showVariety = true;
                });
        }
    }

    useVarButton() {
        if (this.isAllButton) {
            this.filterMarketSeasonVarietyProperty();
            this.marketSeasonVarietyPropertiesSelected = [];
            this.isAllButton = false;
            this.marketSeasonVarietyPropertiesSelectedTmp.filter((mvp) => mvp.market.id === this.marketId
            && mvp.season.id === this.season.id
            && mvp.variety.typeOfFlower.id === this.typeOfFlowersId)
                .forEach((m) => {
                    this.getMarketSeasonVarietyProperty(m.variety.id);
                });
        }
    }

    refreshMarketSeasonVarietyProperty() {
        this.marketSeasonVarietyPropertiesSelected = this.marketSeasonVarietyPropertiesSelectedAll
            .filter((m) => m.market.id === this.marketId && m.season.id === this.season.id
            && m.variety.typeOfFlower.id === this.typeOfFlowersId)
            .map((m) => m);
        this.isAllButton = this.marketSeasonVarietyPropertiesSelected.length === this.varieties.length;
    }

    checkSelectedVarieties(variety: Variety) {
        for (let i = 0; i < this.marketSeasonVarietyPropertiesSelectedAll.length; i++) {
            if (this.marketSeasonVarietyPropertiesSelectedAll[i].variety.id === variety.id
                && this.marketSeasonVarietyPropertiesSelectedAll[i].market.id === this.marketId
                && this.marketSeasonVarietyPropertiesSelectedAll[i].season.id === this.season.id
                && this.marketSeasonVarietyPropertiesSelectedAll[i].variety.typeOfFlower.id === this.typeOfFlowersId) {
                return true;
            }
        }
        return false;
    }

    save(item: PinchListDTO, event, cellIndex) {
        let isSave = true;

        if (isUndefined(this.pinch)) {
            isSave = false;
            this.pinch = new Pinch();
            this.pinchService.createPinch(this.pinch).subscribe((res) => {
                this.pinch = res;
                this.updateAndSaveMarketVarietyProperty(item, event, cellIndex);
            });
        }
        if (isSave) {
            this.updateAndSaveMarketVarietyProperty(item, event, cellIndex);
        }
    }

    updateAndSaveMarketVarietyProperty(item, event, cellIndex) {
        this.marketSeasonVarietyPropertyForSave.length = Length[Length[cellIndex]];
        this.marketSeasonVarietyPropertyForSave.price = event;
        this.marketSeasonVarietyPropertyForSave.variety = item.variety;
        this.marketSeasonVarietyPropertyForSave.marketSeason.market = item.market;
        this.marketSeasonVarietyPropertyForSave.marketSeason.season = item.season;
        this.marketSeasonVarietyPropertyForSave.pinch = this.pinch;
        this.pinchService.updateMarketSeasonVarietyProperty(this.marketSeasonVarietyPropertyForSave).subscribe();
    }

    getMarketSeasonVarietyProperty(varietyId: number) {
        this.pinchService.getMarketSeasonVarietyProperty(this.marketId, varietyId, this.season.id).subscribe((res) => {
            this.marketSeasonVarietyPropertiesSelectedAll.push(res);
            this.marketSeasonVarietyPropertiesSelected.unshift(res);
            this.marketSeasonVarietyPropertiesSelected = this.marketSeasonVarietyPropertiesSelected.slice();
            this.isAllButton = this.marketSeasonVarietyPropertiesSelected.length === this.varieties.length;
        });
    }

    private checkIndexForMarketSeasonVarietyProperty(pinchList: PinchListDTO[], variety: Variety): number {
        for (let i = 0; i < pinchList.length; i++) {
            if (pinchList[i].variety.id === variety.id
                && pinchList[i].season.id === this.season.id
                && pinchList[i].market.id === this.marketId
                && pinchList[i].variety.typeOfFlower.id === this.typeOfFlowersId) {
                return i;
            }
        }
        return -1;
    }
}
