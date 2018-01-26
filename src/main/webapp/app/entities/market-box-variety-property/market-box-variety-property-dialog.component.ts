import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MarketBoxVarietyProperty } from './market-box-variety-property.model';
import { MarketBoxVarietyPropertyPopupService } from './market-box-variety-property-popup.service';
import { MarketBoxVarietyPropertyService } from './market-box-variety-property.service';
import { MarketBox, MarketBoxService } from '../market-box';
import { ResponseWrapper } from '../../shared';
import {VarietyService} from '../../admin/variety/variety.service';
import {Variety} from '../variety/variety.model';

@Component({
    selector: 'jhi-market-box-variety-property-dialog',
    templateUrl: './market-box-variety-property-dialog.component.html'
})
export class MarketBoxVarietyPropertyDialogComponent implements OnInit {

    marketBoxVarietyProperty: MarketBoxVarietyProperty;
    authorities: any[];
    isSaving: boolean;

    marketboxes: MarketBox[];

    varieties: Variety[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketBoxVarietyPropertyService: MarketBoxVarietyPropertyService,
        private marketBoxService: MarketBoxService,
        private varietyService: VarietyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.marketBoxService.query()
            .subscribe((res: ResponseWrapper) => { this.marketboxes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.varietyService.query()
            .subscribe((res: ResponseWrapper) => { this.varieties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketBoxVarietyProperty.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketBoxVarietyPropertyService.update(this.marketBoxVarietyProperty), false);
        } else {
            this.subscribeToSaveResponse(
                this.marketBoxVarietyPropertyService.create(this.marketBoxVarietyProperty), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketBoxVarietyProperty>, isCreated: boolean) {
        result.subscribe((res: MarketBoxVarietyProperty) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketBoxVarietyProperty, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.marketBoxVarietyProperty.created'
            : 'flowersApp.marketBoxVarietyProperty.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'marketBoxVarietyPropertyListModification', content: 'OK'});
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

    trackMarketBoxById(index: number, item: MarketBox) {
        return item.id;
    }

    trackVarietyById(index: number, item: Variety) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-box-variety-property-popup',
    template: ''
})
export class MarketBoxVarietyPropertyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketBoxVarietyPropertyPopupService: MarketBoxVarietyPropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.marketBoxVarietyPropertyPopupService
                    .open(MarketBoxVarietyPropertyDialogComponent, params['id']);
            } else {
                this.modalRef = this.marketBoxVarietyPropertyPopupService
                    .open(MarketBoxVarietyPropertyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
