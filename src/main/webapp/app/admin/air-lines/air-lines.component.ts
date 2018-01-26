import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks,  AlertService } from 'ng-jhipster';
import { AirLines } from './air-lines.model';
import { AirLinesService } from './air-lines.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import Collections = require('typescript-collections');

@Component({
    selector: 'jhi-air-lines',
    templateUrl: './air-lines.component.html',
    styleUrls: ['./air-lines.component.scss']
})
export class AirLinesComponent implements OnInit, OnDestroy {

    airLines: AirLines[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    totalItems: number;
    predicate: any;
    reverse: any;

    constructor(private airLinesService: AirLinesService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private parseLinks: ParseLinks,
                private principal: Principal) {
        this.airLines = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.airLinesService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    reset() {
        this.page = 0;
        this.airLines = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAirLines();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AirLines) {
        return item.id;
    }

    registerChangeInAirLines() {
        this.eventSubscriber = this.eventManager.subscribe('airLinesListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.airLines.push(data[i]);
        }
        if (this.airLines && this.airLines.length) {
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
        while (collection.size() < this.airLines.length) {
            if (j === (Object.keys(Colors).length / 2)) {
                j = 0;
            }
            const color = Colors[j++].toLowerCase();
            collection.add(color);
            this.airLines[i].colorClass = color;
            localStorage.setItem(this.airLines[i].id.toString(), color);
            i++;
        }
    }
}

export enum Colors {RED, GREEN, BLUE, GOLD, BROWN, VIOLET, PINK, AQUA }
