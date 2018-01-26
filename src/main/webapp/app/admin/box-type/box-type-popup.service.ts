import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BoxTypeUi } from './box-type-ui.model';
import { BoxTypeService } from './box-type.service';

@Injectable()
export class BoxTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private boxTypeService: BoxTypeService

    ) {}

    open(component: Component, windowClass: string, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.boxTypeService.find(id).subscribe((boxType) => {
                this.boxTypeModalRef(component, boxType, windowClass);
            });
        } else {
            return this.boxTypeModalRef(component, new BoxTypeUi(), windowClass);
        }
    }

    boxTypeModalRef(component: Component, boxType: BoxTypeUi, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.market = boxType;
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
