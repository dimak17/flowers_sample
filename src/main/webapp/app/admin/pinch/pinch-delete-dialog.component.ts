import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { PinchPopupService } from './pinch-popup.service';
import { PinchService } from './pinch.service';
import {Pinch} from '../../entities/pinch/pinch.model';

@Component({
    selector: 'jhi-pinch-delete-dialog',
    templateUrl: './pinch-delete-dialog.component.html'
})
export class PinchDeleteDialogComponent {

    pinch: Pinch;

    constructor(
        private pinchService: PinchService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pinchService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pinchListModification',
                content: 'Deleted an pinch'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.pinch.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-pinch-delete-popup',
    template: ''
})
export class PinchDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pinchPopupService: PinchPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.pinchPopupService
                .open(PinchDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
