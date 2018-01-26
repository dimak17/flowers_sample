import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PinchVariety } from './pinch-variety.model';
import { PinchVarietyPopupService } from './pinch-variety-popup.service';
import { PinchVarietyService } from './pinch-variety.service';
import { ResponseWrapper } from '../../shared';
import {Variety} from '../variety/variety.model';
import {VarietyService} from '../../admin/variety/variety.service';
import {Pinch} from '../pinch/pinch.model';
import {PinchService} from '../../admin/pinch/pinch.service';

@Component({
    selector: 'jhi-pinch-variety-dialog',
    templateUrl: './pinch-variety-dialog.component.html'
})
export class PinchVarietyDialogComponent implements OnInit {

    pinchVariety: PinchVariety;
    authorities: any[];
    isSaving: boolean;

    pinches: Pinch[];

    varieties: Variety[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private pinchVarietyService: PinchVarietyService,
        private pinchService: PinchService,
        private varietyService: VarietyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.pinchService.query()
            .subscribe((res: ResponseWrapper) => { this.pinches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.varietyService.query()
            .subscribe((res: ResponseWrapper) => { this.varieties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pinchVariety.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pinchVarietyService.update(this.pinchVariety), false);
        } else {
            this.subscribeToSaveResponse(
                this.pinchVarietyService.create(this.pinchVariety), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PinchVariety>, isCreated: boolean) {
        result.subscribe((res: PinchVariety) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PinchVariety, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.pinchVariety.created'
            : 'flowersApp.pinchVariety.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'pinchVarietyListModification', content: 'OK'});
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

    trackPinchById(index: number, item: Pinch) {
        return item.id;
    }

    trackVarietyById(index: number, item: Variety) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pinch-variety-popup',
    template: ''
})
export class PinchVarietyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pinchVarietyPopupService: PinchVarietyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.pinchVarietyPopupService
                    .open(PinchVarietyDialogComponent, params['id']);
            } else {
                this.modalRef = this.pinchVarietyPopupService
                    .open(PinchVarietyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
