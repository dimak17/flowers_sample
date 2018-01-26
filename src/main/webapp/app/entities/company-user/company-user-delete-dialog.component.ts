import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { CompanyUser } from './company-user.model';
import { CompanyUserPopupService } from './company-user-popup.service';
import { CompanyUserService } from './company-user.service';

@Component({
    selector: 'jhi-company-user-delete-dialog',
    templateUrl: './company-user-delete-dialog.component.html'
})
export class CompanyUserDeleteDialogComponent {

    companyUser: CompanyUser;

    constructor(
        private companyUserService: CompanyUserService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyUserListModification',
                content: 'Deleted an companyUser'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.companyUser.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-company-user-delete-popup',
    template: ''
})
export class CompanyUserDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyUserPopupService: CompanyUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.companyUserPopupService
                .open(CompanyUserDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
