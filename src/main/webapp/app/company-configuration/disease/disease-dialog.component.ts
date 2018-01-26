import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

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
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private diseaseService: DiseaseService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['disease']);
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
            this.diseaseService.update(this.disease)
                .subscribe((res: Disease) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.diseaseService.create(this.disease)
                .subscribe((res: Disease) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Disease) {
        this.eventManager.broadcast({ name: 'diseaseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
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
