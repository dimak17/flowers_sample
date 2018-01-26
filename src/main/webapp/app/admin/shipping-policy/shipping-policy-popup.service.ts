import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ShippingPolicyUi } from './shippingui-policy.model';
import { ShippingPolicyService } from './shipping-policy.service';

@Injectable()
export class ShippingPolicyPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private shippingPolicyService: ShippingPolicyService

    ) {}

    open(component: Component,  windowClass: string, id?: number | any): NgbModalRef {

        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.shippingPolicyService.find(id).subscribe((shippingPolicy) => {
                this.shippingPolicyModalRef(component, shippingPolicy, windowClass);
            });
        } else {
            return this.shippingPolicyModalRef(component, new ShippingPolicyUi(), windowClass);
        }
    }

    shippingPolicyModalRef(component: Component, shippingPolicy: ShippingPolicyUi, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.shippingPolicy = shippingPolicy;
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
