import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager} from 'ng-jhipster';

import {MarketSeasonVarietyProperty} from './market-season-variety-property.model';
import {MarketSeasonVarietyPropertyPopupService} from './market-season-variety-property-popup.service';
import {MarketSeasonVarietyPropertyService} from './market-season-variety-property.service';
import {PriceList, PriceListService} from '../price-list';
import {MarketSeason, MarketSeasonService} from '../market-season';
import {ResponseWrapper} from '../../shared';
import {Variety} from '../variety/variety.model';
import {VarietyService} from '../../admin/variety/variety.service';

@Component({
    selector: 'jhi-market-season-variety-property-dialog',
    templateUrl: './market-season-variety-property-dialog.component.html'
})
export class MarketSeasonVarietyPropertyDialogComponent implements OnInit {

    marketSeasonVarietyProperty: MarketSeasonVarietyProperty;
    isSaving: boolean;

    varieties: Variety[];

    pricelists: PriceList[];

    marketseasons: MarketSeason[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketSeasonVarietyPropertyService: MarketSeasonVarietyPropertyService,
        private varietyService: VarietyService,
        private priceListService: PriceListService,
        private marketSeasonService: MarketSeasonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.varietyService.query()
            .subscribe((res: ResponseWrapper) => { this.varieties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.priceListService.query()
            .subscribe((res: ResponseWrapper) => { this.pricelists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.marketSeasonService.query()
            .subscribe((res: ResponseWrapper) => { this.marketseasons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketSeasonVarietyProperty.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketSeasonVarietyPropertyService.update(this.marketSeasonVarietyProperty));
        } else {
            this.subscribeToSaveResponse(
                this.marketSeasonVarietyPropertyService.create(this.marketSeasonVarietyProperty));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketSeasonVarietyProperty>) {
        result.subscribe((res: MarketSeasonVarietyProperty) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketSeasonVarietyProperty) {
        this.eventManager.broadcast({ name: 'marketSeasonVarietyPropertyListModification', content: 'OK'});
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

    trackVarietyById(index: number, item: Variety) {
        return item.id;
    }

    trackPriceListById(index: number, item: PriceList) {
        return item.id;
    }

    trackMarketSeasonById(index: number, item: MarketSeason) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-season-variety-property-popup',
    template: ''
})
export class MarketSeasonVarietyPropertyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketSeasonVarietyPropertyPopupService: MarketSeasonVarietyPropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketSeasonVarietyPropertyPopupService
                    .open(MarketSeasonVarietyPropertyDialogComponent as Component, params['id']);
            } else {
                this.marketSeasonVarietyPropertyPopupService
                    .open(MarketSeasonVarietyPropertyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
