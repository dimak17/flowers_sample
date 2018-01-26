import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MarketBox } from './market-box.model';
import { MarketBoxService } from './market-box.service';
@Injectable()
export class MarketBoxPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketBoxService: MarketBoxService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.marketBoxService.find(id).subscribe((marketBox) => {
                this.marketBoxModalRef(component, marketBox);
            });
        } else {
            return this.marketBoxModalRef(component, new MarketBox());
        }
    }

    marketBoxModalRef(component: Component, marketBox: MarketBox): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketBox = marketBox;
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
