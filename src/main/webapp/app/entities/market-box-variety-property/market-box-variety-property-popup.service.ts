import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MarketBoxVarietyProperty } from './market-box-variety-property.model';
import { MarketBoxVarietyPropertyService } from './market-box-variety-property.service';
@Injectable()
export class MarketBoxVarietyPropertyPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketBoxVarietyPropertyService: MarketBoxVarietyPropertyService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.marketBoxVarietyPropertyService.find(id).subscribe((marketBoxVarietyProperty) => {
                this.marketBoxVarietyPropertyModalRef(component, marketBoxVarietyProperty);
            });
        } else {
            return this.marketBoxVarietyPropertyModalRef(component, new MarketBoxVarietyProperty());
        }
    }

    marketBoxVarietyPropertyModalRef(component: Component, marketBoxVarietyProperty: MarketBoxVarietyProperty): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketBoxVarietyProperty = marketBoxVarietyProperty;
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
