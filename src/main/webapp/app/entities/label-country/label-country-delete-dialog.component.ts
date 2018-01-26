import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { LabelCountry } from './label-country.model';
import { LabelCountryPopupService } from './label-country-popup.service';
import { LabelCountryService } from './label-country.service';

@Component({
    selector: 'jhi-label-country-delete-dialog',
    templateUrl: './label-country-delete-dialog.component.html'
})
export class LabelCountryDeleteDialogComponent {

    labelCountry: LabelCountry;

    constructor(
        private labelCountryService: LabelCountryService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.labelCountryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'labelCountryListModification',
                content: 'Deleted an labelCountry'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.labelCountry.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-label-country-delete-popup',
    template: ''
})
export class LabelCountryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private labelCountryPopupService: LabelCountryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.labelCountryPopupService
                .open(LabelCountryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
