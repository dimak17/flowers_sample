import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClaimsPolicyUi } from './claimsui-policy.model';
import { ClaimsPolicyPopupService } from './claims-policy-popup.service';
import { ClaimsPolicyService } from './claims-policy.service';
import {LATIN_VALIDATION} from '../../shared';

@Component({
    selector: 'jhi-claims-policy-dialog',
    templateUrl: './claims-policy-dialog.component.html',
    styleUrls: ['./claims-policy-dialog.component.scss']
})
export class ClaimsPolicyDialogComponent implements OnInit {

    claimsPolicy: ClaimsPolicyUi;
    authorities: any[];
    dublicateClaimsPolicyName: String;
    isSaving: boolean;
    errorAlert = false;
    shortNameMax = 17;
    fullNameMax = 25;
    invalidShortName = false;
    invalidFullName = false;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private claimsPolicyService: ClaimsPolicyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        if (this.claimsPolicy && this.claimsPolicy.id) {
            this.claimsPolicy.colorClass = localStorage.getItem(this.claimsPolicy.id.toString());
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.claimsPolicy.id) {
            this.subscribeToSaveResponse(
                this.claimsPolicyService.update(this.claimsPolicy), false);
        } else {
            this.subscribeToSaveResponse(
                this.claimsPolicyService.create(this.claimsPolicy), false);
        }
    }

    private subscribeToSaveResponse(result: Observable<ClaimsPolicyUi>, isCreated: boolean) {
        result.subscribe((res: ClaimsPolicyUi) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClaimsPolicyUi, isCreated: boolean) {
        this.errorAlert = false;
        this.alertService.success(
            isCreated ? 'flowersApp.claimsPolicy.created'
                : 'flowersApp.claimsPolicy.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'claimsPolicyListModification', content: 'OK'});
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
            this.dublicateClaimsPolicyName = this.claimsPolicy.shortName.toLowerCase().trim();
        }else {
            this.alertService.error(error.message, null, null);
        }
    }

    shortNameLatinValidation(claimsTypeName: string): boolean {
        if (claimsTypeName && !this.shortNameLengthValidation(this.claimsPolicy.shortName)) {
            return !claimsTypeName.match(LATIN_VALIDATION);
        }
    }

    fullNameLatinValidation(claimsTypeName: string): boolean {
        if (claimsTypeName && !this.fullNameLengthValidation(this.claimsPolicy.fullName)) {
            return !claimsTypeName.match(LATIN_VALIDATION);
        }
    }

    shortNameLengthValidation(fieldData: string): boolean {
        if (fieldData && fieldData.length) {
            if (fieldData.length > 17) {
                this.invalidShortName = true;
                return this.invalidShortName;
            }
        }
    }

    fullNameLengthValidation(fieldData: string): boolean {
        if (fieldData && fieldData.length) {
            if (fieldData.length > 25) {
                this.invalidFullName = true;
                return this.invalidFullName;
            }
        }
    }

    requiredValidation(fieldData: string): boolean {
        if (fieldData && fieldData.trim().length === 0) {
            return true;
        }
    }
    saveButtonDeactivation(ClaimsPolicy: ClaimsPolicyUi): boolean {
        return (this.shortNameLatinValidation(ClaimsPolicy.shortName)
            || this.fullNameLatinValidation(ClaimsPolicy.fullName)
            || this.shortNameLengthValidation(ClaimsPolicy.shortName)
            || this.fullNameLengthValidation(ClaimsPolicy.fullName)
            || this.requiredValidation(ClaimsPolicy.shortName)
            || this.requiredValidation(ClaimsPolicy.fullName));
    }
}

@Component({
    selector: 'jhi-claims-policy-popup',
    template: ''
})
export class ClaimsPolicyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private claimsPolicyPopupService: ClaimsPolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.claimsPolicyPopupService
                    .open(ClaimsPolicyDialogComponent, 'claims-type-modal-window', params['id']);
            } else {
                this.modalRef = this.claimsPolicyPopupService
                    .open(ClaimsPolicyDialogComponent, 'claims-type-modal-window');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
