import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MixType } from './mix-type.model';
import { MixTypeService } from './mix-type.service';

@Injectable()
export class MixTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private mixTypeService: MixTypeService

    ) {}

    open(component: Component, windowClass: string, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.mixTypeService.find(id).subscribe((mixType) => {
                this.mixTypeModalRef(component, mixType, windowClass);
            });
        } else {
            return this.mixTypeModalRef(component, new MixType(), windowClass);
        }
    }

    mixTypeModalRef(component: Component, mixType: MixType, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component,  {windowClass});
        modalRef.componentInstance.mixType = mixType;
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
