import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Position } from './position.model';
import { PositionPopupService } from './position-popup.service';
import { PositionService } from './position.service';
import { Company, CompanyService } from '../company';
import { CompanyUser, CompanyUserService } from '../company-user';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-position-dialog',
    templateUrl: './position-dialog.component.html'
})
export class PositionDialogComponent implements OnInit {

    position: Position;
    authorities: any[];
    isSaving: boolean;
    companies: Company[];
    companyusers: CompanyUser[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private positionService: PositionService,
        private companyService: CompanyService,
        private companyUserService: CompanyUserService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyUserService.query()
            .subscribe((res: ResponseWrapper) => { this.companyusers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.position.id !== undefined) {
            this.subscribeToSaveResponse(
                this.positionService.update(this.position), false);
        } else {
            this.subscribeToSaveResponse(
                this.positionService.create(this.position), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Position>, isCreated: boolean) {
        result.subscribe((res: Position) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Position, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.position.created'
            : 'flowersApp.position.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'positionListModification', content: 'OK'});
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

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackCompanyUserById(index: number, item: CompanyUser) {
        return item.id;
    }

    trackAuthorityById(index: number, item: any) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-position-popup',
    template: ''
})
export class PositionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private positionPopupService: PositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.positionPopupService
                    .open(PositionDialogComponent, params['id']);
            } else {
                this.modalRef = this.positionPopupService
                    .open(PositionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
