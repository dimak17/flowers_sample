import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { MarketSeason } from './market-season.model';
import { MarketSeasonService } from './market-season.service';

@Component({
    selector: 'jhi-market-season-detail',
    templateUrl: './market-season-detail.component.html'
})
export class MarketSeasonDetailComponent implements OnInit, OnDestroy {

    marketSeason: MarketSeason;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private marketSeasonService: MarketSeasonService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketSeasons();
    }

    load(id) {
        this.marketSeasonService.find(id).subscribe((marketSeason) => {
            this.marketSeason = marketSeason;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketSeasons() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketSeasonListModification',
            (response) => this.load(this.marketSeason.id)
        );
    }
}
