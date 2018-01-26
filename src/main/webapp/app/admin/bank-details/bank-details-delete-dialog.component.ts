import {Component, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {EventManager} from 'ng-jhipster';

import {BankDetailsPopupService} from './bank-details-popup.service';
import {BankDetails} from '../../entities/bank-details/bank-details.model';
import {BankDetailsService} from './bank-details.service';

@Component({
    selector: 'jhi-bank-details-delete-dialog',
    templateUrl: './bank-details-delete-dialog.component.html',
    styleUrls: ['./bank-details-delete-dialog.component.scss'],
    providers: [BankDetailsService]
})
export class BankDetailsDeleteDialogComponent {

    bankDetails: BankDetails;
    type: string;

    constructor(
        public activeModal: NgbActiveModal,
        private eventManager: EventManager,
        private bankDetailsService: BankDetailsService
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(type: string, id: number) {
        this.bankDetailsService.deleteUploadedFile(type, id).subscribe(
            (res) => {
                this.eventManager.broadcast({name: 'bankDetailsChanged', status: res.status, type});
            });
        this.clear();
    }
}

@Component({
    selector: 'jhi-bank-details-delete-popup',
    template: ''
})
export class BankDetailsDeletePopupComponent implements OnInit, OnDestroy {

    @ViewChild('modal-template') static modalTemplate: TemplateRef<any>;

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bankDetailsPopupService: BankDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bankDetailsPopupService
                .open(BankDetailsDeleteDialogComponent, 'delete-bank-details',  params['id']
                , params['type']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
