import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { TypeOfFlower } from './type-of-flower.model';
import { TypeOfFlowerService } from './type-of-flower.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-type-of-flower',
    templateUrl: './type-of-flower.component.html'
})
export class TypeOfFlowerComponent implements OnInit, OnDestroy {
typeOfFlowers: TypeOfFlower[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private typeOfFlowerService: TypeOfFlowerService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.typeOfFlowerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.typeOfFlowers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTypeOfFlowers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TypeOfFlower) {
        return item.id;
    }
    registerChangeInTypeOfFlowers() {
        this.eventSubscriber = this.eventManager.subscribe('typeOfFlowerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
