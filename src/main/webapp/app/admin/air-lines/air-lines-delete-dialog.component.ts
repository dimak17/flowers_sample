import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';
import { AirLines } from './air-lines.model';
import { AirLinesPopupService } from './air-lines-popup.service';
import { AirLinesService } from './air-lines.service';

@Component({
    selector: 'jhi-air-lines-delete-dialog',
    templateUrl: './air-lines-delete-dialog.component.html',
    styleUrls: ['./air-lines-delete-dialog.component.scss']
})
export class AirLinesDeleteDialogComponent {

    airLines: AirLines;

    constructor(
        private airLinesService: AirLinesService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.airLinesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'airLinesListModification',
                content: 'Deleted an airLines'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.airLines.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-air-lines-delete-popup',
    template: ''
})
export class AirLinesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private airLinesPopupService: AirLinesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.airLinesPopupService
                .open(AirLinesDeleteDialogComponent, 'air-line-delete-modal-window', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
