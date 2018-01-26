import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { DestinationPopupService } from './destination-popup.service';
import { DestinationService } from './destination.service';
import {Destination} from '../../entities/destination/destination.model';

@Component({
    selector: 'jhi-destination-delete-dialog',
    templateUrl: './destination-delete-dialog.component.html'
})
export class DestinationDeleteDialogComponent {

    destination: Destination;

    constructor(
        private destinationService: DestinationService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.destinationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'destinationListModification',
                content: 'Deleted an destination'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.destination.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-destination-delete-popup',
    template: ''
})
export class DestinationDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private destinationPopupService: DestinationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.destinationPopupService
                .open(DestinationDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
