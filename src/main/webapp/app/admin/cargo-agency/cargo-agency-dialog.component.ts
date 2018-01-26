import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';
import { CargoAgency } from './cargo-agency.model';
import { CargoAgencyPopupService } from './cargo-agency-popup.service';
import { CargoAgencyService } from './cargo-agency.service';
import { ResponseWrapper } from '../../shared';
import { Company } from '../../entities/company/company.model';
import { CompanyService } from '../../entities/company/company.service';
import {
    EMAIL_FIRST_PART_VALIDATION, EMAIL_VALIDATION,
    LATIN_VALIDATION
} from '../../shared/constants/validation.constants';

@Component({
    selector: 'jhi-cargo-agency-dialog',
    templateUrl: './cargo-agency-dialog.component.html'
})
export class CargoAgencyDialogComponent implements OnInit {

    cargoAgency: CargoAgency;
    authorities: any[];
    duplicateCargoAgencyName: string;
    isSaving: boolean;
    errorAlert = false;
    tmpAccountEmail: string;
    companies: Company[];
    loadComponent = false;
    officePhoneMask: string;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private cargoAgencyService: CargoAgencyService,
        private companyService: CompanyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.officePhoneMask = this.cargoAgency.officePhone;
        this.companyService.getAllCargoAgenciesByCurrentCompany()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
            this.cargoAgency.officePhone = this.officePhoneMask;
            this.isSaving = true;
            if (!this.cargoAgency.mainAddress) {
                this.cargoAgency.mainAddress = '';
            }
            if (!this.cargoAgency.additionalAddress) {
                this.cargoAgency.additionalAddress = '';
            }
            if (!this.cargoAgency.officePhone) {
                this.cargoAgency.officePhone = '';
            }
            if (!this.cargoAgency.webPage) {
                this.cargoAgency.webPage = '';
            }
            if (this.cargoAgency.id) {
                this.subscribeToSaveResponse(
                    this.cargoAgencyService.update(this.cargoAgency), false);
            } else {
                this.subscribeToSaveResponse(
                    this.cargoAgencyService.create(this.cargoAgency), true);
            }
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
        return this.cargoAgency.email && this.tmpAccountEmail
            && this.tmpAccountEmail === this.cargoAgency.email.toLowerCase().trim()
            && !this.lengthValidation(fieldData);
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

    saveButtonDeactivation(cargoAgency: CargoAgency): boolean {
        return (this.lengthValidation(cargoAgency.name) || this.latinValidation(cargoAgency.name) || this.requiredValidation(cargoAgency.name)
            || this.lengthValidation(cargoAgency.mainAddress) || this.latinValidation(cargoAgency.mainAddress)
            || this.lengthValidation(cargoAgency.additionalAddress) || this.latinValidation(cargoAgency.additionalAddress)
            || this.lengthValidation(cargoAgency.officePhone) || this.latinValidation(cargoAgency.officePhone)
            || this.lengthValidation(cargoAgency.email) || this.latinValidation(cargoAgency.email))
            || this.emailValidation(cargoAgency.email) || this.completeEmailValidation(cargoAgency.email)
            || this.duplicateValidation(cargoAgency.email) || this.lengthValidation(cargoAgency.webPage);
}

    private subscribeToSaveResponse(result: Observable<CargoAgency>, isCreated: boolean) {
        result.subscribe((res: CargoAgency) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CargoAgency, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.cargoAgency.created'
            : 'flowersApp.cargoAgency.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'cargoAgencyListModification', content: 'OK'});
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
            this.duplicateCargoAgencyName = this.cargoAgency.name.toLowerCase().trim();
        } else {
            this.alertService.error(error.message, null, null);
        }
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cargo-agency-popup',
    template: ''
})
export class CargoAgencyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cargoAgencyPopupService: CargoAgencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.modalRef = this.cargoAgencyPopupService
                    .open(CargoAgencyDialogComponent, 'cargo-agency-modal-window', params['id']);
            } else {
                this.modalRef = this.cargoAgencyPopupService
                    .open(CargoAgencyDialogComponent, 'cargo-agency-modal-window');
        }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
