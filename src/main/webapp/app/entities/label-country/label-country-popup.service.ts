import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LabelCountry } from './label-country.model';
import { LabelCountryService } from './label-country.service';
@Injectable()
export class LabelCountryPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private labelCountryService: LabelCountryService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.labelCountryService.find(id).subscribe((labelCountry) => {
                this.labelCountryModalRef(component, labelCountry);
            });
        } else {
            return this.labelCountryModalRef(component, new LabelCountry());
        }
    }

    labelCountryModalRef(component: Component, labelCountry: LabelCountry): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.labelCountry = labelCountry;
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
