import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {BankDetailsPopupService} from './bank-details-popup.service';
import {Company} from '../../entities/company/company.model';
import {BankDetails} from '../../entities/bank-details/bank-details.model';
import {BankDetailsComponent} from './bank-details.component';
import {BankDetailsService} from './bank-details.service';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'jhi-bank-details-preview-dialog',
    templateUrl: './bank-details-preview-dialog.component.html',
    styleUrls: ['/bank-details-dialog.component.scss'],
    providers: [BankDetailsComponent, BankDetailsService]
})
export class BankDetailsDialogComponentPreview implements OnInit {

    bankDetails: BankDetails;
    authorities: any[];
    companies: Company[];
    data: number;
    loading = false;
    pdfSrc: any;

    constructor(public activeModal: NgbActiveModal,
                private bankDetailsService: BankDetailsService) {
    }

    ngOnInit() {
        this.loading = true;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.showFile();
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    showFile() {
        this.bankDetailsService.showPDF(this.data)
            .finally(() => {
            this.loading = false;
        })
            .subscribe(
            (res) => {
                const fileURL = URL.createObjectURL(res);
                this.pdfSrc = fileURL;
                console.log(fileURL);
            }
        );
    }
}

@Component({
    selector: 'jhi-bank-details-preview-popup',
    template: ''
})
export class BankDetailsPopupComponentPreview implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private bankDetailsPopupService: BankDetailsPopupService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bankDetailsPopupService
                .open(BankDetailsDialogComponentPreview, 'modal-body-preview', null, null, params['data']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
