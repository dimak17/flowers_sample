import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { MarketClient } from './market-client.model';
import { MarketClientService } from './market-client.service';

@Component({
    selector: 'jhi-market-client-detail',
    templateUrl: './market-client-detail.component.html'
})
export class MarketClientDetailComponent implements OnInit, OnDestroy {

    marketClient: MarketClient;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private marketClientService: MarketClientService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketClients();
    }

    load(id) {
        this.marketClientService.find(id).subscribe((marketClient) => {
            this.marketClient = marketClient;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketClients() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketClientListModification',
            (response) => this.load(this.marketClient.id)
        );
    }
}
