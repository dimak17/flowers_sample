import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {AlertService, EventManager, ParseLinks} from 'ng-jhipster';

import {ShippingPolicyUi} from './shippingui-policy.model';
import {ShippingPolicyService} from './shipping-policy.service';
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import Collections = require('typescript-collections');
import {Colors} from '../../shared/constants/colors.constants';

@Component({
    selector: 'jhi-shipping-policy',
    templateUrl: './shipping-policy.component.html',
    styleUrls: ['./shipping-policy.component.scss']
})
export class ShippingPolicyComponent implements OnInit, OnDestroy {
    shippingPolicies: ShippingPolicyUi[];
    currentAccount: any;
    eventSubscriber: Subscription;
    updateClaimsData: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    defaultShippingPolicy: string []= ['FOB'];

    constructor(
        private shippingPolicyService: ShippingPolicyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private parseLinks: ParseLinks,
        private principal: Principal) {
            this.shippingPolicies = [];
            this.itemsPerPage = ITEMS_PER_PAGE;
            this.page = 0;
            this.links = {
                    last: 0
                };
            this.predicate = 'id';
            this.reverse = true;
    }

    loadAll() {
        this.shippingPolicyService.getAllShippingPolicies().subscribe(
            (shippingPolicy) => this.onSuccess(shippingPolicy),
            (shippingPolicy) => this.onError(shippingPolicy)
        );
    }
    reset() {
        this.page = 0;
        this.shippingPolicies = [];
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInShippingPolicies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    public trackDefaultShippingPolicy(shippingPolicyShortName: string): boolean {
    return this.defaultShippingPolicy.indexOf(shippingPolicyShortName) === -1;

    }

    trackId(index: number, item: ShippingPolicyUi) {
        return item.id;
    }
    registerChangeInShippingPolicies() {
        this.eventSubscriber = this.eventManager.subscribe('shippingPolicyListModification', (response) => this.reset());
        this.updateClaimsData = this.eventManager.subscribe('claimsPolicyListModification', (response) => this.reset());

    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data) {
        for (let i = 0; i < data.length; i++) {
            this.shippingPolicies.push(data[i]);
        }
        if (this.shippingPolicies && this.shippingPolicies.length) {
            this.fillRandomColor();
        }

    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    public fillRandomColor() {
        const collection = new Collections.Bag();
        let i = 0;
        let j = 0;

        while (collection.size() < this.shippingPolicies.length) {

            if ( j === (Object.keys(Colors).length / 2)) {
                j = 0;
            }
            const color = Colors[j++].toLowerCase();
            collection.add(color);
            this.shippingPolicies[i].colorClass = color;
            localStorage.setItem(this.shippingPolicies[i].id.toString(), color);
            i++;
        }
    }
}
