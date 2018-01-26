import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import {Client} from '../../entities/client/client.model';
import { ClientPopupService } from './client-popup.service';
import { ClientService } from './client.service';
import { ResponseWrapper } from '../../shared';
import {Company} from '../../entities/company/company.model';
import {ClaimsPolicy} from '../../entities/claims-policy/claims-policy.model';
import {CompanyService} from '../../entities/company/company.service';
import {ClaimsPolicyService} from '../claims-policy/claims-policy.service';

@Component({
    selector: 'jhi-client-dialog',
    templateUrl: './client-dialog.component.html'
})
export class ClientDialogComponent implements OnInit {

    client: Client;
    authorities: any[];
    isSaving: boolean;

    companies: Company[];

    claimspolicies: ClaimsPolicy[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clientService: ClientService,
        private companyService: CompanyService,
        private claimsPolicyService: ClaimsPolicyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.claimsPolicyService.query()
            .subscribe((res: ResponseWrapper) => { this.claimspolicies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.client.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientService.update(this.client), false);
        } else {
            this.subscribeToSaveResponse(
                this.clientService.create(this.client), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Client>, isCreated: boolean) {
        result.subscribe((res: Client) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Client, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.client.created'
            : 'flowersApp.client.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'clientListModification', content: 'OK'});
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

    trackClaimsPolicyById(index: number, item: ClaimsPolicy) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-client-popup',
    template: ''
})
export class ClientPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientPopupService: ClientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clientPopupService
                    .open(ClientDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientPopupService
                    .open(ClientDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
