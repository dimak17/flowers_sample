import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TypeOfFlower } from './type-of-flower.model';
import { TypeOfFlowerService } from './type-of-flower.service';
@Injectable()
export class TypeOfFlowerPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private typeOfFlowerService: TypeOfFlowerService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.typeOfFlowerService.find(id).subscribe((typeOfFlower) => {
                this.typeOfFlowerModalRef(component, typeOfFlower);
            });
        } else {
            return this.typeOfFlowerModalRef(component, new TypeOfFlower());
        }
    }

    typeOfFlowerModalRef(component: Component, typeOfFlower: TypeOfFlower): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.typeOfFlower = typeOfFlower;
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
