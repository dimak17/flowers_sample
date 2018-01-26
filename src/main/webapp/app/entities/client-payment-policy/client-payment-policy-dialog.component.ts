import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClientPaymentPolicy } from './client-payment-policy.model';
import { ClientPaymentPolicyPopupService } from './client-payment-policy-popup.service';
import { ClientPaymentPolicyService } from './client-payment-policy.service';
import { PaymentPolicy, PaymentPolicyService } from '../payment-policy';
import { ResponseWrapper } from '../../shared';
import {Client} from '../client/client.model';
import {ClientService} from '../../admin/client/client.service';

@Component({
    selector: 'jhi-client-payment-policy-dialog',
    templateUrl: './client-payment-policy-dialog.component.html'
})
export class ClientPaymentPolicyDialogComponent implements OnInit {

    clientPaymentPolicy: ClientPaymentPolicy;
    authorities: any[];
    isSaving: boolean;

    clients: Client[];

    paymentpolicies: PaymentPolicy[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clientPaymentPolicyService: ClientPaymentPolicyService,
        private clientService: ClientService,
        private paymentPolicyService: PaymentPolicyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clientService.query()
            .subscribe((res: ResponseWrapper) => { this.clients = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.paymentPolicyService.query()
            .subscribe((res: ResponseWrapper) => { this.paymentpolicies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clientPaymentPolicy.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientPaymentPolicyService.update(this.clientPaymentPolicy), false);
        } else {
            this.subscribeToSaveResponse(
                this.clientPaymentPolicyService.create(this.clientPaymentPolicy), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<ClientPaymentPolicy>, isCreated: boolean) {
        result.subscribe((res: ClientPaymentPolicy) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClientPaymentPolicy, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.clientPaymentPolicy.created'
            : 'flowersApp.clientPaymentPolicy.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'clientPaymentPolicyListModification', content: 'OK'});
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

    trackClientById(index: number, item: Client) {
        return item.id;
    }

    trackPaymentPolicyById(index: number, item: PaymentPolicy) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-client-payment-policy-popup',
    template: ''
})
export class ClientPaymentPolicyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientPaymentPolicyPopupService: ClientPaymentPolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clientPaymentPolicyPopupService
                    .open(ClientPaymentPolicyDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientPaymentPolicyPopupService
                    .open(ClientPaymentPolicyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
