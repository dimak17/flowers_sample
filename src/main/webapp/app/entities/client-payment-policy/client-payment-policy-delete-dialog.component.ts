import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { ClientPaymentPolicy } from './client-payment-policy.model';
import { ClientPaymentPolicyPopupService } from './client-payment-policy-popup.service';
import { ClientPaymentPolicyService } from './client-payment-policy.service';

@Component({
    selector: 'jhi-client-payment-policy-delete-dialog',
    templateUrl: './client-payment-policy-delete-dialog.component.html'
})
export class ClientPaymentPolicyDeleteDialogComponent {

    clientPaymentPolicy: ClientPaymentPolicy;

    constructor(
        private clientPaymentPolicyService: ClientPaymentPolicyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientPaymentPolicyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clientPaymentPolicyListModification',
                content: 'Deleted an clientPaymentPolicy'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.clientPaymentPolicy.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-client-payment-policy-delete-popup',
    template: ''
})
export class ClientPaymentPolicyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientPaymentPolicyPopupService: ClientPaymentPolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clientPaymentPolicyPopupService
                .open(ClientPaymentPolicyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
