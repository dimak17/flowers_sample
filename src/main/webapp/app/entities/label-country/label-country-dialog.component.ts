import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { LabelCountry } from './label-country.model';
import { LabelCountryPopupService } from './label-country-popup.service';
import { LabelCountryService } from './label-country.service';
import { Company, CompanyService } from '../company';
import { ResponseWrapper } from '../../shared';
import {Label} from '../label/label.model';
import {LabelService} from '../../admin/label/label.service';
import {Country} from '../country/country.model';
import {CountryService} from '../../admin/country/country.service';

@Component({
    selector: 'jhi-label-country-dialog',
    templateUrl: './label-country-dialog.component.html'
})
export class LabelCountryDialogComponent implements OnInit {

    labelCountry: LabelCountry;
    authorities: any[];
    isSaving: boolean;

    labels: Label[];

    countries: Country[];

    companies: Company[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private labelCountryService: LabelCountryService,
        private labelService: LabelService,
        private countryService: CountryService,
        private companyService: CompanyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.labelService.query()
            .subscribe((res: ResponseWrapper) => { this.labels = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.countryService.query()
            .subscribe((res: ResponseWrapper) => { this.countries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.labelCountry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.labelCountryService.update(this.labelCountry), false);
        } else {
            this.subscribeToSaveResponse(
                this.labelCountryService.create(this.labelCountry), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<LabelCountry>, isCreated: boolean) {
        result.subscribe((res: LabelCountry) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: LabelCountry, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.labelCountry.created'
            : 'flowersApp.labelCountry.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'labelCountryListModification', content: 'OK'});
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
        this.alertService.error(error.message, null, null);
    }

    trackLabelById(index: number, item: Label) {
        return item.id;
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-label-country-popup',
    template: ''
})
export class LabelCountryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private labelCountryPopupService: LabelCountryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.labelCountryPopupService
                    .open(LabelCountryDialogComponent, params['id']);
            } else {
                this.modalRef = this.labelCountryPopupService
                    .open(LabelCountryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
