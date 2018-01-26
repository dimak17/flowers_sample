import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { ClientPaymentPolicy } from './client-payment-policy.model';
import { ClientPaymentPolicyService } from './client-payment-policy.service';

@Component({
    selector: 'jhi-client-payment-policy-detail',
    templateUrl: './client-payment-policy-detail.component.html'
})
export class ClientPaymentPolicyDetailComponent implements OnInit, OnDestroy {

    clientPaymentPolicy: ClientPaymentPolicy;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private clientPaymentPolicyService: ClientPaymentPolicyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClientPaymentPolicies();
    }

    load(id) {
        this.clientPaymentPolicyService.find(id).subscribe((clientPaymentPolicy) => {
            this.clientPaymentPolicy = clientPaymentPolicy;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClientPaymentPolicies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clientPaymentPolicyListModification',
            (response) => this.load(this.clientPaymentPolicy.id)
        );
    }
}
