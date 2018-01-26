import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BoxTypeGroup } from './box-type-group.model';
import { BoxTypeGroupService } from './box-type-group.service';
@Injectable()
export class BoxTypeGroupPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private boxTypeGroupService: BoxTypeGroupService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.boxTypeGroupService.find(id).subscribe((boxTypeGroup) => {
                this.boxTypeGroupModalRef(component, boxTypeGroup);
            });
        } else {
            return this.boxTypeGroupModalRef(component, new BoxTypeGroup());
        }
    }

    boxTypeGroupModalRef(component: Component, boxTypeGroup: BoxTypeGroup): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.boxTypeGroup = boxTypeGroup;
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
