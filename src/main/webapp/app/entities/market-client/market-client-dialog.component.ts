import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MarketClient } from './market-client.model';
import { MarketClientPopupService } from './market-client-popup.service';
import { MarketClientService } from './market-client.service';
import { Market, MarketService } from '../market';
import { ResponseWrapper } from '../../shared';
import {Client} from '../client/client.model';
import {ClientService} from '../../admin/client/client.service';

@Component({
    selector: 'jhi-market-client-dialog',
    templateUrl: './market-client-dialog.component.html'
})
export class MarketClientDialogComponent implements OnInit {

    marketClient: MarketClient;
    authorities: any[];
    isSaving: boolean;

    markets: Market[];

    clients: Client[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketClientService: MarketClientService,
        private marketService: MarketService,
        private clientService: ClientService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.marketService.query()
            .subscribe((res: ResponseWrapper) => { this.markets = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.clientService.query()
            .subscribe((res: ResponseWrapper) => { this.clients = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketClient.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketClientService.update(this.marketClient), false);
        } else {
            this.subscribeToSaveResponse(
                this.marketClientService.create(this.marketClient), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketClient>, isCreated: boolean) {
        result.subscribe((res: MarketClient) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketClient, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.marketClient.created'
            : 'flowersApp.marketClient.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'marketClientListModification', content: 'OK'});
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

    trackClientById(index: number, item: Client) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-market-client-popup',
    template: ''
})
export class MarketClientPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketClientPopupService: MarketClientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.marketClientPopupService
                    .open(MarketClientDialogComponent, params['id']);
            } else {
                this.modalRef = this.marketClientPopupService
                    .open(MarketClientDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
