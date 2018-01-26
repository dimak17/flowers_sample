import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Rx';
import {AlertService, EventManager} from 'ng-jhipster';
import {BoxTypeService} from './box-type.service';
import {Principal} from '../../shared';
import {BoxTypeUi} from './box-type-ui.model';
import Collections = require('typescript-collections');
import {Colors} from '../../shared/constants/colors.constants';

@Component({
    selector: 'jhi-box-type',
    templateUrl: './box-type.component.html',
    styleUrls: ['./box-type.component.scss']
})
export class BoxTypeComponent implements OnInit, OnDestroy {

    boxTypes: BoxTypeUi[];
    currentAccount: any;
    eventSubscriber: Subscription;
    page: any;
    predicate: any;
    reverse: any;
    defaultBoxTypes: string[] = ['QB', 'HB', 'FB'];

    constructor(private boxTypeService: BoxTypeService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private principal: Principal) {
        this.boxTypes = [];
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.boxTypeService.getAllBoxTypes().subscribe(
            (boxTypes) => this.onSuccess(boxTypes),
            (boxTypes) => this.onError(boxTypes)
        );
    }

    reset() {
        this.page = 0;
        this.boxTypes = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account.user;
        });
        this.registerChangeInBoxTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    public trackDefaultBoxTypes(boxTypeShortName: string): boolean {
        for (const i in this.defaultBoxTypes) {
            if (this.defaultBoxTypes[i] === boxTypeShortName) {
                return false;
            }
        }
        return true;
    }

    trackId(index: number, item: BoxTypeUi) {
        return item.id;
    }

    registerChangeInBoxTypes() {
        this.eventSubscriber = this.eventManager.subscribe('boxTypeListModification', (response) => this.reset());
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
            this.boxTypes.push(data[i]);
        }
            if (this.boxTypes && this.boxTypes.length) {
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

        while (collection.size() < this.boxTypes.length) {

            if ( j === (Object.keys(Colors).length / 2)) {
                j = 0;
            }
            const color = Colors[j++].toLowerCase();
            collection.add(color);
            this.boxTypes[i].colorClass = color;
            localStorage.setItem(this.boxTypes[i].id.toString(), color);
            i++;
        }
    }
}
