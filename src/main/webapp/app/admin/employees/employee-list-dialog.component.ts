import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {EventManager, AlertService, JhiLanguageService} from 'ng-jhipster';

import { EmployeeListPopupService } from './employee-list-popup.service';
import { User } from '../../shared';
import {CompanyUser} from '../../entities/company-user/company-user.model';
import {Company} from '../../entities/company/company.model';
import {EmployeeListService} from './employee-list.service';
import {Position} from '../../entities/position/position.model';
import {SelectItem} from 'primeng/primeng';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {EmployeeListDialogOnLangChangeService} from './employee-list-dialog-on-lang-change.service';
import {
    EMAIL_FIRST_PART_VALIDATION, EMAIL_VALIDATION,
    LATIN_VALIDATION, PHONE_VALIDATION
} from '../../shared/constants/validation.constants';
import {Market} from '../../entities/market/market.model';

@Component({
    selector: 'jhi-employee-user-dialog',
    templateUrl: './employee-list-dialog.component.html'
})
export class EmployeeListDialogComponent implements OnInit {

    companyUser: CompanyUser;
    currentCompanyUser: CompanyUser;
    authorities: any[];
    isSaving: boolean;
    checked = true;
    positions: Position[];
    markets: Market[];
    companyUserPositions: SelectItem[] = [];
    companyUserMarkets: SelectItem[] = [];
    languagePositions: Array<any>[] = [];
    positionsIdSelected: string [] = [];
    marketsIdSelected: string [] = [];
    defaultLabel = '';
    defaultMarketsLabel = '';
    tmpAccountEmail: string;
    accountTmp: string;
    fullNameTmp: string;

    actSwitch: string;
    deactSwitch: string;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private employeeListService: EmployeeListService,
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private employeeListDialogLangChangeService: EmployeeListDialogOnLangChangeService
    ) {
        this.jhiLanguageService.setLocations(['companyUser']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.tmpAccountEmail = '';
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.employeeListService.getAllPositionsByCompany()
            .subscribe((positions: Position[]) => {
                this.positions = positions;
            });
        this.employeeListService.getAllMarketsByCurrentCompany()
            .subscribe((markets: Market[]) => {
                this.markets = markets;
                this.markets.forEach((market) => {
                    this.companyUserMarkets.push({label: ' '.concat(market.name), value: market.id});
                });
            });

        this.employeeListService.getCurrentUserByCurrentCompany().subscribe((current: CompanyUser) => {
            this.currentCompanyUser = current;
            this.accountTmp = this.currentCompanyUser.accountEmail;
            this.fullNameTmp = this.currentCompanyUser.fullName;
        });

        if (this.companyUser && this.companyUser.user && !this.companyUser.user.activated) {
            this.checked = false;
        }

        if (this.companyUser && this.companyUser.positions) {
            this.companyUser.positions.forEach((p) => {
                this.positionsIdSelected.push(p.id.toString());
            });
         }

         if (this.companyUser && this.companyUser.markets) {
            this.companyUser.markets.forEach((m) => {
                this.marketsIdSelected.push(m.id.toString());
            });
         }

        this.jhiLanguageService.getCurrent().then((curentLang: string) => {
            this.employeeListDialogLangChangeService.onLangChange(curentLang);
            this.defaultLabel = this.employeeListDialogLangChangeService.defaultLabel;
            this.defaultMarketsLabel = this.employeeListDialogLangChangeService.defaultMarketsLabel;
            this.languagePositions = this.employeeListDialogLangChangeService.positionsMap;
            this.actSwitch = this.employeeListDialogLangChangeService.actSwitch;
            this.deactSwitch = this.employeeListDialogLangChangeService.deactSwitch;
            this.languagePositions.forEach((obj) => {
                let id: number;
                let positionName: string;

                for (const key in obj) {
                    if (typeof obj[key] === 'number') {
                        id = obj[key];
                    }
                    if (typeof obj[key] === 'string') {
                        positionName = obj[key];
                        this.companyUserPositions.push({label: ' '.concat(positionName), value: id});
                    }
                }
            });
        });
        this.languageHelper.addListener(this.employeeListDialogLangChangeService);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.setSelectedPositions();
        this.setSelectedMarkets();
        if (!this.companyUser.skype) {
            this.companyUser.skype = '';
        }
        if (!this.companyUser.mobilePhone) {
            this.companyUser.mobilePhone = '';
        }
        if (!this.companyUser.officePhone) {
            this.companyUser.officePhone = '';
        }
        if (this.companyUser.id) {
            this.subscribeToSaveResponse(
                this.employeeListService.update(this.companyUser), false);
        } else {
            this.subscribeToSaveResponse(
                this.employeeListService.create(this.companyUser), true);
        }
    }

    private setSelectedPositions() {
        this.companyUser.positions = [];

        if (this.positionsIdSelected) {
            this.positionsIdSelected.forEach((id) => {
                this.positions.forEach((p) => {
                    if (+id === p.id) {
                        this.companyUser.positions.push(p);
                    }
                });
            });
        }
    }

    private setSelectedMarkets() {
        this.companyUser.markets = [];

        if (this.marketsIdSelected) {
            this.marketsIdSelected.forEach((id) => {
                this.markets.forEach((p) => {
                    if (+id === p.id) {
                        this.companyUser.markets.push(p);
                    }
                });
            });
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyUser>, isCreated: boolean) {
        result.subscribe((res: CompanyUser) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CompanyUser, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.companyUser.created'
            : 'flowersApp.companyUser.updated',
            { param : result.id }, null);

        if (this.currentCompanyUser.id === result.id) {
            if (this.accountTmp !== result.accountEmail) {
                this.eventManager.broadcast({name: 'changedAccountEmail', content: 'OK'});
            } else if (this.fullNameTmp !== result.fullName){
                this.eventManager.broadcast({name: 'changedFullName', content: result});
                this.eventManager.broadcast({name: 'companyUserListModification', content: result});
            } else {
                this.eventManager.broadcast({name: 'companyUserListModification', content: result});
            }
        } else {
            this.eventManager.broadcast({name: 'companyUserListModification', content: this.companyUser});
        }
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
            this.tmpAccountEmail = this.companyUser.accountEmail.toLowerCase().trim();
        } else {
            this.alertService.error(error.message, null, null);
        }
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackPositionById(index: number, item: Position) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }

    handleStatusChange(event) {
        this.checked = event.checked;
        if (this.checked === true) {
            this.companyUser.user.activated = true;
        } else {
            this.companyUser.user.activated = false;
        }
    }

    requiredValidation(fieldData: string) {
        return !fieldData || !this.isFillValidation(fieldData);
    }

    isFillValidation(fieldData: any): boolean {
        return fieldData && fieldData.toString().trim();
    }

    latinValidation(fieldData: string): boolean {
        if (fieldData && !this.lengthValidation(fieldData)) {
            return !fieldData.match(LATIN_VALIDATION);
        }
    }

    lengthValidation(fieldData: string): boolean {
        if (fieldData && fieldData.length) {
            return fieldData.length > 50;
        }
    }

    emailValidation(fieldData: string): boolean {
        if (fieldData && !this.lengthValidation(fieldData)) {
            return fieldData.indexOf('@') === -1;
        }
    }

    duplicateValidation(fieldData: string): boolean {
        return this.companyUser.accountEmail && this.tmpAccountEmail
            && this.tmpAccountEmail === this.companyUser.accountEmail.toLowerCase().trim()
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

    multiselectValidation(selection: string []): boolean {
        return selection.length === 0;
    }

    mobilePhoneValidation(fieldData: string): boolean {
        if (fieldData && !this.lengthValidation(fieldData)) {
            return !fieldData.match(PHONE_VALIDATION);
        }
    }

    companyUserValidation(companyUser: CompanyUser): boolean {

        if (!companyUser.user) {
            return false;
        }
        if (this.currentCompanyUser && this.currentCompanyUser.user && this.currentCompanyUser.user.id) {
            return companyUser.user.id !== this.currentCompanyUser.user.id;
        }
    }

    saveButtonDeactive(companyUser: CompanyUser): boolean {
        return this.requiredValidation(companyUser.fullName) || this.requiredValidation(companyUser.accountEmail)
            || this.requiredValidation(companyUser.workEmail) || this.multiselectValidation(this.positionsIdSelected)
            || this.multiselectValidation(this.marketsIdSelected) || this.mobilePhoneValidation(companyUser.mobilePhone)
            || this.latinValidation(companyUser.fullName) || this.lengthValidation(companyUser.officePhone)
            || this.lengthValidation(companyUser.fullName) ||  this.lengthValidation(companyUser.accountEmail)
            || this.lengthValidation(companyUser.workEmail) || this.lengthValidation(companyUser.skype)
            || this.lengthValidation(companyUser.mobilePhone) || this.lengthValidation(companyUser.officePhone)
            || this.emailValidation(companyUser.accountEmail) || this.emailValidation(companyUser.workEmail)
            || this.completeEmailValidation(companyUser.accountEmail) || this.completeEmailValidation(companyUser.workEmail)
            || this.duplicateValidation(companyUser.accountEmail);
    }
}

@Component({
    selector: 'jhi-employee-user-popup',
    template: ''
})
export class EmployeeListPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeListPopupService: EmployeeListPopupService,
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.employeeListPopupService
                    .open(EmployeeListDialogComponent, 'employee-update-modal-window', params['id']);
            } else {
                this.modalRef = this.employeeListPopupService
                    .open(EmployeeListDialogComponent, 'employee-create-modal-window');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
