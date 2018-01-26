import { Component, OnInit, OnDestroy, DoCheck } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';
import { CargoEmployee } from './cargo-employee.model';
import { CargoEmployeeService } from './cargo-employee.service';
import { Principal, ResponseWrapper } from '../../shared';
import { JhiLanguageHelper } from '../../shared/language/language.helper';
import { TranslationService } from '../../shared/language/translation-service';

@Component({
    selector: 'jhi-flowers-cargo-employee',
    templateUrl: './cargo-employee.component.html',
    styleUrls: ['/cargo-employee.scss'],
})

export class CargoEmployeeComponent implements OnInit, OnDestroy, DoCheck {
    cargoEmployees: CargoEmployee [];
    tableCargoEmployees: CargoEmployee [];
    currentAccount: any;
    loadComponent = false;
    languageSubscriber: Subscription;
    cargoEmployee: CargoEmployee;
    cargoEmployeeListModification: Subscription;
    private getTranslation: Subscription;

    fullNameHeader: string;
    emailHeader: string;
    officePhoneHeader: string;
    mobilePhoneHeader: string;
    skypeHeader: string;
    marketsHeader: string;
    positionsHeader: string;
    editHeader: string;
    deleteHeader: string;
    globalFilterPlaceHolderHeader: string;

    constructor(
        private cargoEmployeeService: CargoEmployeeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private principal: Principal,
        private router: Router,
        private languageHelper: JhiLanguageHelper,
        private translationService: TranslationService,
    ) {
        this.loadComponent = true;
        this.jhiLanguageService.setLocations(['companyUser']);
        this.languageHelper.addListener(this.translationService);
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCargoEmployees();
        this.onChangeLanguage();
        this.loadAll();
    }

    ngDoCheck(): void {
        this.loadComponent = false;
    }

    loadAll() {
        this.cargoEmployeeService.getAllCargoEmployeeByCurrentCompany().subscribe(
            (res: ResponseWrapper) => {
                this.cargoEmployees = res.json;
                this.tableCargoEmployees = [];
                if (this.cargoEmployees && this.tableCargoEmployees) {
                    this.cargoEmployees.forEach((cargoEmployee) => {

                        const markets = [];
                        const positions = [];

                        cargoEmployee.markets.forEach((market) => {
                            markets.push(' '.concat(market.name));
                        });

                        cargoEmployee.cargoEmployeePositions.forEach((cargoEmployeePositions) => {
                            positions.push(' '.concat(cargoEmployeePositions.name));
                        });

                        cargoEmployee.tableCargoEmployeePositions = positions.toString();
                        cargoEmployee.tableMarkets = markets.toString();

                        this.tableCargoEmployees.push(cargoEmployee);
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
        this.getTranslation = translationServise.getTranslation(currentLang, 'cargoEmployee').subscribe((res) => {
            this.globalFilterPlaceHolderHeader = res.globalFilterPlaceHolderHeader;
            this.fullNameHeader = res.fullNameHeader;
            this.officePhoneHeader = res.officePhoneHeader;
            this.mobilePhoneHeader = res.mobilePhoneHeader;
            this.skypeHeader = res.skypeHeader;
            this.marketsHeader = res.marketsHeader;
            this.positionsHeader = res.positionsHeader;
            this.emailHeader = res.emailHeader;
            this.editHeader = res.editHeader;
            this.deleteHeader = res.deleteHeader;
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.languageSubscriber);
        this.languageHelper.removeListener(this.translationService);
    }

    trackId(index: number, item: CargoEmployee) {
        return item.id;
    }

    registerChangeInCargoEmployees() {
        this.cargoEmployeeListModification = this.eventManager.subscribe('cargoEmployeeListModification', (response) => this.loadAll());
        this.languageSubscriber = this.eventManager.subscribe('languageChanged', (response) => {
            this.onChangeLanguage();
            this.loadAll();
        });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    editCargoEmployee(cargoEmployee: CargoEmployee) {
        this.router.navigate([{ outlets: {popup: 'cargo-employee/' + cargoEmployee.id + '/cargo-agency/' + cargoEmployee.cargoAgency.id + '/edit'}}], { replaceUrl: true });
    }

    deleteCargoEmployee(cargoEmployee: CargoEmployee) {
        this.router.navigate([{ outlets: {popup: 'cargo-employee/' + cargoEmployee.id + '/cargo-agency/' + cargoEmployee.cargoAgency.id + '/delete'}}], { replaceUrl: true });
    }

    onRowClick(event) {
        if (event.originalEvent.target.cellIndex === 7) {
            this.action(event, '/edit');
        }
        if (event.originalEvent.target.cellIndex === 8) {
            this.action(event, '/delete');
        }
    }

    action(event: CargoEmployee | any, route: String) {
        if (route === '/delete' || route === '/edit') {
            let id;
            let _id;
            if (event.originalEvent && event.originalEvent instanceof MouseEvent) {
                id = event.data.id;
                _id = event.data.cargoAgency.id;
            } else {
                id = event.id;
                _id = event.cargoAgency.id;
            }
            this.router.navigate(['/', {outlets: {popup: 'cargo-employee/' + id + '/cargo-agency/' + _id + route}}], {replaceUrl: true});
        }
    }
}
