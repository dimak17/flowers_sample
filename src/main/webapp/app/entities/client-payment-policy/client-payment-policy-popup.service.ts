import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClientPaymentPolicy } from './client-payment-policy.model';
import { ClientPaymentPolicyService } from './client-payment-policy.service';
@Injectable()
export class ClientPaymentPolicyPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private clientPaymentPolicyService: ClientPaymentPolicyService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clientPaymentPolicyService.find(id).subscribe((clientPaymentPolicy) => {
                this.clientPaymentPolicyModalRef(component, clientPaymentPolicy);
            });
        } else {
            return this.clientPaymentPolicyModalRef(component, new ClientPaymentPolicy());
        }
    }

    clientPaymentPolicyModalRef(component: Component, clientPaymentPolicy: ClientPaymentPolicy): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clientPaymentPolicy = clientPaymentPolicy;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
