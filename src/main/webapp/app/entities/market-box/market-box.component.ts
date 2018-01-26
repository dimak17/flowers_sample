import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { MarketBox } from './market-box.model';
import { MarketBoxService } from './market-box.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-market-box',
    templateUrl: './market-box.component.html'
})
export class MarketBoxComponent implements OnInit, OnDestroy {
marketBoxes: MarketBox[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private marketBoxService: MarketBoxService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.marketBoxService.query().subscribe(
            (res: ResponseWrapper) => {
                this.marketBoxes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMarketBoxes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MarketBox) {
        return item.id;
    }
    registerChangeInMarketBoxes() {
        this.eventSubscriber = this.eventManager.subscribe('marketBoxListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
