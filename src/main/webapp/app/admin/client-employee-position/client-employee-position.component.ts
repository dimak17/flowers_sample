import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ClientEmployeePositionService } from './client-employee-position.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import {ClientEmployeePosition} from '../../entities/client-employee-position/client-employee-position.model';

@Component({
    selector: 'jhi-client-employee-position',
    templateUrl: './client-employee-position.component.html'
})
export class ClientEmployeePositionComponent implements OnInit, OnDestroy {
clientEmployeePositions: ClientEmployeePosition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private clientEmployeePositionService: ClientEmployeePositionService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.clientEmployeePositionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.clientEmployeePositions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClientEmployeePositions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ClientEmployeePosition) {
        return item.id;
    }
    registerChangeInClientEmployeePositions() {
        this.eventSubscriber = this.eventManager.subscribe('clientEmployeePositionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
