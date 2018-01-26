import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AirLines } from './air-lines.model';
import { AirLinesService } from './air-lines.service';

@Injectable()
export class AirLinesPopupService {

    private isOpen = false;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private airLinesService: AirLinesService

    ) {}

    open(component: Component, windowClass: string, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.airLinesService.find(id).subscribe((airLines) => {
                this.airLinesModalRef(component, airLines, windowClass);
            });
        } else {
            return this.airLinesModalRef(component, new AirLines(), windowClass);
        }
    }

    airLinesModalRef(component: Component, airLines: AirLines, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.airLines = airLines;
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
