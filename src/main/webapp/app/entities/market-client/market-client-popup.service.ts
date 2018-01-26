import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MarketClient } from './market-client.model';
import { MarketClientService } from './market-client.service';
@Injectable()
export class MarketClientPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketClientService: MarketClientService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.marketClientService.find(id).subscribe((marketClient) => {
                this.marketClientModalRef(component, marketClient);
            });
        } else {
            return this.marketClientModalRef(component, new MarketClient());
        }
    }

    marketClientModalRef(component: Component, marketClient: MarketClient): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketClient = marketClient;
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
