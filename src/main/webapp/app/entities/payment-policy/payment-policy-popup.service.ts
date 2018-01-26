import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PaymentPolicy } from './payment-policy.model';
import { PaymentPolicyService } from './payment-policy.service';
@Injectable()
export class PaymentPolicyPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private paymentPolicyService: PaymentPolicyService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.paymentPolicyService.find(id).subscribe((paymentPolicy) => {
                this.paymentPolicyModalRef(component, paymentPolicy);
            });
        } else {
            return this.paymentPolicyModalRef(component, new PaymentPolicy());
        }
    }

    paymentPolicyModalRef(component: Component, paymentPolicy: PaymentPolicy): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.paymentPolicy = paymentPolicy;
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
