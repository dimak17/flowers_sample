import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';
import { CargoEmployeePosition } from './cargo-employee-position.model';
import { CargoEmployeePositionPopupService } from './cargo-employee-position-popup.service';
import { CargoEmployeePositionService } from './cargo-employee-position.service';
import { LATIN_VALIDATION } from '../../shared/constants/validation.constants';

@Component({
    selector: 'jhi-cargo-employee-position-dialog',
    templateUrl: './cargo-employee-position-dialog.component.html',
    styleUrls: ['./cargo-employee-position-dialog.component.scss']
})
export class CargoEmployeePositionDialogComponent implements OnInit {

    cargoEmployeePosition: CargoEmployeePosition;
    authorities: any[];
    duplicateCargoEmployeePositionName: string;
    isSaving: boolean;
    errorAlert = false;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private cargoEmployeePositionService: CargoEmployeePositionService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        if (this.cargoEmployeePosition && this.cargoEmployeePosition.id) {
            this.cargoEmployeePosition.colorClass = localStorage.getItem(this.cargoEmployeePosition.id.toString());
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cargoEmployeePosition.id) {
            this.subscribeToSaveResponse(
                this.cargoEmployeePositionService.update(this.cargoEmployeePosition), false);
        } else {
            this.subscribeToSaveResponse(
                this.cargoEmployeePositionService.create(this.cargoEmployeePosition), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<CargoEmployeePosition>, isCreated: boolean) {
        result.subscribe((res: CargoEmployeePosition) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CargoEmployeePosition, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.cargoEmployeePosition.created'
            : 'flowersApp.cargoEmployeePosition.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'cargoEmployeePositionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        if (error.headers.get('x-flowersapp-error') === 'error.DuplicateName') {
            this.errorAlert = true;
            this.duplicateCargoEmployeePositionName = this.cargoEmployeePosition.name.toLowerCase().trim();
        } else {
            this.alertService.error(error.message, null, null);
        }
    }

    latinValidation(cargoEmployeePositionName: string): boolean {
        if (cargoEmployeePositionName) {
            if (!cargoEmployeePositionName.match(LATIN_VALIDATION)) {
                return true;
            }
        }
    }

    fieldLengthValidation(fieldData: string): boolean {
        if (fieldData && fieldData.length) {
            if (fieldData.length > 25) {
                return true;
            }
        }
    }

    requiredValidation(fieldData: string): boolean {
        if (fieldData && fieldData.trim().length === 0) {
            return true;
        }
    }

    saveButtonDeactivation(cargoEmployeePosition: CargoEmployeePosition): boolean {
        return (this.latinValidation(cargoEmployeePosition.name) || this.fieldLengthValidation(cargoEmployeePosition.name) || this.requiredValidation(cargoEmployeePosition.name));
        }
}

@Component({
    selector: 'jhi-cargo-employee-position-popup',
    template: ''
})
export class CargoEmployeePositionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cargoEmployeePositionPopupService: CargoEmployeePositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.cargoEmployeePositionPopupService
                    .open(CargoEmployeePositionDialogComponent, 'cargo-employee-position-modal-window', params['id']);
            } else {
                this.modalRef = this.cargoEmployeePositionPopupService
                    .open(CargoEmployeePositionDialogComponent, 'cargo-employee-position-modal-window');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
