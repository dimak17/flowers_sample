import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { DestinationPopupService } from './destination-popup.service';
import { DestinationService } from './destination.service';
import { ResponseWrapper } from '../../shared';
import {Destination} from '../../entities/destination/destination.model';
import {LabelCountry} from '../../entities/label-country/label-country.model';
import {Company} from '../../entities/company/company.model';
import {LabelCountryService} from '../../entities/label-country/label-country.service';
import {CompanyService} from '../../entities/company/company.service';

@Component({
    selector: 'jhi-destination-dialog',
    templateUrl: './destination-dialog.component.html'
})
export class DestinationDialogComponent implements OnInit {

    destination: Destination;
    authorities: any[];
    isSaving: boolean;

    labelcountries: LabelCountry[];

    companies: Company[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private destinationService: DestinationService,
        private labelCountryService: LabelCountryService,
        private companyService: CompanyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.labelCountryService.query()
            .subscribe((res: ResponseWrapper) => { this.labelcountries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.destination.id !== undefined) {
            this.subscribeToSaveResponse(
                this.destinationService.update(this.destination), false);
        } else {
            this.subscribeToSaveResponse(
                this.destinationService.create(this.destination), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Destination>, isCreated: boolean) {
        result.subscribe((res: Destination) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Destination, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.destination.created'
            : 'flowersApp.destination.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'destinationListModification', content: 'OK'});
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

    trackLabelCountryById(index: number, item: LabelCountry) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-destination-popup',
    template: ''
})
export class DestinationPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private destinationPopupService: DestinationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.destinationPopupService
                    .open(DestinationDialogComponent, params['id']);
            } else {
                this.modalRef = this.destinationPopupService
                    .open(DestinationDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
