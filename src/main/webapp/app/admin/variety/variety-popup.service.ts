import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VarietyService } from './variety.service';
import {Variety} from '../../entities/variety/variety.model';
@Injectable()
export class VarietyPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private varietyService: VarietyService

    ) {}

    open(component: Component, windowClass: string, id?: number | any): NgbModalRef {

        this.isOpen = true;

        if (id) {
            this.varietyService.find(id).subscribe((variety) => {
                this.varietyModalRef(component, variety, windowClass);
            });
        } else {
            return this.varietyModalRef(component, new Variety(), windowClass);
        }
    }

    varietyModalRef(component: Component, variety: Variety, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.variety = variety;
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
