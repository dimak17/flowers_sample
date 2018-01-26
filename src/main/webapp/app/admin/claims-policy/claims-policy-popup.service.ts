import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClaimsPolicyUi } from './claimsui-policy.model';
import { ClaimsPolicyService } from './claims-policy.service';

@Injectable()
export class ClaimsPolicyPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private claimsPolicyService: ClaimsPolicyService

    ) {}

    open(component: Component,  windowClass: string, id?: number | any): NgbModalRef {

        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.claimsPolicyService.find(id).subscribe((claimsPolicy) => {
                this.claimsPolicyModalRef(component, claimsPolicy, windowClass);
            });
        } else {
            return this.claimsPolicyModalRef(component, new ClaimsPolicyUi(), windowClass);
        }
    }

    claimsPolicyModalRef(component: Component, claimsPolicy: ClaimsPolicyUi, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.claimsPolicy = claimsPolicy;
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
