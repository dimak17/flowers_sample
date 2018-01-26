import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';
import { CargoAgency } from './cargo-agency.model';
import { CargoAgencyPopupService } from './cargo-agency-popup.service';
import { CargoAgencyService } from './cargo-agency.service';

@Component({
    selector: 'jhi-cargo-agency-delete-dialog',
    templateUrl: './cargo-agency-delete-dialog.component.html',
    styleUrls: ['/cargo-agency-delete-dialog.component.scss']
})
export class CargoAgencyDeleteDialogComponent {

    cargoAgency: CargoAgency;

    constructor(
        private cargoAgencyService: CargoAgencyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cargoAgencyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cargoAgencyListModification',
                content: 'Deleted an cargoAgency'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.cargoAgency.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-cargo-agency-delete-popup',
    template: ''
})
export class CargoAgencyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cargoAgencyPopupService: CargoAgencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.cargoAgencyPopupService
                .open(CargoAgencyDeleteDialogComponent, 'cargo-agency-delete-modal-window', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
