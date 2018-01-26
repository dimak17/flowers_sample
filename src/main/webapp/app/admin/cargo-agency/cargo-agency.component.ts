import { Component, OnInit, OnDestroy, DoCheck } from '@angular/core';
import { Router } from '@angular/router';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';
import { Subscription } from 'rxjs/Subscription';
import { CargoAgency } from './cargo-agency.model';
import { CargoAgencyService } from './cargo-agency.service';
import { Principal, ResponseWrapper } from '../../shared';
import { JhiLanguageHelper } from '../../shared/language/language.helper';
import { TranslationService } from '../../shared/language/translation-service';

@Component({
    selector: 'jhi-flowers-cargo-agency',
    templateUrl: './cargo-agency.component.html',
    styleUrls: ['/cargo-agency.scss'],

})
export class CargoAgencyComponent implements OnInit, OnDestroy, DoCheck {
    cargoAgencies: CargoAgency[];
    currentAccount: any;
    cargoAgency: CargoAgency;
    cargoAgencyListModification: Subscription;
    languageSubscriber: Subscription;

    globalFilterPlaceHolderHeader: string;
    nameHeader: string;
    mainAddressHeader: string;
    additionalAddressHeader: string;
    officePhoneHeader: string;
    emailHeader: string;
    editHeader: string;
    webPageHeader: string;
    deleteHeader: string;
    private getTranslation: Subscription;
    loadComponent = false;

    constructor(
        private cargoAgencyService: CargoAgencyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private router: Router,
        private jhiLanguageService: JhiLanguageService,
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
        this.registerChangeInCargoAgencies();
        this.onChangeLanguage();
        this.loadAll();
    }

    ngDoCheck(): void {
        this.loadComponent = false;
    }

    loadAll() {
        this.cargoAgencyService.getAllCargoAgenciesByCurrentCompany().subscribe(
            (res: ResponseWrapper) => {
                this.cargoAgencies = res.json;
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
        this.getTranslation = translationServise.getTranslation(currentLang, 'cargoAgency').subscribe((res) => {
            this.globalFilterPlaceHolderHeader = res.globalFilterPlaceHolderHeader;
            this.nameHeader = res.nameHeader;
            this.mainAddressHeader = res.mainAddressHeader;
            this.additionalAddressHeader = res.additionalAddressHeader;
            this.officePhoneHeader = res.officePhoneHeader;
            this.emailHeader = res.emailHeader;
            this.editHeader = res.editHeader;
            this.deleteHeader = res.deleteHeader;
            this.webPageHeader = res.webPageHeader;
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.cargoAgencyListModification);
        this.eventManager.destroy(this.languageSubscriber);
        this.languageHelper.removeListener(this.translationService);
        this.getTranslation.unsubscribe();
    }

    trackId(index: number, item: CargoAgency) {
        return item.id;
    }

    registerChangeInCargoAgencies() {
        this.cargoAgencyListModification = this.eventManager.subscribe('cargoAgencyListModification', (response) => this.loadAll());
        this.languageSubscriber = this.eventManager.subscribe('languageChanged', (response) => {
            this.onChangeLanguage();
            this.loadAll();
        });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    editCargoAgency(cargoAgency: CargoAgency) {
        this.router.navigate([{ outlets: {popup: 'cargo-agency/' + cargoAgency.id + '/edit'}}], { replaceUrl: true });
    }

    deleteCargoAgency(cargoAgency: CargoAgency) {
        this.router.navigate([{ outlets: {popup: 'cargo-agency/' + cargoAgency.id + '/delete'}}], { replaceUrl: true });
    }

    onRowClick(event) {
        if (event.originalEvent.target.cellIndex === 6) {
            this.action(event, '/edit');
        }
        if (event.originalEvent.target.cellIndex === 7) {
            this.action(event, '/delete');
        }
    }

    action(event: CargoAgency | any, route: String) {
        if (route === '/delete' || route === '/edit') {
            let id;
            if (event.originalEvent && event.originalEvent instanceof MouseEvent) {
                id = event.data.id;
            } else {
                id = event.id;
            }
            this.router.navigate(['/', {outlets: {popup: 'cargo-agency/' + id + route}}], {replaceUrl: true});
        }
    }
}
