import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { PriceList } from './price-list.model';
import { PriceListService } from './price-list.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-price-list',
    templateUrl: './price-list.component.html'
})
export class PriceListComponent implements OnInit, OnDestroy {
priceLists: PriceList[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private priceListService: PriceListService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.priceListService.query().subscribe(
            (res: ResponseWrapper) => {
                this.priceLists = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPriceLists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PriceList) {
        return item.id;
    }
    registerChangeInPriceLists() {
        this.eventSubscriber = this.eventManager.subscribe('priceListListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
