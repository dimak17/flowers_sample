import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { DestinationService } from './destination.service';
import { Principal, ResponseWrapper } from '../../shared';
import {Destination} from '../../entities/destination/destination.model';

@Component({
    selector: 'jhi-destination',
    templateUrl: './destination.component.html'
})
export class DestinationComponent implements OnInit, OnDestroy {
destinations: Destination[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private destinationService: DestinationService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        // this.destinationService.query().subscribe(
        //     (res: ResponseWrapper) => {
        //         this.destinations = res.json;
        //     },
        //     (res: ResponseWrapper) => this.onError(res.json)
        // );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDestinations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Destination) {
        return item.id;
    }
    registerChangeInDestinations() {
        this.eventSubscriber = this.eventManager.subscribe('destinationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
