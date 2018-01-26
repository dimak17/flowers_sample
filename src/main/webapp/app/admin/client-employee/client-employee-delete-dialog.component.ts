import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { ClientEmployeePopupService } from './client-employee-popup.service';
import { ClientEmployeeService } from './client-employee.service';
import {ClientEmployee} from '../../entities/client-employee/client-employee.model';

@Component({
    selector: 'jhi-client-employee-delete-dialog',
    templateUrl: './client-employee-delete-dialog.component.html'
})
export class ClientEmployeeDeleteDialogComponent {

    clientEmployee: ClientEmployee;

    constructor(
        private clientEmployeeService: ClientEmployeeService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientEmployeeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clientEmployeeListModification',
                content: 'Deleted an clientEmployee'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.clientEmployee.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-client-employee-delete-popup',
    template: ''
})
export class ClientEmployeeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientEmployeePopupService: ClientEmployeePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clientEmployeePopupService
                .open(ClientEmployeeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
