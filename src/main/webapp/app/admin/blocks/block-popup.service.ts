import {Component, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {BlockService} from './block.service';
import {Block} from '../../entities/block/block.model';

@Injectable()
export class BlockPopupService {

    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private blockService: BlockService

    ) {}

    open(component: Component, windowClass: string, idBlock?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if  (idBlock) {
            this.blockService.findBlockById(idBlock).subscribe((block) => {
                this.companyModalRef(component, block, windowClass);
            } );
        } else {
            return this.companyModalRef(component, null, windowClass);
        }
    }

    companyModalRef(component: Component, block: Block, windowClass: string): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.block = block;
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
