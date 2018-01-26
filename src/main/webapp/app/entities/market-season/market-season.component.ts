import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {AlertService, EventManager} from 'ng-jhipster';

import {MarketSeason} from './market-season.model';
import {MarketSeasonService} from './market-season.service';
import {Principal, ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-market-season',
    templateUrl: './market-season.component.html'
})
export class MarketSeasonComponent implements OnInit, OnDestroy {
    marketSeasons: MarketSeason[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(private marketSeasonService: MarketSeasonService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private principal: Principal) {
    }

    loadAll() {
        this.marketSeasonService.query().subscribe(
            (res: ResponseWrapper) => {
                this.marketSeasons = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMarketSeasons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MarketSeason) {
        return item.id;
    }

    registerChangeInMarketSeasons() {
        this.eventSubscriber = this.eventManager.subscribe('marketSeasonListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
