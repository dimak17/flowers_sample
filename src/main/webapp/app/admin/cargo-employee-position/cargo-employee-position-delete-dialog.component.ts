import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';
import { CargoEmployeePosition } from './cargo-employee-position.model';
import { CargoEmployeePositionPopupService } from './cargo-employee-position-popup.service';
import { CargoEmployeePositionService } from './cargo-employee-position.service';

@Component({
    selector: 'jhi-cargo-employee-position-delete-dialog',
    templateUrl: './cargo-employee-position-delete-dialog.component.html',
    styleUrls: ['./cargo-employee-position-delete-dialog.component.scss']
})
export class CargoEmployeePositionDeleteDialogComponent {

    cargoEmployeePosition: CargoEmployeePosition;

    constructor(
        private cargoEmployeePositionService: CargoEmployeePositionService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cargoEmployeePositionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cargoEmployeePositionListModification',
                content: 'Deleted an cargoEmployeePosition'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.cargoEmployeePosition.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-cargo-employee-position-delete-popup',
    template: ''
})
export class CargoEmployeePositionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cargoEmployeePositionPopupService: CargoEmployeePositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.cargoEmployeePositionPopupService
                .open(CargoEmployeePositionDeleteDialogComponent, 'cargo-employee-position-delete-modal-window', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
