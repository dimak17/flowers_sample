import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {AlertService, EventManager} from 'ng-jhipster';

import {PaymentPolicyService} from './payment-policy.service';
import {Principal, ResponseWrapper} from '../../shared';
import {PaymentPolicy} from '../../entities/payment-policy/payment-policy.model';

@Component({
    selector: 'jhi-payment-policy',
    templateUrl: './payment-policy.component.html',
    styleUrls: ['./payment-policy.component.scss']
})
export class PaymentPolicyComponent implements OnInit, OnDestroy {
    paymentPolicy: PaymentPolicy[];
    currentAccount: any;
    eventSubscriber: Subscription;
    defaultPaymentPolicyOne: string []= ['Credit'];
    defaultPaymentPolicyTwo: string []= ['Prepay'];

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
                this.paymentPolicy = res.json;
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

    public trackDefaultPaymentPolicyOne(paymentPolicyName: string): boolean {
        return this.defaultPaymentPolicyOne.indexOf(paymentPolicyName) === -1;

    }
    public trackDefaultPaymentPolicyTwo(paymentPolicyName: string): boolean {
        return this.defaultPaymentPolicyTwo.indexOf(paymentPolicyName) === -1;

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
