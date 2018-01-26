import {
    Component, OnInit, OnDestroy, ChangeDetectorRef, DoCheck
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {EventManager, AlertService, JhiLanguageService} from 'ng-jhipster';

import { SeasonPopupService } from './season-popup.service';
import { SeasonService } from './season.service';
import {Season} from '../../entities/season/season.model';
import {DIGITS, LATIN_VALIDATION} from '../../shared/constants/validation.constants';
import {DatePipe} from '@angular/common';
import {SelectItem} from 'primeng/primeng';
import {Market} from '../../entities/market/market.model';
import {Position} from '../../entities/position/position.model';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {Subscription} from 'rxjs/Subscription';
import {TranslationService} from '../../shared/language/translation-service';
import {MarketSeason} from '../../entities/market-season/market-season.model';

@Component({
    selector: 'jhi-season-dialog',
    templateUrl: './season-dialog.component.html'
})
export class SeasonDialogComponent implements OnInit, OnDestroy, DoCheck {

    season: Season;
    authorities: any[];
    isSaving: boolean;
    seasonNameMax = 50;
    seasonYearMax = 4;
    seasonStartDate: Date;
    seasonEndDate: Date;
    seasonNotifySince: Date;
    currentDate: Date;
    seasonMarkets: SelectItem[] = [];
    marketsIdSelected: string [] = [];
    positionsIdSelected: string [] = [];
    markets: Market[];
    positions: Position[];
    defaultMarketLabel: string;
    defaultPositionLabel: string;
    selectedPositionsLabel: string;
    selectedMarketsLabel: string;
    seasonPositions: SelectItem[] = [];
    es: any;
    onSpanish = false;
    private getAllMarketsByCurrentCompany: Subscription;
    private getAllPositionsByCompany: Subscription;
    private getTranslation: Subscription;
    private resultSubscribe: Subscription;
    selectAllValuesPending = false;
    tmpSeasonName: string;
    tmpSeasonYear: string;
    seasonYear: number;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private seasonService: SeasonService,
        private eventManager: EventManager,
        private datePipe: DatePipe,
        private jhiLanguageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private translationService: TranslationService,
    ) {
        this.selectAllValuesPending = true;
        this.jhiLanguageService.setLocations(['season']);
        this.languageHelper.addListener(this.translationService);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];

        this.spanishCalendar();
        this.onChangeLanguage();

        if (this.season && this.season.marketSeasons) {
            this.season.marketSeasons.forEach((ms) => {
                this.marketsIdSelected.push(ms.market.id.toString());
            });
        }

        if (this.season && this.season.positions) {
            this.season.positions.forEach((p) => {
                this.positionsIdSelected.push(p.id.toString());
            });
        }

        this.getAllMarketsByCurrentCompany = this.seasonService.getAllMarketsByCurrentCompany()
            .subscribe((markets: Market[]) => {
                this.markets = markets;
                if (this.markets) {
                    this.markets.forEach((market) => {
                        this.seasonMarkets.push({label: ' '.concat(market.name), value: market.id});
                    });
                }
            });
        this.getAllPositionsByCompany = this.seasonService.getAllPositionsByCompany()
            .subscribe((positions: Position[]) => {
                this.positions = positions;
            });
            this.currentDate = new Date();
            this.seasonStartDate = new Date();
            this.seasonEndDate = new Date();
            this.seasonNotifySince = new Date();
        if (this.season.id) {
            this.seasonStartDate.setFullYear(this.season.startDate.year, this.season.startDate.month, this.season.startDate.day);
            this.seasonEndDate.setFullYear(this.season.endDate.year, this.season.endDate.month, this.season.endDate.day);
            this.seasonNotifySince.setFullYear(this.season.notifyStartDate.year, this.season.notifyStartDate.month, this.season.notifyStartDate.day);
            this.seasonYear = this.season.seasonYear;
        }
        this.tmpSeasonName = '';
        this.tmpSeasonYear = '';
    }

    ngDoCheck(): void {
        this.selectAllValuesPending = false;
    }

    ngOnDestroy(): void {
        this.languageHelper.removeListener(this.translationService);
        this.getAllMarketsByCurrentCompany.unsubscribe();
        this.getAllPositionsByCompany.unsubscribe();
        this.getTranslation.unsubscribe();

        if (this.resultSubscribe) {
        this.resultSubscribe.unsubscribe();
        }
    }

    onChangeLanguage() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.translationService.onLangChange(currentLang);
            this.setTranslation(currentLang, this.translationService);
            if (currentLang === 'es') {
                this.onSpanish = true;
            } else {
                this.onSpanish = false;
            }
        });
    }

    setTranslation(currentLang: string, translationServise: TranslationService) {
        this.getTranslation = translationServise.getTranslation(currentLang, 'season').subscribe((res) => {
            this.defaultMarketLabel = res.defaultMarketLabel;
            this.defaultPositionLabel = res.defaultPositionLabel;
            this.selectedPositionsLabel = res.selectedPositionsLabel;
            this.selectedMarketsLabel = res.selectedMarketsLabel;
            this.seasonPositions.push({label: ' '.concat(res.salesManager), value: 1});
            this.seasonPositions.push({label: ' '.concat(res.salesAssistant), value: 2});
            this.seasonPositions.push({label: ' '.concat(res.companyOwner), value: 3});
            this.seasonPositions.push({label: ' '.concat(res.agronomEngineer), value: 4});
            this.seasonPositions.push({label: ' '.concat(res.postHarvestManager), value: 5});
            this.seasonPositions.push({label: ' '.concat(res.generalManager), value: 7});
            this.seasonPositions.push({label: ' '.concat(res.accountantMananger), value: 8});
            this.seasonPositions.push({label: ' '.concat(res.accountantAssistant), value: 9});
            this.seasonPositions.push({label: ' '.concat(res.claimsMananger), value: 10});
            this.seasonPositions.push({label: ' '.concat(res.claimsAssistant), value: 11});
            this.seasonPositions.push({label: ' '.concat(res.coordinationManager), value: 12});
            this.seasonPositions.push({label: ' '.concat(res.systemManager), value: 13});
        });
    }

    spanishCalendar() {
        this.es = {
            firstDayOfWeek: 0,
            dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
            dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            dayNamesMin: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
            monthNames: [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre' ],
            monthNamesShort: [ 'Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
                'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic' ],
        };
    }

    convertDateFromServerToString(date: any): string {
        return date.day + '/' + date.month + '/' + date.year;
    }

    private setSelectedMarkets() {

        this.season.marketSeasons = [];

        if (this.marketsIdSelected) {
            this.season.marketSeasons = [];
            this.marketsIdSelected.forEach((id) => {
                this.markets.forEach((m) => {
                    if (+id === m.id) {
                        const ms: MarketSeason = new MarketSeason();
                        ms.market = m;
                        ms.season = null;
                        this.season.marketSeasons.push(ms);
                    }
                });
            });
        }
    }

    private setSelectedPositions() {
        this.season.positions = [];

        if (this.positionsIdSelected) {
            this.positionsIdSelected.forEach((id) => {
                this.positions.forEach((p) => {
                    if (+id === p.id) {
                        this.season.positions.push(p);
                    }
                });
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.setSelectedMarkets();
        this.setSelectedPositions();
        const startDateObj = this.datePipe.transform(this.seasonStartDate, 'yyyy-MM-dd').split('-');
        this.season.startDate = {year: +startDateObj[0], month: +startDateObj[1], day: +startDateObj[2]};
        const endDateObj = this.datePipe.transform(this.seasonEndDate, 'yyyy-MM-dd').split('-');
        this.season.endDate = {year: +endDateObj[0], month: +endDateObj[1], day: +endDateObj[2]};
        const notifyObj = this.datePipe.transform(this.seasonNotifySince, 'yyyy-MM-dd').split('-');
        this.season.notifyStartDate = {year: +notifyObj[0], month: +notifyObj[1], day: +notifyObj[2]};
        this.season.seasonYear = this.seasonYear;
        if (!this.season.id) {
            this.subscribeToSaveResponse(
                this.seasonService.create(this.season), true);
        } else {
            this.subscribeToSaveResponse(
                this.seasonService.update(this.season), false);
        }
    }

    private subscribeToSaveResponse(result: Observable<Season>, isCreated: boolean) {
        this.resultSubscribe = result.subscribe((res: Season) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Season, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.season.created'
            : 'flowersApp.season.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'seasonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
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
        if (error.headers.get('x-flowersapp-error') === 'error.DuplicateName') {
            this.tmpSeasonName = this.season.seasonName.toLowerCase().trim();
            this.tmpSeasonYear = this.season.seasonYear.toString().trim();
        } else {
            this.alertService.error(error.message, null, null);
        }
    }

    getCl() {
        const r = this.requiredValidation(this.season.seasonYear);
        return {
            'season-required': r,
            'season-year-input': true,
            'input-form': true,
        };
    }

    requiredValidation(fieldData: any) {
        return !fieldData || !this.isFillValidation(fieldData);
    }

    isFillValidation(fieldData: any): boolean {
        return fieldData && fieldData.toString().trim();
    }

    latinValidation(fieldData: string): boolean {
        if (fieldData && !this.maxLength(fieldData, this.seasonNameMax)) {
            return !fieldData.match(LATIN_VALIDATION);
        }
    }

    lengthValidation(fieldData: string): boolean {
        if (fieldData && fieldData.length) {
            return fieldData.length > 50;
        }
    }

    maxLength(data: string | number, maxLength: number): boolean {
        if (data && data.toString().length) {
            return data.toString().length > maxLength;
        }
    }

    multiselectValidation(selection: string []): boolean {
        return selection.length === 0;
    }

    digitValidation(seasonYear: number): boolean {
        if (seasonYear && seasonYear.toString()) {
            return !seasonYear.toString().match(DIGITS);
        }
    }

    endDateValidation(seasonStartDate: Date, seasonEndDate: Date) : boolean {
        return seasonStartDate.getDate() > seasonEndDate.getDate();
    }

    notifyDateValidation(seasonNotifySince: Date, seasonStartDate: Date) : boolean {
        return seasonNotifySince.getDate() > seasonStartDate.getDate();
    }

    duplicateNameValidation(season: Season, maxLength: number): boolean {
        return this.season.seasonName && this.tmpSeasonName && this.tmpSeasonName === this.season.seasonName.toLowerCase().trim()
            && !this.maxLength(season.seasonName, maxLength);
    }

    duplicateYearValidation(season: Season, maxLength: number): boolean {
        return this.season.seasonYear && this.tmpSeasonYear && this.tmpSeasonYear === this.season.seasonYear.toString().trim()
            && !this.maxLength(season.seasonYear, maxLength);
    }

    saveButtonDeactive(season: Season): boolean {
        return this.requiredValidation(season.seasonName) || this.latinValidation(season.seasonName)
            || this.maxLength(season.seasonName, this.seasonNameMax) || this.requiredValidation(this.seasonYear)
            || this.multiselectValidation(this.marketsIdSelected) || this.endDateValidation(this.seasonStartDate, this.seasonEndDate)
            || this.notifyDateValidation(this.seasonNotifySince, this.seasonStartDate);
    }

}

@Component({
    selector: 'jhi-season-popup',
    template: ''
})
export class SeasonPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonPopupService: SeasonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.seasonPopupService
                    .open(SeasonDialogComponent, 'modal_large', params['id']);
            } else {
                this.modalRef = this.seasonPopupService
                    .open(SeasonDialogComponent, 'modal_large');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
