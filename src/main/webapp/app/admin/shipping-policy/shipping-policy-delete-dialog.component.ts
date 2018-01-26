import {Component, OnInit, OnDestroy, ViewChild, TemplateRef} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager} from 'ng-jhipster';
import { ShippingPolicyUi } from './shippingui-policy.model';
import { ShippingPolicyPopupService } from './shipping-policy-popup.service';
import { ShippingPolicyService } from './shipping-policy.service';

@Component({
    selector: 'jhi-shipping-policy-delete-dialog',
    templateUrl: './shipping-policy-delete-dialog.component.html',
    styleUrls: ['./shipping-policy-delete-dialog.component.scss']
})
export class ShippingPolicyDeleteDialogComponent {

    shippingPolicy: ShippingPolicyUi;

    constructor(
        private shippingPolicyService: ShippingPolicyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shippingPolicyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shippingPolicyListModification',
                content: 'Deleted an shippingPolicy'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.shippingPolicy.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-shipping-policy-delete-popup',
    template: ''
})
export class ShippingPolicyDeletePopupComponent implements OnInit, OnDestroy {

    @ViewChild('modal-template') static modalTemplate: TemplateRef<any>;

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shippingPolicyPopupService: ShippingPolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.shippingPolicyPopupService
                .open(ShippingPolicyDeleteDialogComponent, 'delete-shippingPolicy', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
