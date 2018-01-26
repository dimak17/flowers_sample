import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {EventManager} from 'ng-jhipster';

import {MarketVarietyProperty} from './market-variety-property.model';
import {MarketVarietyPropertyService} from './market-variety-property.service';

@Component({
    selector: 'jhi-market-variety-property-detail',
    templateUrl: './market-variety-property-detail.component.html'
})
export class MarketVarietyPropertyDetailComponent implements OnInit, OnDestroy {

    marketVarietyProperty: MarketVarietyProperty;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private marketVarietyPropertyService: MarketVarietyPropertyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketVarietyProperties();
    }

    load(id) {
        this.marketVarietyPropertyService.find(id).subscribe((marketVarietyProperty) => {
            this.marketVarietyProperty = marketVarietyProperty;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketVarietyProperties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketVarietyPropertyListModification',
            (response) => this.load(this.marketVarietyProperty.id)
        );
    }
}
