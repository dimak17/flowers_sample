import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import {AlertService, EventManager, ParseLinks} from 'ng-jhipster';

import { ClaimsPolicyUi } from './claimsui-policy.model';
import { ClaimsPolicyService } from './claims-policy.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import Collections = require('typescript-collections');
import {Colors} from '../../shared/constants/colors.constants';

@Component({
    selector: 'jhi-claims-policy',
    templateUrl: './claims-policy.component.html',
    styleUrls: ['./claims-policy.component.scss']

})
export class ClaimsPolicyComponent implements OnInit, OnDestroy {

    claimsPolicies: ClaimsPolicyUi[];
    currentAccount: any;
    eventSubscriber: Subscription;
    updateShippingData: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    defaultClaimsPolicy: string []= ['FOB'];

    constructor(private claimsPolicyService: ClaimsPolicyService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private parseLinks: ParseLinks,
                private principal: Principal) {
        this.claimsPolicies = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.claimsPolicyService.getAllClaimsPolicies().subscribe(
            (claimsPolicy) => this.onSuccess(claimsPolicy),
            (claimsPolicy) => this.onError(claimsPolicy)
        );
    }

    reset() {
        this.page = 0;
        this.claimsPolicies = [];
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClaimsPolicies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.eventManager.destroy(this.updateShippingData);
    }

    public trackDefaultClaimsPolicy(claimsPolicyShortName: string): boolean {
        return this.defaultClaimsPolicy.indexOf(claimsPolicyShortName) === -1;
    }

    trackId(index: number, item: ClaimsPolicyUi) {
        return item.id;
    }

    registerChangeInClaimsPolicies() {
        this.eventSubscriber = this.eventManager.subscribe('claimsPolicyListModification', (response) => this.reset());
        this.updateShippingData = this.eventManager.subscribe('shippingPolicyListModification', (response) => this.reset());
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
            this.claimsPolicies.push(data[i]);
        }
        if (this.claimsPolicies && this.claimsPolicies.length) {
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

        while (collection.size() < this.claimsPolicies.length) {

            if (j === (Object.keys(Colors).length / 2)) {
                j = 0;
            }
            const color = Colors[j++].toLowerCase();
            collection.add(color);
            this.claimsPolicies[i].colorClass = color;
            localStorage.setItem(this.claimsPolicies[i].id.toString(), color);
            i++;
        }
    }

}
