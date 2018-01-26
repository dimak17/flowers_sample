import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CargoEmployee } from './cargo-employee.model';
import { CargoEmployeeService } from './cargo-employee.service';

@Injectable()
export class CargoEmployeePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private cargoEmployeeService: CargoEmployeeService
    ) {}

    open(component: Component, windowClass: string, cargoEmployeeId?: number | any, cargoAgencyId?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        if (cargoEmployeeId) {
            if (cargoAgencyId) {
                this.cargoEmployeeService.find(cargoEmployeeId, cargoAgencyId).subscribe((cargoEmployee) => {
                    this.cargoEmployeeModalRef(component, cargoEmployee, windowClass);
                });
            } else {
                this.cargoEmployeeService.findOne(cargoEmployeeId).subscribe((cargoEmployee) => {
                    this.cargoEmployeeModalRef(component, cargoEmployee, windowClass);
                });
            }
        } else {
            return this.cargoEmployeeModalRef(component, new CargoEmployee(), windowClass);
        }
    }

    cargoEmployeeModalRef(component: Component, cargoEmployee: CargoEmployee, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.cargoEmployee = cargoEmployee;
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
