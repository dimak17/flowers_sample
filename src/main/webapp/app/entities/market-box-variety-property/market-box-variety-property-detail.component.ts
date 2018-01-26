import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { MarketBoxVarietyProperty } from './market-box-variety-property.model';
import { MarketBoxVarietyPropertyService } from './market-box-variety-property.service';

@Component({
    selector: 'jhi-market-box-variety-property-detail',
    templateUrl: './market-box-variety-property-detail.component.html'
})
export class MarketBoxVarietyPropertyDetailComponent implements OnInit, OnDestroy {

    marketBoxVarietyProperty: MarketBoxVarietyProperty;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private marketBoxVarietyPropertyService: MarketBoxVarietyPropertyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketBoxVarietyProperties();
    }

    load(id) {
        this.marketBoxVarietyPropertyService.find(id).subscribe((marketBoxVarietyProperty) => {
            this.marketBoxVarietyProperty = marketBoxVarietyProperty;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketBoxVarietyProperties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketBoxVarietyPropertyListModification',
            (response) => this.load(this.marketBoxVarietyProperty.id)
        );
    }
}
