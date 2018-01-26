import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { PaymentPolicy } from './payment-policy.model';
import { PaymentPolicyService } from './payment-policy.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-payment-policy',
    templateUrl: './payment-policy.component.html'
})
export class PaymentPolicyComponent implements OnInit, OnDestroy {
paymentPolicies: PaymentPolicy[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private paymentPolicyService: PaymentPolicyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.paymentPolicyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.paymentPolicies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPaymentPolicies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PaymentPolicy) {
        return item.id;
    }
    registerChangeInPaymentPolicies() {
        this.eventSubscriber = this.eventManager.subscribe('paymentPolicyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
