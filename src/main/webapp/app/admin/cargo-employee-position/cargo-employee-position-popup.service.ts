import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CargoEmployeePosition } from './cargo-employee-position.model';
import { CargoEmployeePositionService } from './cargo-employee-position.service';

@Injectable()
export class CargoEmployeePositionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private cargoEmployeePositionService: CargoEmployeePositionService
    ) {}

    open(component: Component, windowClass: string, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        if (id) {
            this.cargoEmployeePositionService.find(id).subscribe((cargoEmployeePosition) => {
                this.cargoEmployeePositionModalRef(component, cargoEmployeePosition, windowClass);
            });
        } else {
            return this.cargoEmployeePositionModalRef(component, new CargoEmployeePosition(), windowClass);
        }
    }

    cargoEmployeePositionModalRef(component: Component, cargoEmployeePosition: CargoEmployeePosition, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.cargoEmployeePosition = cargoEmployeePosition;
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
