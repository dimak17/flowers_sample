import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { TypeOfFlower } from './type-of-flower.model';
import { TypeOfFlowerPopupService } from './type-of-flower-popup.service';
import { TypeOfFlowerService } from './type-of-flower.service';
import { Company, CompanyService } from '../company';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-type-of-flower-dialog',
    templateUrl: './type-of-flower-dialog.component.html'
})
export class TypeOfFlowerDialogComponent implements OnInit {

    typeOfFlower: TypeOfFlower;
    authorities: any[];
    isSaving: boolean;

    companies: Company[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private typeOfFlowerService: TypeOfFlowerService,
        private companyService: CompanyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.typeOfFlower.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeOfFlowerService.update(this.typeOfFlower), false);
        } else {
            this.subscribeToSaveResponse(
                this.typeOfFlowerService.create(this.typeOfFlower), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<TypeOfFlower>, isCreated: boolean) {
        result.subscribe((res: TypeOfFlower) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TypeOfFlower, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.typeOfFlower.created'
            : 'flowersApp.typeOfFlower.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'typeOfFlowerListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-type-of-flower-popup',
    template: ''
})
export class TypeOfFlowerPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeOfFlowerPopupService: TypeOfFlowerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.typeOfFlowerPopupService
                    .open(TypeOfFlowerDialogComponent, params['id']);
            } else {
                this.modalRef = this.typeOfFlowerPopupService
                    .open(TypeOfFlowerDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
