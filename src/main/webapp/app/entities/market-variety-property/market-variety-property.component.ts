import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {AlertService, EventManager} from 'ng-jhipster';

import {MarketVarietyProperty} from './market-variety-property.model';
import {MarketVarietyPropertyService} from './market-variety-property.service';
import {Principal, ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-market-variety-property',
    templateUrl: './market-variety-property.component.html'
})
export class MarketVarietyPropertyComponent implements OnInit, OnDestroy {
marketVarietyProperties: MarketVarietyProperty[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private marketVarietyPropertyService: MarketVarietyPropertyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.marketVarietyPropertyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.marketVarietyProperties = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMarketVarietyProperties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MarketVarietyProperty) {
        return item.id;
    }
    registerChangeInMarketVarietyProperties() {
        this.eventSubscriber = this.eventManager.subscribe('marketVarietyPropertyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
