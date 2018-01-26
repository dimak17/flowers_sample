import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Market } from './market.model';
import { MarketPopupService } from './market-popup.service';
import { MarketService } from './market.service';
import { BoxGroup, BoxGroupService } from '../box-group';
import { CompanyUser, CompanyUserService } from '../company-user';
import { ResponseWrapper } from '../../shared';
import {Company} from '../company/company.model';
import {CompanyService} from '../company/company.service';

@Component({
    selector: 'jhi-market-dialog',
    templateUrl: './market-dialog.component.html'
})
export class MarketDialogComponent implements OnInit {

    market: Market;
    isSaving: boolean;

    companies: Company[];

    boxgroups: BoxGroup[];

    companyusers: CompanyUser[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private marketService: MarketService,
        private comapnyService: CompanyService,
        private boxGroupService: BoxGroupService,
        private companyUserService: CompanyUserService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.comapnyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.boxGroupService.query()
            .subscribe((res: ResponseWrapper) => { this.boxgroups = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyUserService.query()
            .subscribe((res: ResponseWrapper) => { this.companyusers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.market.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketService.update(this.market));
        } else {
            this.subscribeToSaveResponse(
                this.marketService.create(this.market));
        }
    }

    private subscribeToSaveResponse(result: Observable<Market>) {
        result.subscribe((res: Market) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Market) {
        this.eventManager.broadcast({ name: 'marketListModification', content: 'OK'});
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

    trackComapnyById(index: number, item: Company) {
        return item.id;
    }

    trackBoxGroupById(index: number, item: BoxGroup) {
        return item.id;
    }

    trackCompanyUserById(index: number, item: CompanyUser) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-market-popup',
    template: ''
})
export class MarketPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketPopupService: MarketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketPopupService
                    .open(MarketDialogComponent as Component, params['id']);
            } else {
                this.marketPopupService
                    .open(MarketDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
