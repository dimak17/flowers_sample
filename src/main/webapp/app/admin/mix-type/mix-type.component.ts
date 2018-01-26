import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {AlertService, EventManager} from 'ng-jhipster';
import {MixTypeService} from './mix-type.service';
import {Principal} from '../../shared';
import {MixType} from './mix-type.model';
import {CompanyUser} from '../../entities/company-user/company-user.model';
import Collections = require('typescript-collections');

@Component({
    selector: 'jhi-flowers-mix-type',
    templateUrl: './mix-type.component.html',
    styleUrls: ['./mix-type.component.scss']
})
export class MixTypeComponent implements OnInit, OnDestroy {

    mixTypes: MixType[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    companyUser: CompanyUser;

    constructor(private mixTypeService: MixTypeService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private principal: Principal) {
        this.mixTypes = [];

    }

    ngOnInit() {
        this.loadAll();
        this.registerChangeInBoxTypes();
    }

    loadAll() {
        this.mixTypeService.getMixTypes().subscribe(
            (mixTypes) => this.onSuccess(mixTypes),
            (mixTypes) => this.onError(mixTypes.error)
        );
    }

    reset() {
        this.mixTypes = [];
        this.loadAll();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MixType) {
        return item.id;
    }

    checkMixTypeName(name: string): boolean {
        return (name === 'Mix color');
    }

    registerChangeInBoxTypes() {
        this.eventSubscriber = this.eventManager.subscribe('mixTypeListModification', (response) => this.reset());
    }

    private onSuccess(data) {
        for (let i = 0; i < data.length; i++) {
            this.mixTypes.push(data[i]);
        }
        if (this.mixTypes && this.mixTypes.length) {
            this.fillColor();
        }

    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    public fillColor() {
        const collection = new Collections.Bag();
        let i = 0;
        let j = 0;
        while (collection.size() < this.mixTypes.length) {

            if (j === (Object.keys(Colors).length / 2)) {
                j = 0;
            }
            const color = Colors[j++].toLowerCase();
            collection.add(color);
            this.mixTypes[i].colorClass = color;
            localStorage.setItem(this.mixTypes[i].id.toString(), color);
            i++;
        }
    }
}

export enum Colors {RED, GREEN, BLUE, GOLD, BROWN, VIOLET, PINK, AQUA }
