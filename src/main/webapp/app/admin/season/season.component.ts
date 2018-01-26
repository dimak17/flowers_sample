import {Component, OnInit, OnDestroy, AfterViewChecked, DoCheck} from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import {EventManager, AlertService, JhiLanguageService} from 'ng-jhipster';

import { SeasonService } from './season.service';
import { Principal, ResponseWrapper } from '../../shared';
import {Season} from '../../entities/season/season.model';
import {Router} from '@angular/router';
import {DatePipe} from '@angular/common';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {TranslationService} from '../../shared/language/translation-service';

@Component({
    selector: 'jhi-season',
    templateUrl: './season.component.html',
    styleUrls: ['./season.component.scss']
})
export class SeasonComponent implements OnInit, OnDestroy, DoCheck {

    seasons: Season[];
    tableSeasons: Season[];
    currentAccount: any;
    seasonListModification: Subscription;
    languageSubscriber: Subscription;

    headerSeasonName: string;
    headerSeasonYear: string;
    headerSeasonMarkets: string;
    headerSeasonDates: string;
    headerNotifySince: string;
    headerNotifyTo: string;
    headerEdit: string;
    headerDelete: string;
    globalFilterPlaceHolder: string;
    seasonPositions: any;
    private getTranslation: Subscription;
    selectAllValuesPending = false;

    constructor(
        private seasonService: SeasonService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private router: Router,
        private datePipe: DatePipe,
        private jhiLanguageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private translationService: TranslationService,
    ) {
        this.selectAllValuesPending = true;
        this.languageHelper.addListener(this.translationService);
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSeasons();
        this.onChangeLanguage();
        this.loadAll();
    }

    ngDoCheck(): void {
        this.selectAllValuesPending = false;
    }

    loadAll() {
        this.seasonService.getAllByCurrentCompany().subscribe(
            (res: ResponseWrapper) => {
                this.seasons = res.json;
                this.tableSeasons = [];
                if (this.seasons && this.tableSeasons) {
                    this.seasons.forEach((season) => {

                        const startDate = this.datePipe.transform(season.startDate, 'dd/MM/yyyy');
                        const endDate = this.datePipe.transform(season.endDate, 'dd/MM/yyyy');
                        const notifyStartDate = this.datePipe.transform(season.notifyStartDate, 'dd/MM/yyyy');

                        const markets = [];
                        if (season && season.marketSeasons) {
                            season.marketSeasons.forEach((marketSeasons) => {
                                markets.push(' '.concat(marketSeasons.market.name));
                            });
                        }

                        season.dates = startDate + ' - ' + endDate;
                        season.notifyTableStartDate = notifyStartDate;
                        season.tableMarkets = markets.toString();

                        const positionTableResult: string [] = [];

                        if (season && season.positions) {
                            season.positions.forEach((p) => {
                                if (this.seasonPositions) {
                                    this.seasonPositions.forEach((sp) => {
                                        if (p.id === sp.key) {
                                            positionTableResult.push(sp.value);
                                        }
                                    });
                                }
                            });
                        }
                        season.notifyTo = positionTableResult.join();

                        this.tableSeasons.push(season);
                    });
                }
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    onChangeLanguage() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.translationService.onLangChange(currentLang);
            this.setTranslation(currentLang, this.translationService);
        });
    }

    setTranslation(currentLang: string, translationServise: TranslationService) {
        this.getTranslation = translationServise.getTranslation(currentLang, 'season').subscribe((res) => {
            this.seasonPositions = [];
            this.headerSeasonName = res.headerSeasonName;
            this.headerSeasonYear = res.headerSeasonYear;
            this.headerSeasonMarkets = res.headerSeasonMarkets;
            this.headerSeasonDates = res.headerSeasonDates;
            this.headerNotifySince = res.headerNotifySince;
            this.headerNotifyTo = res.headerNotifyTo;
            this.headerEdit = res.headerEdit;
            this.headerDelete = res.headerDelete;
            this.globalFilterPlaceHolder = res.globalFilterPlaceHolder;
            this.seasonPositions.push({key: 1, value: ' '.concat(res.salesManager)});
            this.seasonPositions.push({key: 2, value: ' '.concat(res.salesAssistant)});
            this.seasonPositions.push({key: 3, value: ' '.concat(res.companyOwner)});
            this.seasonPositions.push({key: 4, value: ' '.concat(res.agronomEngineer)});
            this.seasonPositions.push({key: 5, value: ' '.concat(res.postHarvestManager)});
            this.seasonPositions.push({key: 6, value: ' '.concat(res.generalManager)});
            this.seasonPositions.push({key: 7, value: ' '.concat(res.accountantMananger)});
            this.seasonPositions.push({key: 8, value: ' '.concat(res.accountantAssistant)});
            this.seasonPositions.push({key: 9, value: ' '.concat(res.claimsMananger)});
            this.seasonPositions.push({key: 10, value: ' '.concat(res.claimsAssistant)});
            this.seasonPositions.push({key: 11, value: ' '.concat(res.coordinationManager)});
            this.seasonPositions.push({key: 12, value: ' '.concat(res.systemManager)});
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.seasonListModification);
        this.eventManager.destroy(this.languageSubscriber);
        this.languageHelper.removeListener(this.translationService);
        this.getTranslation.unsubscribe();
    }

    convertDateToString(date: any): string {
        return date.day + '/' + date.month + '/' + date.year;
    }

    trackId(index: number, item: Season) {
        return item.id;
    }

    registerChangeInSeasons() {
        this.seasonListModification = this.eventManager.subscribe('seasonListModification', (response) => this.loadAll());
        this.languageSubscriber = this.eventManager.subscribe('languageChanged', (response) => {
            this.onChangeLanguage();
            this.loadAll();
        });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    onRowClick(event) {
        if (event.originalEvent.target.cellIndex == 6) {
            this.action(event, '/edit');
        }
        if (event.originalEvent.target.cellIndex == 7) {
           this.action(event, '/delete');
        }
    }

    action(event: Season | any, route: String) {
        if (route === '/delete' || route === '/edit') {
            let id;
            if (event.originalEvent && event.originalEvent instanceof MouseEvent) {
                id = event.data.id;
            } else {
                id = event.id;
            }
            this.router.navigate(['/', {outlets: {popup: 'season/' + id + route}}], {replaceUrl: true});
        }
    }

}
