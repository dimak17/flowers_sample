import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {AlertService, EventManager} from 'ng-jhipster';

import {Market} from './market.model';
import {MarketService} from './market.service';
import {Principal, ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-market',
    templateUrl: './market.component.html'
})
export class MarketComponent implements OnInit, OnDestroy {
markets: Market[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private marketService: MarketService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.marketService.query().subscribe(
            (res: ResponseWrapper) => {
                this.markets = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMarkets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Market) {
        return item.id;
    }
    registerChangeInMarkets() {
        this.eventSubscriber = this.eventManager.subscribe('marketListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
