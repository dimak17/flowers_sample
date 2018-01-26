import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { MarketVariety } from './market-variety.model';
import { MarketVarietyService } from './market-variety.service';

@Component({
    selector: 'jhi-market-variety-detail',
    templateUrl: './market-variety-detail.component.html'
})
export class MarketVarietyDetailComponent implements OnInit, OnDestroy {

    marketVariety: MarketVariety;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private marketVarietyService: MarketVarietyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketVarieties();
    }

    load(id) {
        this.marketVarietyService.find(id).subscribe((marketVariety) => {
            this.marketVariety = marketVariety;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketVarieties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketVarietyListModification',
            (response) => this.load(this.marketVariety.id)
        );
    }
}
