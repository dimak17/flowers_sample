import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { LabelCountry } from './label-country.model';
import { LabelCountryService } from './label-country.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-label-country',
    templateUrl: './label-country.component.html'
})
export class LabelCountryComponent implements OnInit, OnDestroy {
labelCountries: LabelCountry[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private labelCountryService: LabelCountryService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.labelCountryService.query().subscribe(
            (res: ResponseWrapper) => {
                this.labelCountries = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLabelCountries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LabelCountry) {
        return item.id;
    }
    registerChangeInLabelCountries() {
        this.eventSubscriber = this.eventManager.subscribe('labelCountryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
