import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { PinchVariety } from './pinch-variety.model';
import { PinchVarietyPopupService } from './pinch-variety-popup.service';
import { PinchVarietyService } from './pinch-variety.service';

@Component({
    selector: 'jhi-pinch-variety-delete-dialog',
    templateUrl: './pinch-variety-delete-dialog.component.html'
})
export class PinchVarietyDeleteDialogComponent {

    pinchVariety: PinchVariety;

    constructor(
        private pinchVarietyService: PinchVarietyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pinchVarietyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pinchVarietyListModification',
                content: 'Deleted an pinchVariety'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.pinchVariety.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-pinch-variety-delete-popup',
    template: ''
})
export class PinchVarietyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pinchVarietyPopupService: PinchVarietyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.pinchVarietyPopupService
                .open(PinchVarietyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
