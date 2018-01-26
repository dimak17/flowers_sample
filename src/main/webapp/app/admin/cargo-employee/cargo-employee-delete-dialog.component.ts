import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';
import { CargoEmployee } from './cargo-employee.model';
import { CargoEmployeePopupService } from './cargo-employee-popup.service';
import { CargoEmployeeService } from './cargo-employee.service';

@Component({
    selector: 'jhi-cargo-employee-delete-dialog',
    templateUrl: './cargo-employee-delete-dialog.component.html',
    styleUrls: ['/cargo-employee-delete-dialog.component.scss']
})
export class CargoEmployeeDeleteDialogComponent {

    cargoEmployee: CargoEmployee;

    constructor(
        private cargoEmployeeService: CargoEmployeeService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(cargoEmployeeId: number, cargoAgencyId: number) {
        this.cargoEmployeeService.delete(cargoEmployeeId, cargoAgencyId).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cargoEmployeeListModification',
                content: 'Deleted an cargoEmployee'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.cargoEmployee.deleted', { param : cargoEmployeeId, cargoAgencyId }, null);
    }
}

@Component({
    selector: 'jhi-cargo-employee-delete-popup',
    template: ''
})
export class CargoEmployeeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cargoEmployeePopupService: CargoEmployeePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.cargoEmployeePopupService
                .open(CargoEmployeeDeleteDialogComponent, 'cargo-employee-delete-modal-window', params['cargoEmployeeId'], params['cargoAgencyId']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
