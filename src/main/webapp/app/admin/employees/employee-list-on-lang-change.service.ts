import {Injectable} from '@angular/core';
import {OnLangChange} from '../../shared/language/language.helper';
import {TranslateService} from '@ngx-translate/core';
import {EventManager} from 'ng-jhipster';
import {PositionsTranslateService} from './employee-list-positions-translate.service';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class EmployeeListOnLangChangeService implements OnLangChange {

    private _activatedStringDefault = 'Activated';
    private _deactivatedStringDefault = 'Deactivated';

    private _columns: Array<any> = [];

    private language = '';

    private _positionsMap: Array<any> = [{key: 0, value: ''}];

    private _filterTranslate = 'Filter all columns';

    private _firstText = 'First';
    private _previousText = 'Previous';
    private _nextText = 'Next';
    private _lastText = 'Last';

    private _pageXofY = 'Page';

    get pageXofY(): string {
        return this._pageXofY;
    }

    set pageXofY(value: string) {
        this._pageXofY = value;
    }

    get firstText(): string {
        return this._firstText;
    }

    set firstText(value: string) {
        this._firstText = value;
    }

    get previousText(): string {
        return this._previousText;
    }

    set previousText(value: string) {
        this._previousText = value;
    }

    get nextText(): string {
        return this._nextText;
    }

    set nextText(value: string) {
        this._nextText = value;
    }

    get lastText(): string {
        return this._lastText;
    }

    set lastText(value: string) {
        this._lastText = value;
    }

    get filterTranslate(): string {
        return this._filterTranslate;
    }

    set filterTranslate(value: string) {
        this._filterTranslate = value;
    }

    get positionsMap(): Array<any> {
        return this._positionsMap;
    }

    set positionsMap(value: Array<any>) {
        this._positionsMap = value;
    }

    get activatedStringDefault(): string {
        return this._activatedStringDefault;
    }

    set activatedStringDefault(value: string) {
        this._activatedStringDefault = value;
    }

    get deactivatedStringDefault(): string {
        return this._deactivatedStringDefault;
    }

    set deactivatedStringDefault(value: string) {
        this._deactivatedStringDefault = value;
    }

    get columns(): Array<any> {
        return this._columns;
    }

    constructor(private translateService: TranslateService,
                private eventManager: EventManager,
                private http: Http,
                private positionsTranslateService: PositionsTranslateService) {
    }

    getTranslation(lang, namePath): Observable <any> {
        return this.http.get('i18n/' + lang + '/' + namePath + '.json')
            .map((response) => response.json());
    }

    onLangChange(lang: string) {

        if (this.language !== lang) {

            this._positionsMap = [];

            this._columns = [];

            this.translateService.use(lang);

            this._positionsMap = this.positionsTranslateService.positionEmployeeTranslate(this._positionsMap, this.translateService);

            this.translateService.get('flowersApp.companyUser.filterInput')
                .subscribe((defTitleTranslation: string) => {
                this._filterTranslate = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.firstText')
                .subscribe((defTitleTranslation: string) => {
                this._firstText = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.previousText')
                .subscribe((defTitleTranslation: string) => {
                this._previousText = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.nextText')
                .subscribe((defTitleTranslation: string) => {
                this._nextText = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.lastText')
                .subscribe((defTitleTranslation: string) => {
                this._lastText = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.pageXofY')
                .subscribe((defTitleTranslation: string) => {
                this._pageXofY = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.statusActivated')
                .subscribe((defTitleTranslation: string) => {
                this._activatedStringDefault = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.statusDeactivated')
                .subscribe((defTitleTranslation: string) => {
                this._deactivatedStringDefault = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.columnPositions')
                .subscribe((defTitleTranslation: string) => {
                this._columns.push({title: defTitleTranslation, name: 'positions', sort: false, className: 'title-table'});
            });

            this.translateService.get('flowersApp.companyUser.columnFullName')
                .subscribe((defTitleTranslation: string) => {
                this._columns.push({title: defTitleTranslation, name: 'full_name', sort: false, className: 'title-table'});
            });

            this.translateService.get('flowersApp.companyUser.columnAccountEmail')
                .subscribe((defTitleTranslation: string) => {
                this._columns.push({title: defTitleTranslation, name: 'account_email', sort: false, className: 'title-table'});
            });

            this.translateService.get('flowersApp.companyUser.columnWorkEmail')
                .subscribe((defTitleTranslation: string) => {
                this._columns.push({title: defTitleTranslation, name: 'work_email', sort: false, className: 'title-table'});
            });

            this.translateService.get('flowersApp.companyUser.columnOfficePhone')
                .subscribe((defTitleTranslation: string) => {
                this._columns.push({title: defTitleTranslation, name: 'off_phone', sort: false, className: 'title-table'});
            });

            this.translateService.get('flowersApp.companyUser.columnMobilePhone')
                .subscribe((defTitleTranslation: string) => {
                this._columns.push({title: defTitleTranslation, name: 'mob_phone', sort: false, className: 'title-table'});
            });

            this.translateService.get('flowersApp.companyUser.columnSkype')
                .subscribe((defTitleTranslation: string) => {
                this._columns.push({title: defTitleTranslation, name: 'skype', sort: false, className: 'title-table'});
            });

            this.translateService.get('flowersApp.companyUser.columnMarkets')
                .subscribe((defTitleTranslation: string) => {
                this._columns.push({title: defTitleTranslation, name: 'markets', sort: false, className: 'title-table'});
            });

            this.translateService.get('flowersApp.companyUser.columnStatus')
                .subscribe((defTitleTranslation: string) => {
                this._columns.push({
                    title: defTitleTranslation,
                    name: 'status',
                    sort: 'asc',
                    className: 'title-table'
                });
            });

            this.translateService.get('flowersApp.companyUser.columnEdit')
                .subscribe((defTitleTranslation: string) => {
                this._columns.push({
                    title: defTitleTranslation,
                    name: 'edit',
                    sort: false,
                    className: 'title-table'
                });
                this.eventManager.broadcast({name: 'languageChanged', content: 'OK'});
                this.language = lang;
            });
        }
    }
}
