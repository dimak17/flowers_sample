import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { CountryPopupService } from './country-popup.service';
import { CountryService } from './country.service';
import {Country} from '../../entities/country/country.model';

@Component({
    selector: 'jhi-country-delete-dialog',
    templateUrl: './country-delete-dialog.component.html'
})
export class CountryDeleteDialogComponent {

    country: Country;

    constructor(
        private countryService: CountryService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.countryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'countryListModification',
                content: 'Deleted an country'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.country.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-country-delete-popup',
    template: ''
})
export class CountryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countryPopupService: CountryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.countryPopupService
                .open(CountryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
