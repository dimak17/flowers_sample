import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CargoAgency } from './cargo-agency.model';
import { CargoAgencyService } from './cargo-agency.service';

@Injectable()
export class CargoAgencyPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private cargoAgencyService: CargoAgencyService,
    ) {}

    open(component: Component, windowClass: string, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        if (id) {
            this.cargoAgencyService.find(id).subscribe((cargoAgency) => {
                this.cargoAgencyModalRef(component, cargoAgency, windowClass);
            });
        } else {
            return this.cargoAgencyModalRef(component, new CargoAgency(), windowClass);
        }
    }

    cargoAgencyModalRef(component: Component, cargoAgency: CargoAgency, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.cargoAgency = cargoAgency;
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
