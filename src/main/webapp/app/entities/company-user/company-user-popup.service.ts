import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyUser } from './company-user.model';
import { CompanyUserService } from './company-user.service';
@Injectable()
export class CompanyUserPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private companyUserService: CompanyUserService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.companyUserService.find(id).subscribe((companyUser) => {
                this.companyUserModalRef(component, companyUser);
            });
        } else {
            return this.companyUserModalRef(component, new CompanyUser());
        }
    }

    companyUserModalRef(component: Component, companyUser: CompanyUser): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyUser = companyUser;
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
