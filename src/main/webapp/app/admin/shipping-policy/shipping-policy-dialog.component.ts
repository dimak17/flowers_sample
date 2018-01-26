import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ShippingPolicyUi } from './shippingui-policy.model';
import { ShippingPolicyPopupService } from './shipping-policy-popup.service';
import { ShippingPolicyService } from './shipping-policy.service';
import {LATIN_VALIDATION} from '../../shared';

@Component({
    selector: 'jhi-shipping-policy-dialog',
    templateUrl: './shipping-policy-dialog.component.html',
    styleUrls: ['./shipping-policy-dialog.component.scss']
})
export class ShippingPolicyDialogComponent implements OnInit {

    shippingPolicy: ShippingPolicyUi;
    authorities: any[];
    dublicateShippingPolicyName: String;
    isSaving: boolean;
    errorAlert = false;
    shortNameMax = 17;
    fullNameMax = 25;
    invalidShortName = false;
    invalidFullName = false;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private shippingPolicyService: ShippingPolicyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        if (this.shippingPolicy && this.shippingPolicy.id) {
            this.shippingPolicy.colorClass = localStorage.getItem(this.shippingPolicy.id.toString());
        }
        this.dublicateShippingPolicyName = '';
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shippingPolicy.id) {
            this.subscribeToSaveResponse(
                this.shippingPolicyService.update(this.shippingPolicy), false);
        } else {
            this.subscribeToSaveResponse(
                this.shippingPolicyService.create(this.shippingPolicy), false);
        }
    }

    private subscribeToSaveResponse(result: Observable<ShippingPolicyUi>, isCreated: boolean) {
        result.subscribe((res: ShippingPolicyUi) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ShippingPolicyUi, isCreated: boolean) {
        this.errorAlert = false;
        this.alertService.success(
            isCreated ? 'flowersApp.shippingPolicy.created'
                : 'flowersApp.shippingPolicy.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'shippingPolicyListModification', content: 'OK'});
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
            this.dublicateShippingPolicyName = this.shippingPolicy.shortName.toLowerCase().trim();
            this.errorAlert = true;
        }else {
            this.alertService.error(error.message, null, null);
        }
    }

    shortNameLatinValidation(shippingTypeName: string): boolean {
        if (shippingTypeName && !this.shortNameLengthValidation(this.shippingPolicy.shortName)) {
            return !shippingTypeName.match(LATIN_VALIDATION);
            }

    }

    fullNameLatinValidation(shippingTypeName: string): boolean {
        if (shippingTypeName && !this.fullNameLengthValidation(this.shippingPolicy.fullName)) {
            return !shippingTypeName.match(LATIN_VALIDATION);
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

    saveButtonDeactivation(ShippingPolicy: ShippingPolicyUi): boolean {
        return (this.shortNameLatinValidation(ShippingPolicy.shortName)
            || this.fullNameLatinValidation(ShippingPolicy.fullName)
            || this.shortNameLengthValidation(ShippingPolicy.shortName)
            || this.fullNameLengthValidation(ShippingPolicy.fullName)
            || this.requiredValidation(ShippingPolicy.shortName)
            || this.requiredValidation(ShippingPolicy.fullName));
    }
}

@Component({
    selector: 'jhi-shipping-policy-popup',
    template: ''
})
export class ShippingPolicyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shippingPolicyPopupService: ShippingPolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.shippingPolicyPopupService
                    .open(ShippingPolicyDialogComponent, 'shipping-type-modal-window', params['id']);
            } else {
                this.modalRef = this.shippingPolicyPopupService
                    .open(ShippingPolicyDialogComponent, 'shipping-type-modal-window');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

}
