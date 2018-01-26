import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { ClientEmployeePositionPopupService } from './client-employee-position-popup.service';
import { ClientEmployeePositionService } from './client-employee-position.service';
import {ClientEmployeePosition} from '../../entities/client-employee-position/client-employee-position.model';

@Component({
    selector: 'jhi-client-employee-position-delete-dialog',
    templateUrl: './client-employee-position-delete-dialog.component.html'
})
export class ClientEmployeePositionDeleteDialogComponent {

    clientEmployeePosition: ClientEmployeePosition;

    constructor(
        private clientEmployeePositionService: ClientEmployeePositionService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientEmployeePositionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clientEmployeePositionListModification',
                content: 'Deleted an clientEmployeePosition'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.clientEmployeePosition.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-client-employee-position-delete-popup',
    template: ''
})
export class ClientEmployeePositionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientEmployeePositionPopupService: ClientEmployeePositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clientEmployeePositionPopupService
                .open(ClientEmployeePositionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
