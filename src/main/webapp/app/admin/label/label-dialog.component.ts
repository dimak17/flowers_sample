import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';
import { LabelPopupService } from './label-popup.service';
import { LabelService } from './label.service';
import { ResponseWrapper } from '../../shared';
import {ClientService} from '../client/client.service';
import {Label} from '../../entities/label/label.model';
import {Company} from '../../entities/company/company.model';
import {Client} from '../../entities/client/client.model';
import {CompanyService} from '../../entities/company/company.service';

@Component({
    selector: 'jhi-label-dialog',
    templateUrl: './label-dialog.component.html'
})
export class LabelDialogComponent implements OnInit {

    label: Label;
    authorities: any[];
    isSaving: boolean;

    companies: Company[];

    clients: Client[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private labelService: LabelService,
        private companyService: CompanyService,
        private clientService: ClientService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.clientService.query()
            .subscribe((res: ResponseWrapper) => { this.clients = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.label.id !== undefined) {
            this.subscribeToSaveResponse(
                this.labelService.update(this.label), false);
        } else {
            this.subscribeToSaveResponse(
                this.labelService.create(this.label), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Label>, isCreated: boolean) {
        result.subscribe((res: Label) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Label, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.label.created'
            : 'flowersApp.label.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'labelListModification', content: 'OK'});
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

    trackClientById(index: number, item: Client) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-label-popup',
    template: ''
})
export class LabelPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private labelPopupService: LabelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.labelPopupService
                    .open(LabelDialogComponent, params['id']);
            } else {
                this.modalRef = this.labelPopupService
                    .open(LabelDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
