import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PinchPopupService } from './pinch-popup.service';
import { PinchService } from './pinch.service';
import {Pinch} from '../../entities/pinch/pinch.model';

@Component({
    selector: 'jhi-pinch-dialog',
    templateUrl: './pinch-dialog.component.html'
})
export class PinchDialogComponent implements OnInit {

    pinch: Pinch;
    authorities: any[];
    isSaving: boolean;
    startDateDp: any;
    endDateDp: any;
    notifyStartDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private pinchService: PinchService,
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
        if (this.pinch.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pinchService.update(this.pinch), false);
        } else {
            this.subscribeToSaveResponse(
                this.pinchService.create(this.pinch), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Pinch>, isCreated: boolean) {
        result.subscribe((res: Pinch) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Pinch, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.pinch.created'
            : 'flowersApp.pinch.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'pinchListModification', content: 'OK'});
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
    selector: 'jhi-pinch-popup',
    template: ''
})
export class PinchPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pinchPopupService: PinchPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.pinchPopupService
                    .open(PinchDialogComponent, params['id']);
            } else {
                this.modalRef = this.pinchPopupService
                    .open(PinchDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
