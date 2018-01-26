import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PinchVarietyProperty } from './pinch-variety-property.model';
import { PinchVarietyPropertyService } from './pinch-variety-property.service';
@Injectable()
export class PinchVarietyPropertyPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pinchVarietyPropertyService: PinchVarietyPropertyService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.pinchVarietyPropertyService.find(id).subscribe((pinchVarietyProperty) => {
                this.pinchVarietyPropertyModalRef(component, pinchVarietyProperty);
            });
        } else {
            return this.pinchVarietyPropertyModalRef(component, new PinchVarietyProperty());
        }
    }

    pinchVarietyPropertyModalRef(component: Component, pinchVarietyProperty: PinchVarietyProperty): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pinchVarietyProperty = pinchVarietyProperty;
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
