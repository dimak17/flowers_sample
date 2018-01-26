import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { MarketBox } from './market-box.model';
import { MarketBoxService } from './market-box.service';

@Component({
    selector: 'jhi-market-box-detail',
    templateUrl: './market-box-detail.component.html'
})
export class MarketBoxDetailComponent implements OnInit, OnDestroy {

    marketBox: MarketBox;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private marketBoxService: MarketBoxService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketBoxes();
    }

    load(id) {
        this.marketBoxService.find(id).subscribe((marketBox) => {
            this.marketBox = marketBox;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketBoxes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketBoxListModification',
            (response) => this.load(this.marketBox.id)
        );
    }
}
