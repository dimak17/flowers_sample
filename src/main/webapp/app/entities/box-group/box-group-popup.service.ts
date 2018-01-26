import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BoxGroup } from './box-group.model';
import { BoxGroupService } from './box-group.service';
@Injectable()
export class BoxGroupPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private boxGroupService: BoxGroupService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.boxGroupService.find(id).subscribe((boxGroup) => {
                this.boxGroupModalRef(component, boxGroup);
            });
        } else {
            return this.boxGroupModalRef(component, new BoxGroup());
        }
    }

    boxGroupModalRef(component: Component, boxGroup: BoxGroup): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.boxGroup = boxGroup;
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
