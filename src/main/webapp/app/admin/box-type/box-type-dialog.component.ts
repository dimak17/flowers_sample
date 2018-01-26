import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {EventManager, AlertService, JhiLanguageService} from 'ng-jhipster';

import { BoxTypeUi } from './box-type-ui.model';
import { BoxTypePopupService } from './box-type-popup.service';
import { BoxTypeService } from './box-type.service';
import {LATIN_VALIDATION} from '../../shared';

@Component({
    selector: 'jhi-box-type-dialog',
    templateUrl: './box-type-dialog.component.html'
})
export class BoxTypeDialogComponent implements OnInit {

    boxType: BoxTypeUi;
    authorities: any[];
    isSaving: boolean;
    errorAlert = false;
    currentAccount: any;
    tmpShortName: string;
    shortNameMax = 5;
    fullNameMax = 13;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private boxTypeService: BoxTypeService,
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
    ) {
        this.jhiLanguageService.setLocations(['farm-data']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        if (this.boxType && this.boxType.id) {
            this.boxType.colorClass = localStorage.getItem(this.boxType.id.toString());
        }
        this.tmpShortName = '';
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.boxType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.boxTypeService.update(this.boxType), false);
        } else {
            this.subscribeToSaveResponse(
                this.boxTypeService.create(this.boxType), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BoxTypeUi>, isCreated: boolean) {
        result.subscribe((res: BoxTypeUi) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BoxTypeUi, isCreated: boolean) {
        this.errorAlert = false;
        this.alertService.success(
            isCreated ? 'flowersApp.box-type.created'
            : 'flowersApp.box-type.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'boxTypeListModification', content: 'OK'});
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
            this.tmpShortName = this.boxType.shortName.toLowerCase().trim();
            this.errorAlert = true;
        }else {
            this.alertService.error(error.message, null, null);
        }
    }

    requiredValidation(boxTypeName: string) {
        return !boxTypeName || !this.isFillValidation(boxTypeName);
    }

    isFillValidation(fieldData: any): boolean {
       return fieldData && fieldData.toString().trim();
    }

    latinValidation(data: string, maxLength: number): boolean {
        if (data && !this.lengthValidation(data, maxLength)) {
                return !data.match(LATIN_VALIDATION);
        }
    }

    lengthValidation(fieldData: string, maxLength: number): boolean {
        if (fieldData && fieldData.length) {
                return fieldData.length > maxLength;
        }
    }

    duplicateValidation(fieldData: string): boolean {
        return this.boxType.shortName && this.tmpShortName && this.tmpShortName === this.boxType.shortName.toLowerCase().trim()
            && !this.lengthValidation(fieldData, this.shortNameMax);
    }

    saveButtonDeactivation(boxType: BoxTypeUi): boolean {
        return this.requiredValidation(boxType.shortName) || this.requiredValidation(boxType.fullName) ||
            this.latinValidation(boxType.shortName, this.shortNameMax) || this.latinValidation(boxType.fullName, this.fullNameMax)
            || this.lengthValidation(boxType.shortName, this.shortNameMax) || this.lengthValidation(boxType.fullName, this.fullNameMax);
    }
}

@Component({
    selector: 'jhi-box-type-popup',
    template: ''
})
export class BoxTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private boxTypePopupService: BoxTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.boxTypePopupService
                    .open(BoxTypeDialogComponent, 'modal_small', params['id']);
            } else {
                this.modalRef = this.boxTypePopupService
                    .open(BoxTypeDialogComponent, 'modal_small');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
