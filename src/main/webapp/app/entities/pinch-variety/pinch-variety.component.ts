import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { PinchVariety } from './pinch-variety.model';
import { PinchVarietyService } from './pinch-variety.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pinch-variety',
    templateUrl: './pinch-variety.component.html'
})
export class PinchVarietyComponent implements OnInit, OnDestroy {
pinchVarieties: PinchVariety[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pinchVarietyService: PinchVarietyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pinchVarietyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.pinchVarieties = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPinchVarieties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PinchVariety) {
        return item.id;
    }
    registerChangeInPinchVarieties() {
        this.eventSubscriber = this.eventManager.subscribe('pinchVarietyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
