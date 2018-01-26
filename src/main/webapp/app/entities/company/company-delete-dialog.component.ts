import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Company } from './company.model';
import { CompanyPopupService } from './company-popup.service';
import { CompanyService } from './company.service';

@Component({
    selector: 'jhi-company-delete-dialog',
    templateUrl: './company-delete-dialog.component.html'
})
export class CompanyDeleteDialogComponent {

    company: Company;

    constructor(
        private companyService: CompanyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyListModification',
                content: 'Deleted an company'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.company.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-company-delete-popup',
    template: ''
})
export class CompanyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyPopupService: CompanyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.companyPopupService
                .open(CompanyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
