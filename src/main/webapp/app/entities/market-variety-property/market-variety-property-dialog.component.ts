
import {Response} from '@angular/http';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {MarketVarietyProperty} from './market-variety-property.model';
import {MarketVarietyPropertyPopupService} from './market-variety-property-popup.service';
import {MarketVarietyPropertyService} from './market-variety-property.service';
import {PriceList, PriceListService} from '../price-list';
import {Market, MarketService} from '../market';
import {ResponseWrapper} from '../../shared';
import {Variety} from '../variety/variety.model';
import {VarietyService} from '../../admin/variety/variety.service';
import {ShippingPolicy} from '../../admin/shipping-policy/shipping-policy.model';
import {ShippingPolicyService} from '../../admin/shipping-policy/shipping-policy.service';
import {Observable} from 'rxjs/Observable';
import {ActivatedRoute} from '@angular/router';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {AlertService, EventManager} from 'ng-jhipster';

@Component({
    selector: 'jhi-market-variety-property-dialog',
    templateUrl: './market-variety-property-dialog.component.html'
})
export class MarketVarietyPropertyDialogComponent implements OnInit {

    marketVarietyProperty: MarketVarietyProperty;
    isSaving: boolean;

    pricelists: PriceList[];

    markets: Market[];

    varieties: Variety[];

    shippingpolicies: ShippingPolicy[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketVarietyPropertyService: MarketVarietyPropertyService,
        private priceListService: PriceListService,
        private marketService: MarketService,
        private varietyService: VarietyService,
        private shippingPolicyService: ShippingPolicyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.priceListService.query()
            .subscribe((res: ResponseWrapper) => { this.pricelists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.marketService.query()
            .subscribe((res: ResponseWrapper) => { this.markets = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.varietyService.query()
            .subscribe((res: ResponseWrapper) => { this.varieties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.shippingPolicyService.query()
            .subscribe((res: ResponseWrapper) => { this.shippingpolicies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketVarietyProperty.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketVarietyPropertyService.update(this.marketVarietyProperty));
        } else {
            this.subscribeToSaveResponse(
                this.marketVarietyPropertyService.create(this.marketVarietyProperty));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketVarietyProperty>) {
        result.subscribe((res: MarketVarietyProperty) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketVarietyProperty) {
        this.eventManager.broadcast({ name: 'marketVarietyPropertyListModification', content: 'OK'});
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

    trackPriceListById(index: number, item: PriceList) {
        return item.id;
    }

    trackMarketById(index: number, item: Market) {
        return item.id;
    }

    trackVarietyById(index: number, item: Variety) {
        return item.id;
    }

    trackShippingPolicyById(index: number, item: ShippingPolicy) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-variety-property-popup',
    template: ''
})
export class MarketVarietyPropertyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketVarietyPropertyPopupService: MarketVarietyPropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketVarietyPropertyPopupService
                    .open(MarketVarietyPropertyDialogComponent as Component, params['id']);
            } else {
                this.marketVarietyPropertyPopupService
                    .open(MarketVarietyPropertyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
