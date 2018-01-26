import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { PinchVarietyProperty } from './pinch-variety-property.model';
import { PinchVarietyPropertyService } from './pinch-variety-property.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pinch-variety-property',
    templateUrl: './pinch-variety-property.component.html'
})
export class PinchVarietyPropertyComponent implements OnInit, OnDestroy {
pinchVarietyProperties: PinchVarietyProperty[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pinchVarietyPropertyService: PinchVarietyPropertyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pinchVarietyPropertyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.pinchVarietyProperties = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPinchVarietyProperties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PinchVarietyProperty) {
        return item.id;
    }
    registerChangeInPinchVarietyProperties() {
        this.eventSubscriber = this.eventManager.subscribe('pinchVarietyPropertyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
