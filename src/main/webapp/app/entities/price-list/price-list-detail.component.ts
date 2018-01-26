import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PriceList } from './price-list.model';
import { PriceListService } from './price-list.service';

@Component({
    selector: 'jhi-price-list-detail',
    templateUrl: './price-list-detail.component.html'
})
export class PriceListDetailComponent implements OnInit, OnDestroy {

    priceList: PriceList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private priceListService: PriceListService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPriceLists();
    }

    load(id) {
        this.priceListService.find(id).subscribe((priceList) => {
            this.priceList = priceList;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPriceLists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'priceListListModification',
            (response) => this.load(this.priceList.id)
        );
    }
}
