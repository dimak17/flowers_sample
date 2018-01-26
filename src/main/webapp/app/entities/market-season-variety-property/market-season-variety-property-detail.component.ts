import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { MarketSeasonVarietyProperty } from './market-season-variety-property.model';
import { MarketSeasonVarietyPropertyService } from './market-season-variety-property.service';

@Component({
    selector: 'jhi-market-season-variety-property-detail',
    templateUrl: './market-season-variety-property-detail.component.html'
})
export class MarketSeasonVarietyPropertyDetailComponent implements OnInit, OnDestroy {

    marketSeasonVarietyProperty: MarketSeasonVarietyProperty;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private marketSeasonVarietyPropertyService: MarketSeasonVarietyPropertyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketSeasonVarietyProperties();
    }

    load(id) {
        this.marketSeasonVarietyPropertyService.find(id).subscribe((marketSeasonVarietyProperty) => {
            this.marketSeasonVarietyProperty = marketSeasonVarietyProperty;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketSeasonVarietyProperties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketSeasonVarietyPropertyListModification',
            (response) => this.load(this.marketSeasonVarietyProperty.id)
        );
    }
}
