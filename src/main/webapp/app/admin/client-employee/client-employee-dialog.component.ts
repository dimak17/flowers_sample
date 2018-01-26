import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClientEmployeePopupService } from './client-employee-popup.service';
import { ClientEmployeeService } from './client-employee.service';
import { ResponseWrapper } from '../../shared';
import {ClientService} from '../client/client.service';
import {ClientEmployee} from '../../entities/client-employee/client-employee.model';
import {Client} from '../../entities/client/client.model';
import {ClientEmployeePosition} from '../../entities/client-employee-position/client-employee-position.model';
import {Company} from '../../entities/company/company.model';
import {CompanyService} from '../../entities/company/company.service';
import {ClientEmployeePositionService} from '../client-employee-position/client-employee-position.service';

@Component({
    selector: 'jhi-client-employee-dialog',
    templateUrl: './client-employee-dialog.component.html'
})
export class ClientEmployeeDialogComponent implements OnInit {

    clientEmployee: ClientEmployee;
    authorities: any[];
    isSaving: boolean;

    clients: Client[];

    companies: Company[];

    clientemployeepositions: ClientEmployeePosition[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clientEmployeeService: ClientEmployeeService,
        private clientService: ClientService,
        private companyService: CompanyService,
        private clientEmployeePositionService: ClientEmployeePositionService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clientService.query()
            .subscribe((res: ResponseWrapper) => { this.clients = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.clientEmployeePositionService.query()
            .subscribe((res: ResponseWrapper) => { this.clientemployeepositions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clientEmployee.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientEmployeeService.update(this.clientEmployee), false);
        } else {
            this.subscribeToSaveResponse(
                this.clientEmployeeService.create(this.clientEmployee), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<ClientEmployee>, isCreated: boolean) {
        result.subscribe((res: ClientEmployee) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClientEmployee, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.clientEmployee.created'
            : 'flowersApp.clientEmployee.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'clientEmployeeListModification', content: 'OK'});
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

    trackClientById(index: number, item: Client) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackClientEmployeePositionById(index: number, item: ClientEmployeePosition) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-client-employee-popup',
    template: ''
})
export class ClientEmployeePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientEmployeePopupService: ClientEmployeePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clientEmployeePopupService
                    .open(ClientEmployeeDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientEmployeePopupService
                    .open(ClientEmployeeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
