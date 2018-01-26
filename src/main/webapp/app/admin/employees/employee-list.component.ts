import {Component, OnDestroy, OnInit} from '@angular/core';
import {CompanyUser} from '../../entities/company-user/company-user.model';
import {Subscription} from 'rxjs/Subscription';
import {EmployeeListService} from './employee-list.service';
import {AlertService, EventManager, JhiLanguageService} from 'ng-jhipster';
import {Principal} from '../../shared/auth/principal.service';
import {Router} from '@angular/router';
import {Company} from '../../entities/company/company.model';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {EmployeeListOnLangChangeService} from './employee-list-on-lang-change.service';
import {TranslationService} from '../../shared/language/translation-service';
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'jhi-flowers-employee-list',
    templateUrl: './employee-list.component.html',
    styleUrls: ['employee-list.scss'],
    providers: [EmployeeListService]
})
export class EmployeeListComponent implements OnInit, OnDestroy {

    currentAccount: any;
    companyUsers: CompanyUser[];
    company: Company;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    statusSubscriber: Subscription;
    languageSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    status = false;
    onlyActivated = false;
    userId: string;
    public rows: Array<any> = [];
    activatedString: string;
    deactivatedString: string;
    languagePositions: Array<any>=  [{key: 0, value: ''}];
    filterTranslation: string;
    pageXofY: string;

    public columns: Array<any> = [];
    private _tableData: Array<any> = [];
    public page = 1;

    public itemsPerPage = 10;
    public maxSize = 5;
    public numPages = 1;
    public length = 0;
    public firstText: string;
    public previousText: string;
    public nextText: string;
    public lastText: string;

    public salesManager: string;
    public salesAssistant: string;
    public companyOwner: string;
    public agronomEngineer: string;
    public postHarvestManager: string;
    public generalManager: string;
    public accountantMananger: string;
    public accountantAssistant: string;
    public claimsMananger: string;
    public claimsAssistant: string;
    public coordinationManager: string;
    public systemManager: string;

    public columnPositions: string;
    public columnFullName: string;
    public columnAccountEmail: string;
    public columnWorkEmail: string;
    public columnOfficePhone: string;
    public columnMobilePhone: string;
    public columnSkype: string;
    public columnMarkets: string;
    public columnStatus: string;
    public columnEdit: string;

    public config: any = [];

    private data: Array<any>;

    public constructor( private employeeListService: EmployeeListService,
                        private alertService: AlertService,
                        private principal: Principal,
                        private router: Router,
                        private eventManager: EventManager,
                        private jhiLanguageService: JhiLanguageService,
                        private languageHelper: JhiLanguageHelper,
                        private employeeListLangChangeService: TranslationService,
    ) {
        this.jhiLanguageService.setLocations(['companyUser']);
    }

    public ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });

        this.onChangeLanguage();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.eventManager.destroy(this.statusSubscriber);
        this.eventManager.destroy(this.languageSubscriber);
        this.languageHelper.removeListener(this.employeeListLangChangeService);
    }

    onChangeLanguage() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.employeeListLangChangeService.onLangChange(currentLang);
            this.setTranslation(currentLang, this.employeeListLangChangeService);
            // this.languagePositions = this.employeeListLangChangeService.positionsMap;
            // this.columns = this.employeeListLangChangeService.columns;
            // this.activatedString = this.employeeListLangChangeService.activatedStringDefault;
            // this.deactivatedString = this.employeeListLangChangeService.deactivatedStringDefault;
            // this.filterTranslation = this.employeeListLangChangeService.filterTranslate;
            // this.firstText = this.employeeListLangChangeService.firstText;
            // this.previousText = this.employeeListLangChangeService.previousText;
            // this.nextText = this.employeeListLangChangeService.nextText;
            // this.lastText = this.employeeListLangChangeService.lastText;
            // this.pageXofY = this.employeeListLangChangeService.pageXofY;
        });
    }

    loadAll() {
        this.employeeListService.getCompanyUsersFromCurrentCompany().subscribe(
            (users) => this.onSuccess(users),
            (users) => this.onError(users.error)
        );
    }

    setTranslation(currentLang: string, translationServise: TranslationService) {
        translationServise.getTranslation(currentLang, 'companyUser').subscribe((res) => {
           this.salesManager = res.salesManager;
           this.salesAssistant = res.salesAssistant;
           this.companyOwner = res.companyOwner;
           this.agronomEngineer = res.agronomEngineer;
           this.postHarvestManager = res.postHarvestManager;
           this.generalManager = res.generalManager;
           this.accountantMananger = res.accountantMananger;
           this.accountantAssistant = res.accountantAssistant;
           this.claimsMananger = res.claimsMananger;
           this.claimsAssistant = res.claimsAssistant;
           this.coordinationManager = res.coordinationManager;
           this.systemManager = res.systemManager;

           this.columnPositions = res.columnPositions;
           this.columnFullName = res.columnFullName;
           this.columnAccountEmail = res.columnAccountEmail;
           this.columnWorkEmail = res.columnWorkEmail;
           this.columnOfficePhone = res.columnOfficePhone;
           this.columnMobilePhone = res.columnMobilePhone;
           this.columnSkype = res.columnSkype;
           this.columnMarkets = res.columnMarkets;
           this.columnStatus = res.columnStatus;
           this.columnEdit = res.columnEdit;

           this.activatedString = res.statusActivated;
           this.deactivatedString = res.statusDeactivated;
           this.filterTranslation = res.filterInput;
           this.firstText = res.firstText;
           this.previousText = res.previousText;
           this.nextText = res.nextText;
           this.lastText = res.lastText;
           this.pageXofY = res.pageXofY;
           this.setPositions();
           this.setColumns();
       });
    }

    setColumns(){
        this.columns.push({title: this.columnPositions, name: 'positions', sort: false, className: 'title-table'});
        this.columns.push({title: this.columnFullName, name: 'full_name', sort: false, className: 'title-table'});
        this.columns.push({title: this.columnAccountEmail, name: 'account_email', sort: false, className: 'title-table'});
        this.columns.push({title: this.columnWorkEmail, name: 'work_email', sort: false, className: 'title-table'});
        this.columns.push({title: this.columnOfficePhone, name: 'off_phone', sort: false, className: 'title-table'});
        this.columns.push({title: this.columnMobilePhone, name: 'mob_phone', sort: false, className: 'title-table'});
        this.columns.push({title: this.columnSkype, name: 'skype', sort: false, className: 'title-table'});
        this.columns.push({title: this.columnMarkets, name: 'markets', sort: false, className: 'title-table'});
        this.columns.push({title: this.columnStatus, name: 'status', sort: 'asc', className: 'title-table'});
        this.columns.push({title: this.columnEdit, name: 'edit', sort: false, className: 'title-table'});

        this.languageHelper.addListener(this.employeeListLangChangeService);

        this.config  = {
            paging: true,
            sorting: {columns: this.columns},
            filtering: {filterString: ''},
            className: ['cells-resize', 'table-rows', 'table-striped', 'table-bordered'],
        };

        this.loadAll();
        this.registerChangeInCompanyUsers();
    }

    setPositions() {
        this.languagePositions.push({key: 1, value: this.salesManager});
        this.languagePositions.push({key: 2, value: this.salesAssistant});
        this.languagePositions.push({key: 3, value: this.companyOwner});
        this.languagePositions.push({key: 4, value: this.agronomEngineer});
        this.languagePositions.push({key: 5, value: this.postHarvestManager});
        this.languagePositions.push({key: 6, value: this.generalManager});
        this.languagePositions.push({key: 7, value: this.accountantMananger});
        this.languagePositions.push({key: 8, value: this.accountantAssistant});
        this.languagePositions.push({key: 9, value: this.claimsMananger});
        this.languagePositions.push({key: 10, value: this.claimsAssistant});
        this.languagePositions.push({key: 11, value: this.coordinationManager});
        this.languagePositions.push({key: 12, value: this.systemManager});
    }

    reset() {
        this.page = 1;
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data) {
        this._tableData = [];
        this.length = data.length;
        for (let i = 0; i < this.length; i++) {
            let statusDetail: string;
            if (data[i].user.activated === false) {
                statusDetail = '<div class="deactivated">' + this.deactivatedString + '</div>';
            } else {
                statusDetail = '<div class="activated">' + this.activatedString + '</div>';
            }

            let positionsString = '';
            let marketsString = '';
            const positionsArray: string[] = [];
            const marketsArray: string[] = [];

            data[i].positions.forEach((p) => {
                this.languagePositions.forEach((obj) => {
                    let id: number;
                    let positionName: string;

                        for (const key in obj) {

                            if (typeof obj[key] === 'number') {
                                id = obj[key];
                            }
                            if (typeof obj[key] === 'string') {
                                positionName = obj[key];
                                if (id === p.id) {
                                    positionsArray.push(' '.concat(positionName));
                                }
                            }
                        }
                });
            });
            data[i].markets.forEach((m) => {
               marketsArray.push(' '.concat(m.name));
            });
            positionsString =  positionsArray.join();
            marketsString = marketsArray.join();
            if (this.onlyActivated === true && data[i].user.activated === true) {
                    this.pushDataToTable(data[i], positionsString, marketsString, statusDetail);
            }
            if (this.onlyActivated === false) {
                    this.pushDataToTable(data[i], positionsString, marketsString, statusDetail);
            }
        }
        this.companyUsers = this._tableData;
        this.onChangeTable(this.config);
    }

    private pushDataToTable(data, positionsString, marketsString, statusDetail) {
        this._tableData.push({
            'positions': positionsString,
            'full_name': data.fullName,
            'account_email': data.accountEmail,
            'work_email': data.workEmail,
            'off_phone': data.officePhone,
            'mob_phone': data.mobilePhone,
            'skype': data.skype,
            'status': statusDetail,
            'markets': marketsString,
            'edit': '<span class="edit-block-table">' + data.id + '</span>'
        });
    }

    public checkChange() {
            this.onlyActivated = !this.onlyActivated;
            this.eventManager.broadcast({ name: 'changedStatus', content: 'OK'});
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    registerChangeInCompanyUsers() {
        this.eventSubscriber = this.eventManager.subscribe('companyUserListModification', (response) => this.loadAll());
        this.statusSubscriber = this.eventManager.subscribe('changedStatus', (response) => this.reset());
        this.languageSubscriber = this.eventManager.subscribe('languageChanged', (response) => {
            this.onChangeLanguage();
        });
    }

    public changePage(page: any, data: Array<any> = this.data): Array<any> {
        const start = (page.page - 1) * page.itemsPerPage;
        const end = page.itemsPerPage > -1 ? (start + page.itemsPerPage) : data.length;
        return data.slice(start, end);
    }

    public changeSort(data: any, config: any): any {
        if (!config.sorting) {
            return data;
        }

        const columns = this.config.sorting.columns || [];
        let columnName: string = void 0;
        let sort: string = void 0;

        for (let i = 0; i < columns.length; i++) {
            if (columns[i].sort !== '' && columns[i].sort !== false) {
                columnName = columns[i].name;
                sort = columns[i].sort;
            }
        }
        if (!columnName) {
            return data;
        }
        // simple sorting
        return data.sort((previous: any, current: any) => {
            if (previous[columnName] > current[columnName]) {
                return sort === 'desc' ? -1 : 1;
            } else if (previous[columnName] < current[columnName]) {
                return sort === 'asc' ? -1 : 1;
            }
            return 0;
        });
    }

    public changeFilter(data: any, config: any): any {

        let filteredData: Array<any> = data;

        this.columns.forEach((column: any) => {
            if (column.filtering) {
                filteredData = filteredData.filter((item: any) => {
                    return item[column.name].match(column.filtering.filterString);
                });
            }
        });
        if (!config.filtering) {
            return filteredData;
        }
        if (config.filtering.columnName) {
            return filteredData.filter((item: any) =>
                item[config.filtering.columnName].match(this.config.filtering.filterString));
        }
        const tempArray: Array<any> = [];
        filteredData.forEach((item: any) => {
            let flag = false;
            this.columns.forEach((column: any) => {
                if (item[column.name].toString().match(this.config.filtering.filterString)) {
                    flag = true;
                }
            });
            if (flag) {
                tempArray.push(item);
            }
        });
        filteredData = tempArray;

        return filteredData;
    }

    public onChangeTable(config: any, page: any = {page: this.page, itemsPerPage: this.itemsPerPage}): any {
        if (config.filtering) {
            Object.assign(this.config.filtering, config.filtering);
        }

        if (config.sorting) {
            Object.assign(this.config.sorting, config.sorting);
        }
        const filteredData = this.changeFilter(this.companyUsers, this.config);
        const sortedData = this.changeSort(filteredData, this.config);
        this.rows = page && config.paging ? this.changePage(page, sortedData) : sortedData;
        this.length = sortedData.length;
    }

    public onCellClick(data: any): any {
        console.log(data);
            const numberId: Array<number> = [];
            for (let i = 0; i < data.row.edit.length; i++) {
                if (data.row.edit.charAt(i).match('[0-9]')) {
                    numberId.push(data.row.edit.charAt(i));
                }
            }
            this.userId = numberId.toString();
        if ( data.column === 'edit') {
            this.router.navigate(['/', { outlets: { popup: 'employee-user/' + this.userId + '/edit'} }]);
        }
    }

}
