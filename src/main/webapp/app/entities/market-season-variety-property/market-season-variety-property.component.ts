import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {AlertService, EventManager} from 'ng-jhipster';

import {MarketSeasonVarietyProperty} from './market-season-variety-property.model';
import {MarketSeasonVarietyPropertyService} from './market-season-variety-property.service';
import {Principal, ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-market-season-variety-property',
    templateUrl: './market-season-variety-property.component.html'
})
export class MarketSeasonVarietyPropertyComponent implements OnInit, OnDestroy {
marketSeasonVarietyProperties: MarketSeasonVarietyProperty[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private marketSeasonVarietyPropertyService: MarketSeasonVarietyPropertyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.marketSeasonVarietyPropertyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.marketSeasonVarietyProperties = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMarketSeasonVarietyProperties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MarketSeasonVarietyProperty) {
        return item.id;
    }
    registerChangeInMarketSeasonVarietyProperties() {
        this.eventSubscriber = this.eventManager.subscribe('marketSeasonVarietyPropertyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
