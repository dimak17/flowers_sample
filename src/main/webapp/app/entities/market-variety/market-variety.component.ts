import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { MarketVariety } from './market-variety.model';
import { MarketVarietyService } from './market-variety.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-market-variety',
    templateUrl: './market-variety.component.html'
})
export class MarketVarietyComponent implements OnInit, OnDestroy {
marketVarieties: MarketVariety[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private marketVarietyService: MarketVarietyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.marketVarietyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.marketVarieties = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMarketVarieties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MarketVariety) {
        return item.id;
    }
    registerChangeInMarketVarieties() {
        this.eventSubscriber = this.eventManager.subscribe('marketVarietyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
