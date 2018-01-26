import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Disease } from './disease.model';
import { DiseasePopupService } from './disease-popup.service';
import { DiseaseService } from './disease.service';

@Component({
    selector: 'jhi-disease-dialog',
    templateUrl: './disease-dialog.component.html'
})
export class DiseaseDialogComponent implements OnInit {

    disease: Disease;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private diseaseService: DiseaseService,
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
        if (this.disease.id !== undefined) {
            this.subscribeToSaveResponse(
                this.diseaseService.update(this.disease), false);
        } else {
            this.subscribeToSaveResponse(
                this.diseaseService.create(this.disease), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Disease>, isCreated: boolean) {
        result.subscribe((res: Disease) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Disease, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.disease.created'
            : 'flowersApp.disease.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'diseaseListModification', content: 'OK'});
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
    selector: 'jhi-disease-popup',
    template: ''
})
export class DiseasePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private diseasePopupService: DiseasePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.diseasePopupService
                    .open(DiseaseDialogComponent, params['id']);
            } else {
                this.modalRef = this.diseasePopupService
                    .open(DiseaseDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
