import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PaymentPolicy } from './payment-policy.model';
import { PaymentPolicyService } from './payment-policy.service';

@Component({
    selector: 'jhi-payment-policy-detail',
    templateUrl: './payment-policy-detail.component.html'
})
export class PaymentPolicyDetailComponent implements OnInit, OnDestroy {

    paymentPolicy: PaymentPolicy;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private paymentPolicyService: PaymentPolicyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPaymentPolicies();
    }

    load(id) {
        this.paymentPolicyService.find(id).subscribe((paymentPolicy) => {
            this.paymentPolicy = paymentPolicy;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPaymentPolicies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'paymentPolicyListModification',
            (response) => this.load(this.paymentPolicy.id)
        );
    }
}
