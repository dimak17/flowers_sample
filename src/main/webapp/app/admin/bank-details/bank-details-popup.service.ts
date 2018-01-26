import {Injectable, Component} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {BankDetailsService} from './bank-details.service';
import {BankDetails} from '../../entities/bank-details/bank-details.model';

@Injectable()
export class BankDetailsPopupService {
    private isOpen = false;

    constructor(private modalService: NgbModal,
                private router: Router,
                private bankDetailsService: BankDetailsService) {
    }

    open(component: Component, windowClass: string, id?: number | any, type?: string, data?: number): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bankDetailsService.find(id).subscribe((bankDetails) => {
                this.bankDetailsModalRef(component, bankDetails, windowClass, type, data);
            });
        } else {
            return this.bankDetailsModalRef(component, new BankDetails(), windowClass, type, data);
        }
    }

    bankDetailsModalRef(component: Component, bankDetails: BankDetails, windowClass: string, type: string, data: number): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.bankDetails = bankDetails;
        if (data) {
            modalRef.componentInstance.data = data;
        }
        if (type) {
            modalRef.componentInstance.type = type;
        }
        modalRef.result.then((result) => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{outlets: {popup: null}}], {replaceUrl: true});
            this.isOpen = false;
        });
        return modalRef;
    }
}
