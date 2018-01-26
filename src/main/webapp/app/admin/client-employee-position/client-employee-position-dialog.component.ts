import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClientEmployeePositionPopupService } from './client-employee-position-popup.service';
import { ClientEmployeePositionService } from './client-employee-position.service';
import { ResponseWrapper } from '../../shared';
import {ClientEmployeeService} from '../client-employee/client-employee.service';
import {ClientEmployeePosition} from '../../entities/client-employee-position/client-employee-position.model';
import {Company} from '../../entities/company/company.model';
import {ClientEmployee} from '../../entities/client-employee/client-employee.model';
import {CompanyService} from '../../entities/company/company.service';

@Component({
    selector: 'jhi-client-employee-position-dialog',
    templateUrl: './client-employee-position-dialog.component.html'
})
export class ClientEmployeePositionDialogComponent implements OnInit {

    clientEmployeePosition: ClientEmployeePosition;
    authorities: any[];
    isSaving: boolean;

    companies: Company[];

    clientemployees: ClientEmployee[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clientEmployeePositionService: ClientEmployeePositionService,
        private companyService: CompanyService,
        private clientEmployeeService: ClientEmployeeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.clientEmployeeService.query()
            .subscribe((res: ResponseWrapper) => { this.clientemployees = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clientEmployeePosition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientEmployeePositionService.update(this.clientEmployeePosition), false);
        } else {
            this.subscribeToSaveResponse(
                this.clientEmployeePositionService.create(this.clientEmployeePosition), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<ClientEmployeePosition>, isCreated: boolean) {
        result.subscribe((res: ClientEmployeePosition) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClientEmployeePosition, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.clientEmployeePosition.created'
            : 'flowersApp.clientEmployeePosition.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'clientEmployeePositionListModification', content: 'OK'});
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

    trackClientEmployeeById(index: number, item: ClientEmployee) {
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
    selector: 'jhi-client-employee-position-popup',
    template: ''
})
export class ClientEmployeePositionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientEmployeePositionPopupService: ClientEmployeePositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clientEmployeePositionPopupService
                    .open(ClientEmployeePositionDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientEmployeePositionPopupService
                    .open(ClientEmployeePositionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
