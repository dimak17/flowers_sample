import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { PinchVarietyProperty } from './pinch-variety-property.model';
import { PinchVarietyPropertyPopupService } from './pinch-variety-property-popup.service';
import { PinchVarietyPropertyService } from './pinch-variety-property.service';

@Component({
    selector: 'jhi-pinch-variety-property-delete-dialog',
    templateUrl: './pinch-variety-property-delete-dialog.component.html'
})
export class PinchVarietyPropertyDeleteDialogComponent {

    pinchVarietyProperty: PinchVarietyProperty;

    constructor(
        private pinchVarietyPropertyService: PinchVarietyPropertyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pinchVarietyPropertyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pinchVarietyPropertyListModification',
                content: 'Deleted an pinchVarietyProperty'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.pinchVarietyProperty.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-pinch-variety-property-delete-popup',
    template: ''
})
export class PinchVarietyPropertyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pinchVarietyPropertyPopupService: PinchVarietyPropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.pinchVarietyPropertyPopupService
                .open(PinchVarietyPropertyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
