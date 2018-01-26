import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MarketVarietyProperty } from './market-variety-property.model';
import { MarketVarietyPropertyService } from './market-variety-property.service';

@Injectable()
export class MarketVarietyPropertyPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketVarietyPropertyService: MarketVarietyPropertyService

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
                this.marketVarietyPropertyService.find(id).subscribe((marketVarietyProperty) => {
                    this.ngbModalRef = this.marketVarietyPropertyModalRef(component, marketVarietyProperty);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.marketVarietyPropertyModalRef(component, new MarketVarietyProperty());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    marketVarietyPropertyModalRef(component: Component, marketVarietyProperty: MarketVarietyProperty): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketVarietyProperty = marketVarietyProperty;
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
