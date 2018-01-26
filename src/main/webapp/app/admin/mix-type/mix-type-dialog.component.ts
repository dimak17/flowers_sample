import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MixType } from './mix-type.model';
import { MixTypePopupService } from './mix-type-popup.service';
import { MixTypeService } from './mix-type.service';
import {LATIN_VALIDATION} from '../../shared/constants/validation.constants';

@Component({
    selector: 'jhi-flowers-mix-type-dialog',
    templateUrl: './mix-type-dialog.component.html'
})
export class MixTypeDialogComponent implements OnInit {

    mixType: MixType;
    dublicateMixTypeName: String;
    authorities: any[];
    isSaving: boolean;
    errorAlert = false;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private mixTypeService: MixTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        if (this.mixType && this.mixType.id) {
            this.mixType.colorClass = localStorage.getItem(this.mixType.id.toString());
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mixType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mixTypeService.update(this.mixType), false);
        } else {
            this.subscribeToSaveResponse(
                this.mixTypeService.create(this.mixType), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<MixType>, isCreated: boolean) {
        result.subscribe((res: MixType) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MixType, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.mixType.created'
            : 'flowersApp.mixType.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'mixTypeListModification', content: 'OK'});
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
        if (error.headers.get('x-flowersapp-error') === 'error.DuplicateName') {
            this.errorAlert = true;
            this.dublicateMixTypeName = this.mixType.name.toLowerCase().trim();
        } else {
            this.alertService.error(error.message, null, null);
        }
    }
    latinValidation(mixTypeName: string): boolean {
        if (mixTypeName) {
            if (!mixTypeName.match(LATIN_VALIDATION)) {
                return true;
            }
        }
    }

    fieldLengthValidation(fieldData: string): boolean {
        if (fieldData && fieldData.length) {
            if (fieldData.length > 24) {
                return true;
            }
        }
    }

    requiredValidation(fieldData: string): boolean {
        if (fieldData && fieldData.trim().length === 0) {
            return true;
        }
    }

    saveButtonDeactivation( mixType: MixType): boolean {
        return (this.latinValidation(mixType.name) || this.fieldLengthValidation(mixType.name)
            || this.requiredValidation(mixType.name));
    }

}

@Component({
    selector: 'jhi-flowers-mix-type-popup',
    template: ''
})
export class MixTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mixTypePopupService: MixTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.mixTypePopupService
                    .open(MixTypeDialogComponent, 'modal_small', params['id']);
            } else {
                this.modalRef = this.mixTypePopupService
                    .open(MixTypeDialogComponent, 'modal_small');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
