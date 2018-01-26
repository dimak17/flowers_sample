import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';
import { AirLines } from './air-lines.model';
import { AirLinesPopupService } from './air-lines-popup.service';
import { AirLinesService } from './air-lines.service';
import { Company, CompanyService } from '../../entities/company';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-air-lines-dialog',
    templateUrl: './air-lines-dialog.component.html',
    styleUrls: ['./air-lines-dialog.component.scss']
})
export class AirLinesDialogComponent implements OnInit {

    airLines: AirLines;
    authorities: any[];
    isSaving: boolean;
    errorAlert = false;

    companies: Company[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private airLinesService: AirLinesService,
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
        if (this.airLines.id !== undefined) {
            this.subscribeToSaveResponse(
                this.airLinesService.update(this.airLines), false);
        } else {
            this.subscribeToSaveResponse(
                this.airLinesService.create(this.airLines), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<AirLines>, isCreated: boolean) {
        result.subscribe((res: AirLines) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AirLines, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.airLines.created'
            : 'flowersApp.airLines.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'airLinesListModification', content: 'OK'});
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
        }else {
            this.alertService.error(error.message, null, null);
        }
    }

    latinLengthValidation(airLineName: string): boolean {
        const LATIN_LENGTH_25 = '^[a-zA-Z0-9 +-.,!@#$%^&*();\/|<>"\'_\\:áéñóúüÁÉÑÚÜ‌]{0,25}$';
        return airLineName && !airLineName.match(LATIN_LENGTH_25);
    }

    digitLengthValidation(fieldData: number): boolean {
        const DIGITS_LENGTH_5 = '^[0-9]{0,5}$';
        return fieldData && !fieldData.toString().match(DIGITS_LENGTH_5);
    }

    saveButtonDeactivation( airLines: AirLines): boolean {
        return (this.latinLengthValidation(airLines.name) || this.digitLengthValidation(airLines.number));
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-air-lines-popup',
    template: ''
})
export class AirLinesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private airLinesPopupService: AirLinesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.airLinesPopupService
                    .open(AirLinesDialogComponent, 'air-line-modal-window', params['id']);
            } else {
                this.modalRef = this.airLinesPopupService
                    .open(AirLinesDialogComponent, 'air-line-modal-window');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
