import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MarketBox } from './market-box.model';
import { MarketBoxPopupService } from './market-box-popup.service';
import { MarketBoxService } from './market-box.service';
import { Market, MarketService } from '../market';
import { ResponseWrapper } from '../../shared';
import {BoxType} from '../box-type/box-type.model';
import {BoxTypeService} from '../../admin/box-type/box-type.service';

@Component({
    selector: 'jhi-market-box-dialog',
    templateUrl: './market-box-dialog.component.html'
})
export class MarketBoxDialogComponent implements OnInit {

    marketBox: MarketBox;
    authorities: any[];
    isSaving: boolean;

    markets: Market[];

    boxtypes: BoxType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketBoxService: MarketBoxService,
        private marketService: MarketService,
        private boxTypeService: BoxTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.marketService.query()
            .subscribe((res: ResponseWrapper) => { this.markets = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.boxTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.boxtypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketBox.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketBoxService.update(this.marketBox), false);
        } else {
            this.subscribeToSaveResponse(
                this.marketBoxService.create(this.marketBox), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketBox>, isCreated: boolean) {
        result.subscribe((res: MarketBox) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketBox, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.marketBox.created'
            : 'flowersApp.marketBox.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'marketBoxListModification', content: 'OK'});
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

    trackBoxTypeById(index: number, item: BoxType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-box-popup',
    template: ''
})
export class MarketBoxPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketBoxPopupService: MarketBoxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.marketBoxPopupService
                    .open(MarketBoxDialogComponent, params['id']);
            } else {
                this.modalRef = this.marketBoxPopupService
                    .open(MarketBoxDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
