import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { CompanyUser } from './company-user.model';
import { CompanyUserPopupService } from './company-user-popup.service';
import { CompanyUserService } from './company-user.service';
import { User, UserService } from '../../shared';
import { Company, CompanyService } from '../company';
import { Position, PositionService } from '../position';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-user-dialog',
    templateUrl: './company-user-dialog.component.html'
})
export class CompanyUserDialogComponent implements OnInit {

    companyUser: CompanyUser;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    companies: Company[];

    positions: Position[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private companyUserService: CompanyUserService,
        private userService: UserService,
        private companyService: CompanyService,
        private positionService: PositionService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.positionService.query()
            .subscribe((res: ResponseWrapper) => { this.positions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.companyUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyUserService.update(this.companyUser), false);
        } else {
            this.subscribeToSaveResponse(
                this.companyUserService.create(this.companyUser), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyUser>, isCreated: boolean) {
        result.subscribe((res: CompanyUser) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CompanyUser, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.companyUser.created'
            : 'flowersApp.companyUser.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'companyUserListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackPositionById(index: number, item: Position) {
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
    selector: 'jhi-company-user-popup',
    template: ''
})
export class CompanyUserPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyUserPopupService: CompanyUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.companyUserPopupService
                    .open(CompanyUserDialogComponent, params['id']);
            } else {
                this.modalRef = this.companyUserPopupService
                    .open(CompanyUserDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
