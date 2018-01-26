import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {EmployeeListService} from './employee-list.service';
import {CompanyUser} from '../../entities/company-user/company-user.model';

@Injectable()
export class EmployeeListPopupService {

    private isOpen = false;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private employeeListService: EmployeeListService
    ) {
    }

    open(component: Component, windowClass: string, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.employeeListService.find(id).subscribe((companyUser) => {
                this.companyUserModalRef(component, companyUser, windowClass);
            });
        } else {
            return this.companyUserModalRef(component, new CompanyUser(), windowClass);
        }
    }

    companyUserModalRef(component: Component, companyUser: CompanyUser,  windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
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
