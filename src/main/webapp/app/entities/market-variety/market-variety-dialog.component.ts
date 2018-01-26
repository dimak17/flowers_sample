import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MarketVariety } from './market-variety.model';
import { MarketVarietyPopupService } from './market-variety-popup.service';
import { MarketVarietyService } from './market-variety.service';
import { Market, MarketService } from '../market';
import { ResponseWrapper } from '../../shared';
import {Variety} from '../variety/variety.model';
import {VarietyService} from '../../admin/variety/variety.service';

@Component({
    selector: 'jhi-market-variety-dialog',
    templateUrl: './market-variety-dialog.component.html'
})
export class MarketVarietyDialogComponent implements OnInit {

    marketVariety: MarketVariety;
    authorities: any[];
    isSaving: boolean;

    markets: Market[];

    varieties: Variety[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketVarietyService: MarketVarietyService,
        private marketService: MarketService,
        private varietyService: VarietyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.marketService.query()
            .subscribe((res: ResponseWrapper) => { this.markets = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.varietyService.query()
            .subscribe((res: ResponseWrapper) => { this.varieties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketVariety.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketVarietyService.update(this.marketVariety), false);
        } else {
            this.subscribeToSaveResponse(
                this.marketVarietyService.create(this.marketVariety), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketVariety>, isCreated: boolean) {
        result.subscribe((res: MarketVariety) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketVariety, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.marketVariety.created'
            : 'flowersApp.marketVariety.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'marketVarietyListModification', content: 'OK'});
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

    trackMarketById(index: number, item: Market) {
        return item.id;
    }

    trackVarietyById(index: number, item: Variety) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-variety-popup',
    template: ''
})
export class MarketVarietyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketVarietyPopupService: MarketVarietyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.marketVarietyPopupService
                    .open(MarketVarietyDialogComponent, params['id']);
            } else {
                this.modalRef = this.marketVarietyPopupService
                    .open(MarketVarietyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
