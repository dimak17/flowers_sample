import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PaymentPolicy } from './payment-policy.model';
import { PaymentPolicyPopupService } from './payment-policy-popup.service';
import { PaymentPolicyService } from './payment-policy.service';
import { Company, CompanyService } from '../company';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-payment-policy-dialog',
    templateUrl: './payment-policy-dialog.component.html'
})
export class PaymentPolicyDialogComponent implements OnInit {

    paymentPolicy: PaymentPolicy;
    authorities: any[];
    isSaving: boolean;

    companies: Company[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private paymentPolicyService: PaymentPolicyService,
        private companyService: CompanyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.paymentPolicy.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paymentPolicyService.update(this.paymentPolicy), false);
        } else {
            this.subscribeToSaveResponse(
                this.paymentPolicyService.create(this.paymentPolicy), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PaymentPolicy>, isCreated: boolean) {
        result.subscribe((res: PaymentPolicy) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PaymentPolicy, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.paymentPolicy.created'
            : 'flowersApp.paymentPolicy.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'paymentPolicyListModification', content: 'OK'});
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
        this.alertService.error(error.message, null, null);
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-payment-policy-popup',
    template: ''
})
export class PaymentPolicyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paymentPolicyPopupService: PaymentPolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.paymentPolicyPopupService
                    .open(PaymentPolicyDialogComponent, params['id']);
            } else {
                this.modalRef = this.paymentPolicyPopupService
                    .open(PaymentPolicyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
