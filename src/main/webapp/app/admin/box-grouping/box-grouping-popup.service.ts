import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {BoxGroupDTO} from './box-group-dto.model';
import {BoxGroupingService} from './box-grouping.service';
import {isNumber} from '@ng-bootstrap/ng-bootstrap/util/util';

@Injectable()
export class BoxGroupingPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private boxGroupingService: BoxGroupingService
    ) {
    }

    open(component: Component, id: number | any, groupIndex: number, groupName: string, className?: string): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id && isNumber(id)) {
            this.boxGroupingService.find(id).subscribe((block) => {
                this.blockModalRef(component, block, groupIndex, groupName, className);
            });
        } else {
            return this.blockModalRef(component, new BoxGroupDTO(), groupIndex, groupName, className);
        }
    }

    blockModalRef(component: Component, boxGroup: BoxGroupDTO, groupIndex: number, groupName: string,  className?: string): NgbModalRef {
        const modalRef = this.modalService.open(component, { windowClass: className });
        modalRef.componentInstance.boxGroup = boxGroup;
        modalRef.componentInstance.groupIndex = groupIndex;
        modalRef.componentInstance.groupName = groupName;
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
