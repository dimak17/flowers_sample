import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MarketUi } from './market-ui.model';
import { MarketService } from './market.service';
import {Market} from '../../entities/market/market.model';

@Injectable()
export class MarketPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private marketService: MarketService

    ) {}

    open(component: Component,  windowClass: string, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.marketService.find(id).subscribe((market) => {
                this.marketModalRef(component, market, windowClass);
            });
        } else {
            return this.marketModalRef(component, new Market(), windowClass);
        }
    }

    marketModalRef(component: Component, market: Market, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.market = market;
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
