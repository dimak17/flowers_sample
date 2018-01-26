import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { PaymentPolicy } from './payment-policy.model';
import { PaymentPolicyPopupService } from './payment-policy-popup.service';
import { PaymentPolicyService } from './payment-policy.service';

@Component({
    selector: 'jhi-payment-policy-delete-dialog',
    templateUrl: './payment-policy-delete-dialog.component.html'
})
export class PaymentPolicyDeleteDialogComponent {

    paymentPolicy: PaymentPolicy;

    constructor(
        private paymentPolicyService: PaymentPolicyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paymentPolicyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'paymentPolicyListModification',
                content: 'Deleted an paymentPolicy'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.paymentPolicy.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-payment-policy-delete-popup',
    template: ''
})
export class PaymentPolicyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paymentPolicyPopupService: PaymentPolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.paymentPolicyPopupService
                .open(PaymentPolicyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
