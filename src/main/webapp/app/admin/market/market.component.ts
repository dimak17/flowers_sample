import {Component, OnDestroy, OnInit} from '@angular/core';
import {MarketService} from './market.service';
import {EventManager} from 'ng-jhipster';
import {Colors} from '../../shared/constants/enum.constants';
import {MarketUi} from './market-ui.model';
import Collections = require('typescript-collections');
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'jhi-flowers-market',
  templateUrl: './market.component.html',
  styleUrls: ['./market.scss'],
  providers: [MarketService]
})
export class MarketComponent implements OnInit, OnDestroy {
    markets: MarketUi[];

    subscriptions: Subscription[] = [];
    constructor(
        private marketService: MarketService,
        private eventManager: EventManager
    ) {

    }

    ngOnInit() {
        const sub = this.eventManager
            .subscribe('changeMarketEvent', (res) => this.loadAll());
        this.subscriptions.push(sub);
        this.loadAll();
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach((s) => s.unsubscribe());
    }

    loadAll() {
        const sub = this.marketService.findAll().subscribe(
            (res) => {
                this.markets = res;
                this.fillColor();
            });
        this.subscriptions.push(sub);
    }

    public fillColor() {
        const collection = new Collections.Bag();
        let i = 0;
        let j = 0;
        while (collection.size() < this.markets.length) {
            if (collection.size() == (Object.keys(Colors).length / 2)){
                j = 0;
            }
            const color = Colors[j++].toLowerCase();
            collection.add(color);
            this.markets[i].colorClass = color;
            localStorage.setItem(this.markets[i].id.toString(), color);
            i++;
        }
    }

    trackId(index: number, item: MarketUi) {
        return item.id;
    }
}
