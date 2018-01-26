import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { MarketClient } from './market-client.model';
import { MarketClientService } from './market-client.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-market-client',
    templateUrl: './market-client.component.html'
})
export class MarketClientComponent implements OnInit, OnDestroy {
marketClients: MarketClient[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private marketClientService: MarketClientService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.marketClientService.query().subscribe(
            (res: ResponseWrapper) => {
                this.marketClients = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMarketClients();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MarketClient) {
        return item.id;
    }
    registerChangeInMarketClients() {
        this.eventSubscriber = this.eventManager.subscribe('marketClientListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
