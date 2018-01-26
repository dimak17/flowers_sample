import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ClientPaymentPolicy } from './client-payment-policy.model';
import { ClientPaymentPolicyService } from './client-payment-policy.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-client-payment-policy',
    templateUrl: './client-payment-policy.component.html'
})
export class ClientPaymentPolicyComponent implements OnInit, OnDestroy {
clientPaymentPolicies: ClientPaymentPolicy[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private clientPaymentPolicyService: ClientPaymentPolicyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.clientPaymentPolicyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.clientPaymentPolicies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClientPaymentPolicies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ClientPaymentPolicy) {
        return item.id;
    }
    registerChangeInClientPaymentPolicies() {
        this.eventSubscriber = this.eventManager.subscribe('clientPaymentPolicyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
