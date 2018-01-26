import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MarketSeasonVarietyProperty } from './market-season-variety-property.model';
import { MarketSeasonVarietyPropertyService } from './market-season-variety-property.service';

@Injectable()
export class MarketSeasonVarietyPropertyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketSeasonVarietyPropertyService: MarketSeasonVarietyPropertyService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.marketSeasonVarietyPropertyService.find(id).subscribe((marketSeasonVarietyProperty) => {
                    this.ngbModalRef = this.marketSeasonVarietyPropertyModalRef(component, marketSeasonVarietyProperty);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.marketSeasonVarietyPropertyModalRef(component, new MarketSeasonVarietyProperty());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    marketSeasonVarietyPropertyModalRef(component: Component, marketSeasonVarietyProperty: MarketSeasonVarietyProperty): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketSeasonVarietyProperty = marketSeasonVarietyProperty;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
