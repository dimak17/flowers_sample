import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PinchService } from './pinch.service';
import {Pinch} from '../../entities/pinch/pinch.model';

@Injectable()
export class PinchPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pinchService: PinchService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.pinchService.find(id).subscribe((pinch) => {
                if (pinch.startDate) {
                    pinch.startDate = {
                        year: pinch.startDate.getFullYear(),
                        month: pinch.startDate.getMonth() + 1,
                        day: pinch.startDate.getDate()
                    };
                }
                if (pinch.endDate) {
                    pinch.endDate = {
                        year: pinch.endDate.getFullYear(),
                        month: pinch.endDate.getMonth() + 1,
                        day: pinch.endDate.getDate()
                    };
                }
                if (pinch.notifyStartDate) {
                    pinch.notifyStartDate = {
                        year: pinch.notifyStartDate.getFullYear(),
                        month: pinch.notifyStartDate.getMonth() + 1,
                        day: pinch.notifyStartDate.getDate()
                    };
                }
                this.pinchModalRef(component, pinch);
            });
        } else {
            return this.pinchModalRef(component, new Pinch());
        }
    }

    pinchModalRef(component: Component, pinch: Pinch): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pinch = pinch;
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
