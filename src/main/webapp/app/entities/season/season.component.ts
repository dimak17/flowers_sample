import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {AlertService, EventManager} from 'ng-jhipster';

import {Season} from './season.model';
import {SeasonService} from './season.service';
import {Principal, ResponseWrapper} from '../../shared';

@Component({
    selector: 'jhi-season',
    templateUrl: './season.component.html'
})
export class SeasonComponent implements OnInit, OnDestroy {
seasons: Season[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private seasonService: SeasonService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.seasonService.query().subscribe(
            (res: ResponseWrapper) => {
                this.seasons = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSeasons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Season) {
        return item.id;
    }
    registerChangeInSeasons() {
        this.eventSubscriber = this.eventManager.subscribe('seasonListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
