import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { MarketBoxVarietyProperty } from './market-box-variety-property.model';
import { MarketBoxVarietyPropertyService } from './market-box-variety-property.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-market-box-variety-property',
    templateUrl: './market-box-variety-property.component.html'
})
export class MarketBoxVarietyPropertyComponent implements OnInit, OnDestroy {
marketBoxVarietyProperties: MarketBoxVarietyProperty[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private marketBoxVarietyPropertyService: MarketBoxVarietyPropertyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.marketBoxVarietyPropertyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.marketBoxVarietyProperties = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMarketBoxVarietyProperties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MarketBoxVarietyProperty) {
        return item.id;
    }
    registerChangeInMarketBoxVarietyProperties() {
        this.eventSubscriber = this.eventManager.subscribe('marketBoxVarietyPropertyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
