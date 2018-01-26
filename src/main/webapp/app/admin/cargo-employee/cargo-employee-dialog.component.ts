import { Component, DoCheck, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager, JhiLanguageService } from 'ng-jhipster';
import { CargoEmployee } from './cargo-employee.model';
import { CargoEmployeePopupService } from './cargo-employee-popup.service';
import { CargoEmployeeService } from './cargo-employee.service';
import {
    EMAIL_FIRST_PART_VALIDATION,
    EMAIL_VALIDATION,
    LATIN_VALIDATION,
    PHONE_VALIDATION
} from '../../shared/constants/validation.constants';
import { CargoEmployeePosition } from '../cargo-employee-position/cargo-employee-position.model';
import { SelectItem } from 'primeng/primeng';
import { Market } from '../../entities/market/market.model';
import { CompanyUser } from '../../entities/company-user/company-user.model';
import { CargoAgency } from '../cargo-agency/cargo-agency.model';
import { isUndefined } from 'ngx-bootstrap/bs-moment/utils/type-checks';
import { TranslationService } from '../../shared/language/translation-service';
import { JhiLanguageHelper } from '../../shared/language/language.helper';
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'jhi-cargo-employee-dialog',
    templateUrl: './cargo-employee-dialog.component.html',
    styleUrls : ['./cargo-employee-dialog.component.scss']

})

export class CargoEmployeeDialogComponent implements OnInit, OnDestroy, DoCheck {

    cargoEmployee: CargoEmployee;
    companyUser: CompanyUser;
    authorities: any[];
    isSaving: boolean;
    errorAlert = false;
    marketsIdSelected: string [] = [];
    selectedPositions: string [] = [];
    selectedAgency: string;
    cargoEmployeePositions: SelectItem [] = [];
    companyUserMarkets: SelectItem [] = [];
    cargoAgencies: SelectItem [] = [];
    cargoEmployeePosition: CargoEmployeePosition[];
    markets: Market[];
    tmpAccountEmail: string;
    private getTranslation: Subscription;
    defaultLabel: string;
    defaultMarketsLabel: string;
    selectedPositionsLabel: string;
    selectedMarketsLabel: string;
    chooseAgencyForCargoEmployee: string;
    cargoAgency: CargoAgency[];
    mobilePhoneMask: string;
    officePhoneMask: string;
    loadComponent = false;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private cargoEmployeeService: CargoEmployeeService,
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private translationService: TranslationService,
    ) {
        this.loadComponent = true;
        this.jhiLanguageService.setLocations(['companyUser']);
        this.languageHelper.addListener(this.translationService);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.cargoAgency = [];
        this.onChangeLanguage();

        this.cargoEmployeeService.findAllCargoEmployeePositions()
            .subscribe((cargoEmployeePositions: CargoEmployeePosition []) => {
            this.cargoEmployeePosition = cargoEmployeePositions;
            this.cargoEmployeePosition.forEach((cargoEmployeePosition) => {
                this.cargoEmployeePositions.push({label: ' '.concat(cargoEmployeePosition.name), value: cargoEmployeePosition.id});
            });
        });

        this.cargoEmployeeService.findAllCargoAgencies()
            .subscribe((cargoAgencies: CargoAgency []) => {
                this.cargoAgency = cargoAgencies;
                this.cargoAgency.forEach((cargoAgency) => {
                    this.cargoAgencies.push({label: ' '.concat(cargoAgency.name), value: cargoAgency.id});
                });
                if (this.cargoEmployee.id) {
                    this.selectedAgency = this.cargoEmployee.cargoAgency.id.toString();
                }
            });

        this.cargoEmployeeService.getAllMarketsByCurrentCompany()
            .subscribe((markets: Market[]) => {
                this.markets = markets;
                this.markets.forEach((market) => {
                    this.companyUserMarkets.push({label: ' '.concat(market.name), value: market.id});
                });
            });

        if (this.cargoEmployee && this.cargoEmployee.cargoEmployeePositions) {
            this.cargoEmployee.cargoEmployeePositions.forEach((p) => {
                this.selectedPositions.push(p.id.toString());
            });
        }

        if (this.cargoEmployee && this.cargoEmployee.markets) {
            this.cargoEmployee.markets.forEach((m) => {
                this.marketsIdSelected.push(m.id.toString());
            });
        }

        this.mobilePhoneMask = this.cargoEmployee.mobilePhone;
        this.officePhoneMask = this.cargoEmployee.officePhone;
    }

    ngDoCheck(): void {
        this.loadComponent = false;
    }

    ngOnDestroy(): void {
        this.languageHelper.removeListener(this.translationService);
        this.getTranslation.unsubscribe();
    }

    onChangeLanguage() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.translationService.onLangChange(currentLang);
            this.setTranslation(currentLang, this.translationService);
        });
    }

    setTranslation(currentLang: string, translationServise: TranslationService) {
        this.getTranslation = translationServise.getTranslation(currentLang, 'cargoEmployee').subscribe((res) => {
            this.defaultMarketsLabel = res.defaultMarketsLabel;
            this.defaultLabel = res.defaultLabel;
            this.selectedPositionsLabel = res.selectedPositionsLabel;
            this.selectedMarketsLabel = res.selectedMarketsLabel;
            this.chooseAgencyForCargoEmployee = res.chooseAgencyForCargoEmployee;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.cargoAgency.forEach((a) => {
            if (a.id.toString() == this.selectedAgency) {
                this.cargoEmployee.cargoAgency = a;
            }
        });

        this.cargoEmployee.mobilePhone = this.mobilePhoneMask;
        this.cargoEmployee.officePhone = this.officePhoneMask;

        this.isSaving = true;
        this.setSelectedPositions();
        this.setSelectedMarkets();
        if (!this.cargoEmployee.officePhone) {
            this.cargoEmployee.officePhone = '';
        }
        if (!this.cargoEmployee.mobilePhone) {
            this.cargoEmployee.mobilePhone = '';
        }
        if (!this.cargoEmployee.skype) {
            this.cargoEmployee.skype = '';
        }
        if (this.cargoEmployee.id) {
            this.subscribeToSaveResponse(
                this.cargoEmployeeService.update(this.cargoEmployee), false);
        } else {
            this.subscribeToSaveResponse(
                this.cargoEmployeeService.create(this.cargoEmployee), true);
        }
    }

    private setSelectedPositions() {
        this.cargoEmployee.cargoEmployeePositions = [];
        if (this.selectedPositions) {
            this.selectedPositions.forEach((id) => {
                this.cargoEmployeePosition.forEach((p) => {
                    if (+id === p.id) {
                        this.cargoEmployee.cargoEmployeePositions.push(p);
                    }
                });
            });
        }
    }

    private setSelectedMarkets() {
        this.cargoEmployee.markets = [];
        if (this.marketsIdSelected) {
            this.marketsIdSelected.forEach((id) => {
                this.markets.forEach((p) => {
                    if (+id === p.id) {
                        this.cargoEmployee.markets.push(p);
                    }
                });
            });
        }
    }

    private subscribeToSaveResponse(result: Observable<CargoEmployee>, isCreated: boolean) {
        result.subscribe((res: CargoEmployee) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CargoEmployee, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.cargoEmployee.created'
            : 'flowersApp.cargoEmployee.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'cargoEmployeeListModification', content: 'OK'});
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
            this.errorAlert = true;
        } else {
            this.alertService.error(error.message, null, null);
        }
    }

    trackPositionById(index: number, item: CargoEmployeePosition) {
        return item.id;
    }

    latinValidation(fieldData: string): boolean {
        if (fieldData) {
            return !fieldData.match(LATIN_VALIDATION);
        }
    }

    lengthValidation(fieldData: string): boolean {
        if (fieldData && fieldData.length) {
            return fieldData.length > 50;
        }
    }

    requiredValidation(fieldData: string) {
        return !fieldData || !this.isFillValidation(fieldData);
    }

    emailValidation(fieldData: string): boolean {
        if (fieldData && !this.lengthValidation(fieldData)) {
            return fieldData.indexOf('@') === -1;
        }
    }

    isFillValidation(fieldData: any): boolean {
        return fieldData && fieldData.toString().trim();
    }

    duplicateValidation(fieldData: string): boolean {
        return this.cargoEmployee.email && this.tmpAccountEmail
            && this.tmpAccountEmail === this.cargoEmployee.email.toLowerCase().trim()
            && !this.lengthValidation(fieldData);
    }

    dropdownValidation(selection: string): boolean {
        return isUndefined(selection);
    }

    completeEmailValidation(fieldData: string): boolean {
        if (this.duplicateValidation(fieldData)) {
            return false;
        }
        if (fieldData && !this.emailValidation(fieldData)) {
            const checkedStrings: string [] = fieldData.split('@');
            const first: string = checkedStrings[0];
            const second: string = checkedStrings[1].trim();
            if (second === '') {
                return true;
            }
            if (!first.match(EMAIL_FIRST_PART_VALIDATION) || first.match(' ')) {
                return true;
            }
            if (second.match(EMAIL_VALIDATION)) {
                return true;
            }
            if (!(second.indexOf('.') === -1)) {
                const dotStrings: string [] = second.split('.');
                const third = dotStrings[dotStrings.length - 1].trim();
                if (third === '') {
                    return true;
                }
                if (third.match(EMAIL_VALIDATION)) {
                    return true;
                }
            }
        }
    }

    saveButtonDeactivation(cargoEmployee: CargoEmployee): boolean {
        return (this.lengthValidation(cargoEmployee.fullName) || this.latinValidation(cargoEmployee.fullName)
            || this.requiredValidation(cargoEmployee.fullName) || this.lengthValidation(cargoEmployee.email)
            || this.latinValidation(cargoEmployee.email) || this.lengthValidation(cargoEmployee.officePhone)
            || this.latinValidation(cargoEmployee.officePhone) || this.lengthValidation(cargoEmployee.mobilePhone)
            || this.latinValidation(cargoEmployee.mobilePhone) || this.lengthValidation(cargoEmployee.skype)
            || this.latinValidation(cargoEmployee.skype)) || this.emailValidation(cargoEmployee.email)
            || this.completeEmailValidation(cargoEmployee.email) || this.duplicateValidation(cargoEmployee.email)
            || this.dropdownValidation(this.selectedAgency);
    }
}

@Component({
    selector: 'jhi-cargo-employee-popup',
    template: ''
})
export class CargoEmployeePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cargoEmployeePopupService: CargoEmployeePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['cargoEmployeeId'] ) {
                this.modalRef = this.cargoEmployeePopupService
                    .open(CargoEmployeeDialogComponent, 'cargo-employee-modal-window', params['cargoEmployeeId'], params['cargoAgencyId']);
            } else {
                this.modalRef = this.cargoEmployeePopupService
                    .open(CargoEmployeeDialogComponent, 'cargo-employee-modal-window');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
