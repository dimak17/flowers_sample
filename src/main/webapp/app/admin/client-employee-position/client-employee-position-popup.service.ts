import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClientEmployeePositionService } from './client-employee-position.service';
import {ClientEmployeePosition} from '../../entities/client-employee-position/client-employee-position.model';
@Injectable()
export class ClientEmployeePositionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private clientEmployeePositionService: ClientEmployeePositionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clientEmployeePositionService.find(id).subscribe((clientEmployeePosition) => {
                this.clientEmployeePositionModalRef(component, clientEmployeePosition);
            });
        } else {
            return this.clientEmployeePositionModalRef(component, new ClientEmployeePosition());
        }
    }

    clientEmployeePositionModalRef(component: Component, clientEmployeePosition: ClientEmployeePosition): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clientEmployeePosition = clientEmployeePosition;
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
