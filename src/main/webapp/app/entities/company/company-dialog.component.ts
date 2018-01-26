import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Company } from './company.model';
import { CompanyPopupService } from './company-popup.service';
import { CompanyService } from './company.service';

@Component({
    selector: 'jhi-company-dialog',
    templateUrl: './company-dialog.component.html'
})
export class CompanyDialogComponent implements OnInit {

    company: Company;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private companyService: CompanyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.company.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyService.update(this.company), false);
        } else {
            this.subscribeToSaveResponse(
                this.companyService.create(this.company), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Company>, isCreated: boolean) {
        result.subscribe((res: Company) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Company, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.company.created'
            : 'flowersApp.company.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'companyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-company-popup',
    template: ''
})
export class CompanyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyPopupService: CompanyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.companyPopupService
                    .open(CompanyDialogComponent, params['id']);
            } else {
                this.modalRef = this.companyPopupService
                    .open(CompanyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
