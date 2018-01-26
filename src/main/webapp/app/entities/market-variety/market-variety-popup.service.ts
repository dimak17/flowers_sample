import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MarketVariety } from './market-variety.model';
import { MarketVarietyService } from './market-variety.service';
@Injectable()
export class MarketVarietyPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketVarietyService: MarketVarietyService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.marketVarietyService.find(id).subscribe((marketVariety) => {
                this.marketVarietyModalRef(component, marketVariety);
            });
        } else {
            return this.marketVarietyModalRef(component, new MarketVariety());
        }
    }

    marketVarietyModalRef(component: Component, marketVariety: MarketVariety): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketVarieties = marketVariety;
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
