import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MarketSeason } from './market-season.model';
import { MarketSeasonPopupService } from './market-season-popup.service';
import { MarketSeasonService } from './market-season.service';
import { Market, MarketService } from '../market';
import { ResponseWrapper } from '../../shared';
import {Season} from '../season/season.model';
import {SeasonService} from '../../admin/season/season.service';

@Component({
    selector: 'jhi-market-season-dialog',
    templateUrl: './market-season-dialog.component.html'
})
export class MarketSeasonDialogComponent implements OnInit {

    marketSeason: MarketSeason;
    isSaving: boolean;

    markets: Market[];

    seasons: Season[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketSeasonService: MarketSeasonService,
        private marketService: MarketService,
        private seasonService: SeasonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.marketService.query()
            .subscribe((res: ResponseWrapper) => { this.markets = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.seasonService.query()
            .subscribe((res: ResponseWrapper) => { this.seasons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketSeason.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketSeasonService.update(this.marketSeason));
        } else {
            this.subscribeToSaveResponse(
                this.marketSeasonService.create(this.marketSeason));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketSeason>) {
        result.subscribe((res: MarketSeason) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketSeason) {
        this.eventManager.broadcast({ name: 'marketSeasonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackMarketById(index: number, item: Market) {
        return item.id;
    }

    trackSeasonById(index: number, item: Season) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-season-popup',
    template: ''
})
export class MarketSeasonPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketSeasonPopupService: MarketSeasonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketSeasonPopupService
                    .open(MarketSeasonDialogComponent as Component, params['id']);
            } else {
                this.marketSeasonPopupService
                    .open(MarketSeasonDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
