import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PinchVarietyProperty } from './pinch-variety-property.model';
import { PinchVarietyPropertyPopupService } from './pinch-variety-property-popup.service';
import { PinchVarietyPropertyService } from './pinch-variety-property.service';
import { PinchVariety, PinchVarietyService } from '../pinch-variety';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pinch-variety-property-dialog',
    templateUrl: './pinch-variety-property-dialog.component.html'
})
export class PinchVarietyPropertyDialogComponent implements OnInit {

    pinchVarietyProperty: PinchVarietyProperty;
    authorities: any[];
    isSaving: boolean;

    pinchvarieties: PinchVariety[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private pinchVarietyPropertyService: PinchVarietyPropertyService,
        private pinchVarietyService: PinchVarietyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.pinchVarietyService.query()
            .subscribe((res: ResponseWrapper) => { this.pinchvarieties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pinchVarietyProperty.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pinchVarietyPropertyService.update(this.pinchVarietyProperty), false);
        } else {
            this.subscribeToSaveResponse(
                this.pinchVarietyPropertyService.create(this.pinchVarietyProperty), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PinchVarietyProperty>, isCreated: boolean) {
        result.subscribe((res: PinchVarietyProperty) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PinchVarietyProperty, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.pinchVarietyProperty.created'
            : 'flowersApp.pinchVarietyProperty.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'pinchVarietyPropertyListModification', content: 'OK'});
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

    trackPinchVarietyById(index: number, item: PinchVariety) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pinch-variety-property-popup',
    template: ''
})
export class PinchVarietyPropertyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pinchVarietyPropertyPopupService: PinchVarietyPropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.pinchVarietyPropertyPopupService
                    .open(PinchVarietyPropertyDialogComponent, params['id']);
            } else {
                this.modalRef = this.pinchVarietyPropertyPopupService
                    .open(PinchVarietyPropertyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
