import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, Renderer2} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager, JhiLanguageService} from 'ng-jhipster';

import {MarketUi} from './market-ui.model';
import {MarketPopupService} from './market-popup.service';
import {MarketService} from './market.service';
import {LATIN_VALIDATION} from '../../shared';
import {SelectItem} from 'primeng/primeng';
import {Variety} from '../../entities/variety/variety.model';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import {isUndefined} from 'util';
import {MarketVariety, MarketVarietyType} from '../../entities/market-variety/market-variety.model';
import {Market} from '../../entities/market/market.model';
import * as _ from 'lodash';
import * as Collections from 'typescript-collections';
import {BoxType} from '../../entities/box-type/box-type.model';
import {BoxGroup} from '../../entities/box-group/box-group.model';
import {BoxSizeUnit, MarketBox} from '../../entities/market-box/market-box.model';
import {
    MarketBoxVarietyPropertyDto,
    MarketBoxVarietyPropertyDtoResponse
} from '../../shared/dto/market-box-variety-property-dto.model';
import {Length} from '../../shared/constants/length.constants';
import {BoxTypeGroup} from '../../entities/box-type-group/box-type-group.model';
import {Subscription} from 'rxjs/Subscription';

@Component({
    selector: 'jhi-market-dialog',
    templateUrl: './market-dialog.component.html',
    styleUrls: ['./market-dialog.component.scss'],
    providers: [MarketService]
})
export class MarketDialogComponent implements OnInit, OnDestroy, AfterViewInit {
    market: Market;

    isSaving: boolean;
    errorAlert = false;

    boxGroups: BoxGroup[];

    boxGroupsSelected: BoxGroup[];

    boxGroupsItems: SelectItem[];
    boxGroupsSelectedItems: number[];
    boxGroupsSelectedItemsRight: SelectItem[];
    varietyTypes: SelectItem[];
    selectedVarietyType = 0;
    typeOfFlowerNames: Array<string>;
    selectedVarieties: Variety[][][] = [];
    chosenVarieties: Variety[][][] = [];
    varieties: Variety[];
    typeOfFlowers: TypeOfFlower[] = [];
    marketVarieties: MarketVariety[];
    selectedTypeOfFlowerVal: number;
    activeIndex = 0;
    selectAllValues: boolean[][][] = [];
    selectAllValuesPending = false;
    checkBoxCheckAllLabel: string;
    createLabel: string;
    editLabel: string;
    boxTableParamNamesColumn: string;
    boxTableValueColumn: string;
    tabLabelVarieties: string;
    tabLabelBoxGroups: string;
    tabLabelBoxes: string;
    uniqueBoxes: BoxType[];
    uniqueBoxesItems: SelectItem[];
    uniqueBoxesItemSelected: number;
    boxTypeSelected: BoxType;
    lengthMaskPattern = '9?999';
    capacityPropertyMaskPattern = '9?99';
    propertiesMeasuresItems: SelectItem[];
    propertiesMeasuresItemSelected: Collections.Dictionary<number, string>;
    sizes: Collections.Dictionary<number, Sizable[]>;
    SPECIAL = MarketVarietyType.SPECIAL;
    PROHIBITED = MarketVarietyType.PROHIBITED;
    static TABS_COUNT = 3;
    private readonly prohibitedIndex = 1;
    private readonly specialIndex = 0;
    private defaultSizeUnit: number;
    lengths: number[];
    lengthsLabels: string[];
    marketBoxVarietyPropertyDtosSelected: MarketBoxVarietyPropertyDto[][];
    typeOfFlowersItems: SelectItem[];
    private boxesVarieties: Variety[];
    private readonly boxesTabIndex = 2;
    private marketBoxVarietyPropertyDtos: MarketBoxVarietyPropertyDto[][];
    private subscriptions: Subscription[] = [];
    private prohibitedVarietiesLabel: string;
    private specialVarietiesLabel: string;
    private duplicateMarketName: string;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketService: MarketService,
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private eRef: ElementRef,
        private rd: Renderer2,
    ) {
        (<any>Array.prototype).groupByTwo = function(prop, propInternal) {
            return this.reduce(function(groups, item) {
                const val = item[prop][propInternal];
                groups[val] = groups[val] || [];
                groups[val].push(item);
                return groups;
            }, {});
        };
        (<any>Array.prototype).groupByOne = function(prop) {
            return this.reduce(function(groups, item) {
                const val = item[prop];
                groups[val] = groups[val] || [];
                groups[val].push(item);
                return groups;
            }, {});
        };
        this.varietyTypes = [
            {label: ' Special varieties', value: this.specialIndex},
            {label: ' Prohibited varieties', value: this.prohibitedIndex}
        ];

        this.selectedVarietyType = 0;
        this.jhiLanguageService.setLocations(['market']);
        //TODO translations
        this.sizes = new Collections.Dictionary<number, Sizable[]>();
        const boxSizeKeys = Object.keys(BoxSizeUnit);
        this.propertiesMeasuresItems = [];
        boxSizeKeys.filter((key) => !isNaN(Number(BoxSizeUnit[key])))
            .forEach((label) => {
                this.propertiesMeasuresItems.push({label, value: ''});
            });
        boxSizeKeys.filter((key) => isNaN(Number(BoxSizeUnit[key])))
            .forEach((value, i) => {
                this.propertiesMeasuresItems[i].value = value;
            });
        this.propertiesMeasuresItemSelected = new Collections.Dictionary<number, string>();
        this.defaultSizeUnit = BoxSizeUnit.CM;
        this.lengths = Object.keys(Length)
            .filter((key) => !isNaN(Number(key))).map((key) => Number(key));
        this.lengthsLabels = Object.keys(Length)
            .filter((key) => isNaN(Number(key))).map((s) => s.substr(1));
    }
    ngOnInit() {
        this.isSaving = false;
        this.initArray(this.chosenVarieties);
        this.initArray(this.selectedVarieties);
        this.fillVarieties();
    }

    ngAfterViewInit(): void {
        const selectBtn = this.eRef.nativeElement.querySelectorAll( '.variety-type-select-button .ui-button-text');
        const specialVarietiesText = selectBtn[0];
        const prohibitedVarieties = selectBtn[1];
        this.rd.addClass(specialVarietiesText, 'fa-exclamation');
        this.rd.addClass(specialVarietiesText, 'fa');
        this.rd.addClass(prohibitedVarieties, 'fa-thumbs-o-down');
        this.rd.addClass(prohibitedVarieties, 'fa');
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach((s) => s.unsubscribe());
    }

    private initArray<T>(arr: T[][][]) {
        for (let i = 0; i < MarketDialogComponent.TABS_COUNT; i++) {
            arr[i] = [];
            arr[i][0] = [];
            if (i === 0) {
                arr[i][1] = [];
            }
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        // tab 1
        this.isSaving = true;
        const varietiesToSave: Variety[] = [];
        this.market.marketVarieties = [];

        this.selectedVarieties
            .filter((vArray, i) => i === 0 && vArray && vArray.length && vArray[0].length)
            .forEach((vArray) => varietiesToSave.push(...vArray));

        varietiesToSave.forEach((vArray: Variety[], typeIndex) => {
            vArray.forEach((v) => {
                const marketVariety = new MarketVariety();
                marketVariety.variety = v;
                marketVariety.type = typeIndex ? MarketVarietyType.PROHIBITED : MarketVarietyType.SPECIAL;
                this.market.marketVarieties.push(marketVariety);
            });
        });

        // tab 2
        if (this.boxGroupsSelectedItems) {
            this.market.boxGroups = this.boxGroupsSelectedItems
                .map((groupId) => this.boxGroups
                    .filter((bg) => bg.id == groupId)[0]);
            this.market.boxGroups.forEach((bg) => bg.boxTypeGroups = null);
        }
        // tab 3
        const marketBoxes: MarketBox[] = [];
        this.sizes.forEach((boxTypeId, sizes) => {
            const mb = new MarketBox();
            mb.unit = BoxSizeUnit[BoxSizeUnit[sizes[0].measure]];
            mb.length = Number(sizes[0].value);
            mb.width = Number(sizes[1].value);
            mb.height = Number(sizes[2].value);
            mb.boxType = this.uniqueBoxes.filter((b) => b.id === boxTypeId)[0];
            marketBoxes.push(mb);
        });

        if (this.market.marketBoxes && this.market.marketBoxes.length) {
            this.market.marketBoxes = this.market.marketBoxes.map((mb) => {
                const mbNew = marketBoxes.filter((mbNew) => mbNew.boxType.id === mb.boxType.id)[0];
                mbNew.id = mb.id;
                return mbNew;
            });
        } else {
            this.market.marketBoxes = marketBoxes;
        }
        if (this.market.id) {
            this.subscribeToSaveResponse(
                this.marketService.update(this.market), false);
        } else {
            this.subscribeToSaveResponse(
                this.marketService.create(this.market), true);
        }
    }

    mapToObject(map) {
        const out = Object.create(null);
        map.forEach((value, key) => {
            if (value instanceof Map) {
                out[key] = this.mapToObject(value);
            }
            else {
                out[key] = value;
            }
        });
        return out;
    }

    private subscribeToSaveResponse(result: Observable<MarketUi>, isCreated: boolean) {
        const sub = result.subscribe((res: MarketUi) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
        this.subscriptions.push(sub);
    }

    private onSaveSuccess(result: MarketUi, isCreated: boolean) {
        this.market.marketBoxes = result.marketBoxes;
        this.savePropTable(result, result.marketBoxes);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        if (error.headers.get('x-flowersapp-error') == 'error.DuplicateName') {
            this.errorAlert = true;
            this.duplicateMarketName = this.market.name;
        } else {
            this.alertService.error(error.message, null, null);
        }
    }

    latinValid(marketName: string): boolean {
        return marketName && this.fieldLengthValid(marketName) ?
                !!marketName.match(LATIN_VALIDATION)
                : true;
    }

    fieldLengthValid(marketName: string): boolean {
        return marketName ? marketName.length < 17 : true;
    }

    onSelectVarietyType(value: number) {
        this.selectedVarietyType = value;
        this.chosenVarieties[this.activeIndex][this.getTypeIndex()] = this.varieties
            .filter((v) => v.typeOfFlower.id == this.selectedTypeOfFlowerVal);
    }

    saveButtonDeactivation(market: MarketUi) : boolean {
        return !this.latinValid(market.name) || !this.fieldLengthValid(market.name);
    }

    onSelectAllVarieties(isAllSelected: boolean) {
        const typeIndex = this.getTypeIndex();
        this.selectAllValuesPending = true;
        this.selectAllValues[this.activeIndex][typeIndex][this.getTypeOfFlowersIndex()] = isAllSelected;
        setTimeout(isAllSelected
            ? () => {
                const sub = Observable.of(_.cloneDeep(this.chosenVarieties[this.activeIndex][this.getTypeIndex()]))
                    .subscribe((cloned: Variety[]) => {
                        this.selectedVarieties[this.activeIndex][typeIndex].push(...cloned);
                        this.boxesTabOnSelectVariety();
                    }, (error) => {
                        this.selectAllValuesPending = false;
                        console.error(error.message);
                    }, () => {
                        this.selectAllValuesPending = false;
                    });
                this.subscriptions.push(sub);
            }
            : () => {
                const sub = Observable.of(this.selectedVarieties[this.activeIndex][typeIndex]
                    .filter((v) => v.typeOfFlower.id !== this.selectedTypeOfFlowerVal))
                    .subscribe((filtered: Variety[]) => {
                        this.selectedVarieties[this.activeIndex][typeIndex] = filtered;
                        this.boxesTabOnSelectVariety();
                    }, (error) => {
                        this.selectAllValuesPending = false;
                        console.error(error.message);
                    }, () => {
                        this.selectAllValuesPending = false;
                    });
                this.subscriptions.push(sub);
            }, 500);
    }

    onSelectVariety(variety: Variety) {
        let isRemovedVariety: boolean;
        const typeIndex = this.getTypeIndex();
        if (!this.selectedVarieties[this.activeIndex][typeIndex]) {
            this.selectedVarieties[this.activeIndex][typeIndex] = [];
        }
        const index: number = this.selectedVarieties[this.activeIndex][typeIndex]
            .map((v) => v.id).indexOf(variety.id);
        if (index !== -1) {
            this.selectedVarieties[this.activeIndex][typeIndex].splice(index, 1);
            isRemovedVariety = true;
        }
        if (!isRemovedVariety) {
            this.selectedVarieties[this.activeIndex][typeIndex].push(variety);
        }

        this.boxesTabOnSelectVariety();
    }

    public onSelectTypeOfFlower(value: any) {
        this.selectedTypeOfFlowerVal = value;
        const typeIndex = this.getTypeIndex();
        for (let i = 0; i < this.chosenVarieties[this.activeIndex][typeIndex].length; i++) {
            for (let y = 0; y < this.selectedVarieties[this.activeIndex][typeIndex].length; y++) {
                if (this.chosenVarieties[this.activeIndex][typeIndex][i].id
                    === this.selectedVarieties[this.activeIndex][typeIndex][y].id) {
                    break;
                }
            }
        }

        this.fillVarietiesByTypeOfFlower(value, false);
    }

    private fillVarietiesByTypeOfFlower(selectedId, isInitial: boolean) {
        const typeIndex = this.getTypeIndex();
        if (!isUndefined(selectedId)){
            if (isInitial) {
                for (let tabIndex = 0; tabIndex < MarketDialogComponent.TABS_COUNT; tabIndex++) {
                    this.chosenVarieties[tabIndex][this.specialIndex] = this.varieties
                        .filter((v) => v.typeOfFlower.id === selectedId);
                    this.chosenVarieties[tabIndex][this.specialIndex] = this.varieties
                        .filter((v) => v.typeOfFlower.id === selectedId);
                }
            } else {
                this.chosenVarieties[this.activeIndex][typeIndex] = this.varieties
                    .filter((v) => v.typeOfFlower.id === selectedId);
            }
        }
    }

    private fillVarieties() {
        if (this.market.id) {
            const fillVarieties = this.marketService.findVarietiesFromCurrentCompany();
            const fillMarketVarieties = this.marketService.findMarketVarietyByMarketId(this.market.id);
            const fillTypeOfFlowers = this.marketService.findAllTypeOfFlowerCurrentCompany();
            const fillBoxGroupings = this.marketService.findBoxGroupsByCurrentCompany();
            const fillTranslations = this.observeTranslations();
            const sub = Observable.forkJoin(
                fillVarieties,
                fillMarketVarieties,
                fillTypeOfFlowers,
                fillBoxGroupings,
                fillTranslations
            ).subscribe((result: Array<any>) => {
                //TODO error handling
                this.prepareVarietiesResult(result[0]);
                this.prepareMarketVarietyResult(result[1]);
                this.prepareTypeOfFlowersResult(result[2]);
                this.prepareBoxGroupingResult(result[3]);
                this.prepareTranslationsResult(result[4]);
            });
            this.subscriptions.push(sub);
        } else {
            const fillVarieties = this.marketService.findVarietiesFromCurrentCompany();
            const fillTypeOfFlowers = this.marketService.findAllTypeOfFlowerCurrentCompany();
            const fillBoxGroupings = this.marketService.findBoxGroupsByCurrentCompany();
            const fillTranslations = this.observeTranslations();
            const sub = Observable.forkJoin(
                fillVarieties,
                fillTypeOfFlowers,
                fillBoxGroupings,
                fillTranslations
            ).subscribe((result: Array<any>) => {
                //TODO error handling
                this.prepareVarietiesResult(result[0]);
                this.prepareTypeOfFlowersResult(result[1]);
                this.prepareBoxGroupingResult(result[2]);
                this.prepareTranslationsResult(result[3]);
            });
            this.subscriptions.push(sub);
        }
    }

    private fillTypeOfFlowerNames() {
        this.typeOfFlowerNames = this.typeOfFlowers.map((t: TypeOfFlower) => t.name);
        this.typeOfFlowersItems = this.typeOfFlowers.map((t) => <SelectItem>{label: t.name, value: t.id});

        //TODO refact this
        this.selectedTypeOfFlowerVal = this.typeOfFlowersItems[0].value;
        for (let i = 0; i < MarketDialogComponent.TABS_COUNT; i++) {
            this.selectAllValues[i] = [];
            this.typeOfFlowersItems.forEach((t, j) => {
                this.selectAllValues[i][j] = [];
            });
        }
        this.fillVarietiesByTypeOfFlower(this.typeOfFlowersItems[0].value, true);
    }

    getSelectedVarietiesTitles() {
        const typeIndex = this.getTypeIndex();
        return this.chosenVarieties[this.activeIndex][typeIndex]
            && this.chosenVarieties[this.activeIndex][typeIndex].length > 0
            && this.selectedVarieties[this.activeIndex][typeIndex].length > 0
                ? this.chosenVarieties[this.activeIndex][typeIndex]
                    .filter((v) => this.selectedVarieties[this.activeIndex][typeIndex].map((selV) => selV.id).indexOf(v.id) !== -1)
                    .map((v) => v.name)
                    .reduce((a, b) => a + ', ' + b, '')
                    .substr(2)
                : '';
    }

    getSelectedStyleClass(variety: Variety) {
        const typeIndex = this.getTypeIndex();
        return this.selectedVarieties[this.activeIndex][typeIndex]
            .map((v) => v.id).indexOf(variety.id) !== -1 ? 'square-selected' : '';
    }

    private observeTranslations() {
        return (<any>this.jhiLanguageService).translateService.get([
            'flowersApp.market.home.createLabel',
            'flowersApp.market.home.editLabel',
            'flowersApp.market.checkAll',
            'flowersApp.market.box-table-param-col-header',
            'flowersApp.market.box-table-value-col-header',
            'flowersApp.market.tabs.boxes',
            'flowersApp.market.tabs.box-groups',
            'flowersApp.market.tabs.varieties',
            'flowersApp.market.selectbtn.prohibited',
            'flowersApp.market.selectbtn.special',
        ]);
    }

    private prepareTranslationsResult(translations) {
        this.checkBoxCheckAllLabel = translations['flowersApp.market.checkAll'];
        this.createLabel = translations['flowersApp.market.home.createLabel'];
        this.editLabel = translations['flowersApp.market.home.editLabel'];
        this.boxTableParamNamesColumn = translations['flowersApp.market.box-table-param-col-header'];
        this.boxTableValueColumn = translations['flowersApp.market.box-table-value-col-header'];
        this.tabLabelBoxes = translations['flowersApp.market.tabs.boxes'];
        this.tabLabelBoxGroups = translations['flowersApp.market.tabs.box-groups'];
        this.tabLabelVarieties = translations['flowersApp.market.tabs.varieties'];
        this.specialVarietiesLabel = translations['flowersApp.market.selectbtn.special'];
        this.prohibitedVarietiesLabel = translations['flowersApp.market.selectbtn.prohibited'];
        this.varietyTypes[0].label = ' ' + this.specialVarietiesLabel;
        this.varietyTypes[1].label = ' ' +  this.prohibitedVarietiesLabel;
    }

    getTypeOfFlowersIndex(): number {
        return this.typeOfFlowersItems
            .map((i) => i.value)
            .indexOf(this.selectedTypeOfFlowerVal);
    }

    getTypeIndex(): number {
        return this.activeIndex ? this.getSelectedBoxIndex() : this.selectedVarietyType;
    }

    getSelectedBoxGroups() {
        return this.boxGroupsSelectedItems
            .map((id) => this.boxGroupsItems.filter((item) => id === item.value))
            .filter((f) => f.length != 0)
            .map((a) => a[0]);
    }

    onSelectBoxGroup(value) {
        const init = !this.boxGroupsSelectedItems || !this.boxGroupsSelectedItemsRight;
        if (init) {
            this.boxGroupsSelectedItemsRight = [];
            this.boxGroupsSelectedItemsRight.push(...this.boxGroupsItems
                .filter((item) => this.boxGroupsSelectedItems.indexOf(item.value) !== -1)
            );
            this.boxGroupsSelected = this.boxGroups
                .filter((b) => this.boxGroupsSelectedItems.indexOf(b.id) !== -1);
            const newBoxTypeIds = this.prepareBoxes(true);
            this.initPropTableElems(newBoxTypeIds);
        } else {
            const isAdd = this.boxGroupsSelectedItems.length > this.boxGroupsSelectedItemsRight.length;
            if (isAdd) {
                const newElem = this.boxGroupsItems
                    .filter((bgi) =>  this.boxGroupsSelectedItems
                        .filter((item) => this.boxGroupsSelectedItemsRight
                            .map((selItem) => selItem.value)
                            .indexOf(item) === -1)
                        .indexOf(bgi.value) !== -1
                    );
                if (newElem) {
                    this.boxGroupsSelectedItemsRight.unshift(...newElem);
                }
                this.boxGroupsSelected = this.boxGroups
                    .filter((b) => this.boxGroupsSelectedItems.indexOf(b.id) !== -1);
                const newBoxTypeIds = this.prepareBoxes(true, newElem[0].value);
                this.addPropTableElems(newBoxTypeIds);
            } else {
                const remGroupId = this.boxGroupsSelectedItemsRight
                    .filter((rItem) => this.boxGroupsSelectedItems.indexOf(rItem.value) === -1)[0].value;
                this.boxGroupsSelectedItemsRight = this.boxGroupsSelectedItemsRight
                    .filter(
                        (selected) => this.boxGroupsSelectedItemsRight
                            .filter((item) => this.boxGroupsSelectedItems.indexOf(item.value) === -1)
                            .indexOf(selected.value) !== -1
                    );
                this.boxGroupsSelected = this.boxGroups
                    .filter((b) => this.boxGroupsSelectedItems.indexOf(b.id) !== -1);
                const idsToRem = this.prepareBoxes(false, remGroupId);
                this.removePropTableElems(idsToRem);
            }
        }
    }

    onTabChange(e) {
        this.activeIndex = e.index;
    }

    onCloseGroup(id: number) {
        this.boxGroupsSelectedItems = this.boxGroupsSelectedItems
            .filter((selectedId) => selectedId !== id);
        this.boxGroupsSelectedItemsRight = this.boxGroupsSelectedItemsRight
            .filter((selectedItem) => selectedItem.value !== id);
    }

    trackByFn(index, item) {
        return index;
    }

    trackById(index, item) {
        return item.id;
    }

    trackByValue(index, item) {
        return item.value;
    }

    private prepareBoxGroupingResult(boxGroups: BoxGroup[]) {
        this.boxGroups = boxGroups;
        this.boxGroupsItems = this.boxGroups
            .map((b) => {
                return {
                    label: Array.from(<Array<BoxTypeGroup>>b.boxTypeGroups)
                        .map((btg) => btg.boxType.shortName)
                        .reduce((a, b) => a + '/' + b, '')
                        .substr(1),
                    value: b.id
                };
            });
        this.boxGroupsSelected = this.market.boxGroups;
        if (this.boxGroupsSelected && this.boxGroupsSelected.length) {
            this.prepareBoxes(true);
            this.marketService.findMarketBoxVarietyPropertiesByMarket(this.market.id)
                .subscribe((res) => {
                    const propsByBoxId = (<any>res).groupByTwo('marketBox', 'id');
                    Object.keys(propsByBoxId)
                        .forEach((key) => {
                            const respGrouped = propsByBoxId[key];
                            const marketBox = this.market.marketBoxes.filter((mb) => mb.id === Number(key))[0];
                            const boxIndex = this.uniqueBoxesItems.map((u) => u.value).indexOf(marketBox.boxType.id);
                            if (!this.marketBoxVarietyPropertyDtosSelected) {
                                this.marketBoxVarietyPropertyDtosSelected = [];
                            }
                            this.marketBoxVarietyPropertyDtosSelected[boxIndex] = respGrouped
                                .map((r: MarketBoxVarietyPropertyDtoResponse) => new MarketBoxVarietyPropertyDto(r))
                                .reduce((total, amount) => total.concat(amount), []);
                            const dtosAdditional = this.createDto(marketBox, this.varieties);
                            this.marketBoxVarietyPropertyDtos = [];
                            this.marketBoxVarietyPropertyDtos[boxIndex] = [
                                ...dtosAdditional,
                                ...this.marketBoxVarietyPropertyDtosSelected[boxIndex]
                            ];
                            //fill dummy data for table value bindings
                            this.boxesVarieties = this.marketBoxVarietyPropertyDtosSelected[boxIndex]
                                .map((dto) => dto.variety)
                                .filter((v, i, arr) => arr.map((innerV) => innerV.id).indexOf(v.id) === i);
                            this.selectedVarieties[this.boxesTabIndex][boxIndex] = this.boxesVarieties;
                            this.marketBoxVarietyPropertyDtosSelected[boxIndex]
                                .forEach((dto, dtoIndex) => this.lengths
                                    .filter((length) => Array.from(dto.capacitiesOnLength.keys())
                                        .map((el) => Number(el[0]))
                                        .indexOf(length) === -1)
                                    .forEach((length) => this.marketBoxVarietyPropertyDtosSelected[boxIndex][dtoIndex]
                                        .capacitiesOnLength.set([Length[Length[length]], 0], 0)));
                        });
                });
        }
    }

    private initPropTable() {
        this.initDtos();
        const selectedBoxIndex = this.getSelectedBoxIndex();
        if (!this.marketBoxVarietyPropertyDtos[selectedBoxIndex]
            || !this.marketBoxVarietyPropertyDtos[selectedBoxIndex].length) {
            const marketBoxes: MarketBox[] = [];
            this.sizes.forEach((boxTypeId, sizes) => {
                const mb = new MarketBox();
                mb.boxType = this.uniqueBoxes.filter((b) => b.id === boxTypeId)[0];
                if (mb.boxType) {
                    mb.unit = BoxSizeUnit[BoxSizeUnit[sizes[0].measure]];
                    mb.length = Number(sizes[0].value);
                    mb.width = Number(sizes[1].value);
                    mb.height = Number(sizes[2].value);
                    marketBoxes.push(mb);
                }
            });
            const selectedMarketBox = marketBoxes
                .filter((mb) => mb.boxType.id == this.boxTypeSelected.id)[0];
            const varietiesAdditional = this.varieties
                .filter((v) => this.marketBoxVarietyPropertyDtosSelected[selectedBoxIndex]
                .map((dtoV) => dtoV.variety.id).indexOf(v.id) === -1);
            const dtosAdditional = this.createDto(selectedMarketBox, varietiesAdditional);
            this.marketBoxVarietyPropertyDtos[selectedBoxIndex] = [
                ...dtosAdditional,
                ...this.marketBoxVarietyPropertyDtosSelected[selectedBoxIndex]
            ];
        }
    }

    private prepareBoxes(isAdd: boolean, newElemId?: number): number[] {
        if (this.boxGroupsSelected && this.boxGroupsSelected.length) {
            this.boxGroupsSelectedItems = this.boxGroupsSelected.map((b) => b.id);
            this.boxGroupsSelectedItemsRight = this.getSelectedBoxGroups();
            const boxTypes = this.boxGroupsSelected
                .map((bgs) => bgs.boxTypeGroups)
                .map((boxGroup) => (<Array<any>>boxGroup).map((d) => d.boxType))
                .reduce((total, amount) => total.concat(amount));
            const boxTypesIds = boxTypes.map((b) => b.id).filter((id, i, arr) => arr.indexOf(id) === i);
            if (isAdd) {
                return this.addFlowBoxesPreparing(boxTypesIds, boxTypes, newElemId);
            } else {
                return this.removeFlowBoxPreparing(boxTypesIds, newElemId);
            }
        } else {
            this.lastBoxGroupDeselected();
        }
        return null;
    }

    private prepareMarketVarietyResult(marketVarieties: MarketVariety[]) {
        this.marketVarieties = marketVarieties;
        this.marketVarieties.forEach((mv) => {
            if (mv.type.toString() == MarketVarietyType[this.SPECIAL]) {
                this.selectedVarieties[0][this.specialIndex].push(mv.variety);
            }
            if (mv.type.toString() == MarketVarietyType[this.PROHIBITED]) {
                this.selectedVarieties[0][this.prohibitedIndex].push(mv.variety);
            }
        });
    }

    private prepareTypeOfFlowersResult(typeOfFlowers: TypeOfFlower[]) {
        this.typeOfFlowers = typeOfFlowers;
        this.fillTypeOfFlowerNames();
    }

    private prepareVarietiesResult(varieties: Variety[]) {
        this.varieties = varieties;
    }

    onSelectBox(event) {
        this.boxTypeSelected = this.uniqueBoxes.filter((u) => u.id === this.uniqueBoxesItemSelected)[0];
        this.selectedTypeOfFlowerVal = this.typeOfFlowersItems[0].value;
    }

    getSelectedBoxIndex(): number {
        return this.uniqueBoxesItems
            ? this.uniqueBoxesItems.map((u) => u.value)
                .indexOf(this.uniqueBoxesItemSelected)
            : 0;
    }

    onMeasureChoose(event, sizeables: Sizable[]) {
        sizeables.forEach((s) => s.measure = event.value);
    }

    getPropertyTableCellEditorName(property, col) {
        return property.variety.name + Array.from(property.capacitiesOnLength.keys())[col];
    }

    getKey(property, col) {
        const keys = Array.from(property.capacitiesOnLength.keys());
        const indexOfKey = keys.map((key) => key[0]).indexOf(col);
        return keys[indexOfKey];
    }

    changePropertyTableInputMaskModel(event, property, col) {
        const capacity: number = parseInt(event.replace(/_/gi, ''), 10);
        this.getKey(property, col)[1] = capacity;
        // sync selected with marketBoxVarietyPropertyDtos
        const boxIndex = this.getSelectedBoxIndex();
        this.getKey(
            this.marketBoxVarietyPropertyDtos[boxIndex]
                .filter((dto) => dto.variety.id === property.variety.id)[0], col
        )[1] = capacity;
    }

    changeBoxesSizeInputMaskModel(event, size) {
        size.value = parseInt(event.replace(/_/gi, ''), 10);
    }

    private fillBoxSizes(newBoxTypeIds: number[]) {
        if (this.market.marketBoxes && this.market.marketBoxes.length) {
            if (newBoxTypeIds) {
                newBoxTypeIds.filter((id) => this.uniqueBoxesItems.map((u) => u.value).indexOf(id) === -1)
                    .map((id) => this.market.marketBoxes.filter((mb) => mb.boxType.id === id)[0])
                    .forEach((mb) => {
                        const boxSize: Sizable[] = this.createSizeables(mb);
                        this.sizes.setValue(mb.boxType.id, boxSize);
                    });
            } else {
                this.market.marketBoxes.forEach((mb) => {
                    const boxSize: Sizable[] = this.createSizeables(mb);
                    this.sizes.setValue(mb.boxType.id, boxSize);
                });
            }
        } else {
            this.uniqueBoxes.map((ub) => ub.id).forEach((id) => {
                this.sizes.setValue(id, this.createSizeables());
            });
        }
    }

    private createSizeables(mb?: MarketBox): Sizable[] {
        return [
            {
                name: 'flowersApp.market.length-label',
                value: mb && !isUndefined(mb.length) ? mb.length.toString(10) : '0',
                measure: mb && !isUndefined(mb.unit) ? Number(BoxSizeUnit[mb.unit]) : this.defaultSizeUnit
            },
            {
                name: 'flowersApp.market.width-label',
                value: mb && !isUndefined(mb.width) ? mb.width.toString(10) : '0',
                measure: mb && !isUndefined(mb.unit) ? Number(BoxSizeUnit[mb.unit]) : this.defaultSizeUnit
            },
            {
                name: 'flowersApp.market.height-label',
                value: mb && !isUndefined(mb.height) ? mb.height.toString(10) : '0',
                measure: mb && !isUndefined(mb.unit) ? Number(BoxSizeUnit[mb.unit]) : this.defaultSizeUnit
            },
        ];
    }

    private savePropTable(result: MarketUi, marketBoxes: MarketBox[]) {
        if (this.marketBoxVarietyPropertyDtosSelected && this.marketBoxVarietyPropertyDtosSelected.length) {
            this.marketBoxVarietyPropertyDtosSelected.forEach((dtoItem, dtoIndex) => {
                const market = Object.assign({}, result);
                market.boxGroups = null;
                dtoItem.forEach((dto) => {
                        dto.capacitiesOnLengthObj = this.mapToObject(dto.capacitiesOnLength);
                        dto.marketBox = marketBoxes[dtoIndex];
                        dto.market = market;
                    });
                //TODO a lot of requests fix it
                const sub = this.marketService.updateMarketBoxVarietyProperties(dtoItem)
                    .subscribe((res) => {
                        this.successHandler(result);
                    });
                this.subscriptions.push(sub);
            });
        } else {
            this.successHandler(result);
        }
    }

    private successHandler(result: MarketUi) {
        this.errorAlert = false;
        this.duplicateMarketName = null;
        this.eventManager.broadcast({ name: 'changeMarketEvent', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private initMarketBoxes(isAdd: boolean, newBoxTypeIds: number[]) {
        if (!this.market.marketBoxes || !this.market.marketBoxes.length) {
            this.uniqueBoxesItems.forEach( (u) => {
                const newMb = this.buildMarketBox(u);
                if (!this.market.marketBoxes) this.market.marketBoxes = [];
                this.market.marketBoxes.push(newMb);
            });
        } else if (this.market.marketBoxes.length !== this.uniqueBoxesItems.length) {
            if (isAdd) {
                const newUniqueBox = this.uniqueBoxesItems
                    .filter((u) => this.market.marketBoxes.map((mb) => mb.boxType.id).indexOf(u.value) === -1)[0];
                this.market.marketBoxes.push(this.buildMarketBox(newUniqueBox));
            } else {
                const mbToRem = this.market.marketBoxes
                    .filter((mb) => this.uniqueBoxesItems.map((u) => u.value).indexOf(mb.boxType.id) === -1)[0];
                const indexToRem = this.market.marketBoxes.map((mb) => mb.boxType.id).indexOf(mbToRem.boxType.id);
                this.market.marketBoxes.splice(indexToRem, 1);
            }
        } else {
            this.fillBoxSizes(newBoxTypeIds);
        }
    }

    private buildMarketBox(u: SelectItem): MarketBox {
        const newMb = new MarketBox();
        const newBox = new BoxType();
        newBox.id = u.value;
        newMb.boxType = newBox;
        const sizables: Sizable[] = this.createSizeables(newMb);
        this.sizes.setValue(newMb.boxType.id, sizables);

        const sizes = this.sizes.getValue(newBox.id);

        sizes.forEach((s) => {
            if (s.name === 'flowersApp.market.width-label') {
                newMb.width = Number(s.value);
            } else if (s.name === 'flowersApp.market.height-label') {
                newMb.height = Number(s.value);
            } else if (s.name === 'flowersApp.market.length-label') {
                newMb.length = Number(s.value);
            }
            newMb.unit = s.measure;
        });
        return newMb;
    }

    private boxesTabOnSelectVariety() {
        if (this.activeIndex === this.boxesTabIndex) {
            this.initPropTable();
            const selectedIndex = this.uniqueBoxesItems.map((u) => u.value).indexOf(this.boxTypeSelected.id);
            this.marketBoxVarietyPropertyDtosSelected[selectedIndex] = _.cloneDeep(this.selectedVarieties[this.activeIndex][selectedIndex])
                .reverse()
                .map((v) => this.marketBoxVarietyPropertyDtos[selectedIndex].filter((dto) => dto.variety.id === v.id)[0]);
        }
    }

    isNameValid(): boolean {
        return !this.market.name || !this.latinValid(this.market.name)
            || !this.fieldLengthValid(this.market.name) ||
            (this.errorAlert && this.duplicateMarketName && this.duplicateMarketName === this.market.name);
    }

    private initSelectAllValues() {
        this.uniqueBoxes.forEach((u, i) => {
            this.selectAllValues[this.boxesTabIndex][i] = [];
        });
    }

    private initDtos() {
        if (!this.marketBoxVarietyPropertyDtosSelected && !this.marketBoxVarietyPropertyDtos) {
            this.marketBoxVarietyPropertyDtos = [];
            this.marketBoxVarietyPropertyDtosSelected = [];
            this.uniqueBoxesItems.forEach((u, i) => {
                this.marketBoxVarietyPropertyDtos[i] = [];
                this.marketBoxVarietyPropertyDtosSelected[i] = [];
            });
        } else if (this.uniqueBoxesItems.length !== this.marketBoxVarietyPropertyDtos.length) {
            const selectedIndex = this.uniqueBoxesItems.map((u) => u.value).indexOf(this.boxTypeSelected.id);
            const marketBox = this.market.marketBoxes[selectedIndex];
            const boxIndex = this.uniqueBoxesItems.map((u) => u.value).indexOf(marketBox.boxType.id);
            if (!this.marketBoxVarietyPropertyDtosSelected[selectedIndex]) {
                this.marketBoxVarietyPropertyDtosSelected[selectedIndex] = [];
            }
            const dtosAdditional = this.createDto(marketBox, this.varieties);
            this.marketBoxVarietyPropertyDtos = [];
            this.marketBoxVarietyPropertyDtos[boxIndex] = [
                ...dtosAdditional
            ];
        }
    }

    private addPropTableElems(newBoxTypeIds: number[]) {
        newBoxTypeIds
            .filter((nbti) => this.uniqueBoxesItems.map((u) => u.value).indexOf(nbti) === -1)
            .map((nbti) => this.uniqueBoxesItems.map((u) => u.value).indexOf(nbti))
            .forEach((i) => {
                this.marketBoxVarietyPropertyDtosSelected[i] = [];
                this.marketBoxVarietyPropertyDtos[i] = this.createDto(this.market.marketBoxes[i], this.varieties);
            });
    }

    private createDto(marketBox: MarketBox, varieties: Variety[]): MarketBoxVarietyPropertyDto[] {
        const capacitiesOnLength = this.lengths
            .reduce((map, length) => {
                map.set([Length[Length[length]], 0], 0);
                return map;
            }, new Map<[Length, number], number>());
        return varieties
            .map((v) => {
                const dto = new MarketBoxVarietyPropertyDto();
                dto.variety = v;
                dto.capacitiesOnLength = _.cloneDeep(capacitiesOnLength);
                dto.marketBox = marketBox;
                return dto;
            });
    }

    private initPropTableElems(newBoxTypeIds: number[]) {
        if (!this.marketBoxVarietyPropertyDtosSelected && !this.marketBoxVarietyPropertyDtos) {
            this.marketBoxVarietyPropertyDtos = [];
            this.marketBoxVarietyPropertyDtosSelected = [];
            this.uniqueBoxesItems.forEach((u, i) => {
                this.marketBoxVarietyPropertyDtos[i] = [];
                this.marketBoxVarietyPropertyDtosSelected[i] = [];
            });
        }

        this.addPropTableElems(newBoxTypeIds);
    }

    private calculateUniqueBoxes(boxTypesIds: number[], boxTypes: BoxType[]) {
        if (!this.uniqueBoxes || !this.uniqueBoxes.length) {
            this.uniqueBoxes = [];
            const u = boxTypes.filter((b, pos) => boxTypesIds.indexOf(b.id) !== -1
                && boxTypes.map((innerB) => innerB.id).indexOf(b.id) === pos);
            if (u.length) {
                this.uniqueBoxes.push(...u);
            }
        } else {
            const u = boxTypesIds.filter((id) => this.uniqueBoxes.map((u) => u.id).indexOf(id) === -1)
                .map((newId) => boxTypes.filter((b) => b.id === newId)[0]);
            if (u.length) {
                this.uniqueBoxes.push(...u);
            }
        }
        this.uniqueBoxesItems = this.uniqueBoxes.map((u) => <SelectItem>{label: u.shortName, value: u.id});
        this.uniqueBoxesItems.forEach((u, i) => this.selectAllValues[this.activeIndex][i] = []);
        this.uniqueBoxesItemSelected = this.uniqueBoxes[0].id;
        this.boxTypeSelected = this.uniqueBoxes[0];
    }

    removePropTableElems(indexesToRem: number[]) {
        if (indexesToRem) {
            indexesToRem.forEach((i) => {
                this.marketBoxVarietyPropertyDtos.splice(i, 1);
                this.marketBoxVarietyPropertyDtosSelected.splice(i, 1);
            });
        }
        else if (!this.boxGroupsSelectedItems.length) {
            this.marketBoxVarietyPropertyDtos = [];
            this.marketBoxVarietyPropertyDtosSelected = [];
        }
    }

    private addFlowBoxesPreparing(boxTypesIds: number[], boxTypes: BoxType[], newElemId: number) {
        let newBoxTypesIds;
        const isInited = this.uniqueBoxesItems && this.uniqueBoxesItems.length;
        if (isInited) {
            const newGroup = this.boxGroupsSelected.filter((b) => b.id === newElemId);
            if (newGroup && newGroup.length) {
                newBoxTypesIds = this.boxGroupsSelected.filter((b) => b.id === newElemId)
                    .map((bgs) => bgs.boxTypeGroups)
                    .map((boxGroup) => (<Array<any>>boxGroup).map((d) => d.boxType))
                    .reduce((total, amount) => total.concat(amount))
                    .map((b) => b.id)
                    .filter((id, i, arr) => arr.indexOf(id) === i);
            }
            if (newGroup && newGroup.length && newBoxTypesIds && newBoxTypesIds.length) {
                newBoxTypesIds.filter((id) => this.uniqueBoxesItems.map((u) => u.value).indexOf(id) === -1)
                    .map((nbti) => this.uniqueBoxesItems.map((u) => u.value).indexOf(nbti)).forEach((i) => {
                    if (i < 0) {
                        this.selectedVarieties[this.boxesTabIndex].push([]);
                        this.chosenVarieties[this.boxesTabIndex].push(
                            this.varieties
                                .filter((v) => v.typeOfFlower.id === this.selectedTypeOfFlowerVal)
                        );
                    } else {
                        this.selectedVarieties[this.boxesTabIndex][i] = [];
                        this.chosenVarieties[this.boxesTabIndex][i] = this.varieties
                            .filter((v) => v.typeOfFlower.id === this.selectedTypeOfFlowerVal);
                    }
                });
            }
            this.calculateUniqueBoxes(newBoxTypesIds, boxTypes);
        } else {
            this.calculateUniqueBoxes(boxTypesIds, boxTypes);
            if (isUndefined(newElemId)
                || !this.chosenVarieties[this.boxesTabIndex]
                || !this.chosenVarieties[this.boxesTabIndex].length) {
                this.uniqueBoxesItems.forEach((u, i) => {
                    this.selectedVarieties[this.boxesTabIndex][i] = [];
                    this.chosenVarieties[this.boxesTabIndex][i] = this.varieties
                        .filter((v) => v.typeOfFlower.id === this.selectedTypeOfFlowerVal);
                });
                newBoxTypesIds = this.uniqueBoxes.map((u) => u.id);
            }
        }
        this.initMarketBoxes(true,  !isInited ? null : newBoxTypesIds);
        this.initSelectAllValues();
        return newBoxTypesIds;
    }

    private lastBoxGroupDeselected() {
        this.uniqueBoxes = [];
        this.uniqueBoxesItems = [];
        this.selectAllValues[this.boxesTabIndex] = [];
        this.uniqueBoxesItemSelected = null;
        this.selectedVarieties[this.boxesTabIndex] = [];
        this.chosenVarieties[this.boxesTabIndex] = [];
        this.market.marketBoxes = [];
    }

    private removeFlowBoxPreparing(boxTypesIds: number[], newElemId: number): number[] {
        const newGroup = this.boxGroups.filter((b) => b.id === newElemId)[0];
        const boxTypesToRemIds = (<Array<BoxType>>(newGroup.boxTypeGroups))
            .map((boxGroup) => (<any>boxGroup).boxType.id);
        if (boxTypesToRemIds.filter((id) => boxTypesIds.indexOf(id) !== -1).length !== boxTypesToRemIds.length) {
            const idsToRem = boxTypesToRemIds.filter((id) => boxTypesIds.indexOf(id) === -1);
            const indexesToRem = idsToRem.map((id) => this.uniqueBoxes.map((u) => u.id).indexOf(id));
            indexesToRem.forEach((indexToRem) => {
                this.uniqueBoxes.splice(indexToRem, 1);
                this.uniqueBoxesItems.splice(indexToRem, 1);
                this.selectAllValues[this.boxesTabIndex].splice(indexToRem, 1);
                this.uniqueBoxesItemSelected = this.uniqueBoxes[0].id;
                this.selectedVarieties[this.boxesTabIndex].splice(indexToRem, 1);
                this.chosenVarieties[this.boxesTabIndex].splice(indexToRem, 1);
            });
            this.boxTypeSelected = this.uniqueBoxes[0];
            this.initMarketBoxes(false, boxTypesToRemIds);
            return indexesToRem;
        }
    }
}

@Component({
    selector: 'jhi-market-popup',
    template: ''
})
export class MarketPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private boxTypePopupService: MarketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.boxTypePopupService
                    .open(MarketDialogComponent, 'market-modal-window', params['id']);
            } else {
                this.modalRef = this.boxTypePopupService
                    .open(MarketDialogComponent, 'market-modal-window');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

export interface Sizable {
    name: string;
    value: string;
    measure: number;
}
