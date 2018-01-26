import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {FarmDataService} from './farm-data.service';
import {Company} from '../../entities/company/company.model';

@Injectable()
export class FarmDataPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private farmDataService: FarmDataService

    ) {}

    open(component: Component, windowClass: string): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        this.farmDataService.getCurrentCompany().subscribe((company) => {
            this.companyModalRef(component, company, windowClass);
        });
    }

    companyModalRef(component: Component, company: Company, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.company = company;
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
