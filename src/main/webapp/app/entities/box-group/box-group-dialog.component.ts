import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { BoxGroup } from './box-group.model';
import { BoxGroupPopupService } from './box-group-popup.service';
import { BoxGroupService } from './box-group.service';
import { Market, MarketService } from '../market';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-box-group-dialog',
    templateUrl: './box-group-dialog.component.html'
})
export class BoxGroupDialogComponent implements OnInit {

    boxGroup: BoxGroup;
    authorities: any[];
    isSaving: boolean;

    markets: Market[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private boxGroupService: BoxGroupService,
        private marketService: MarketService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.marketService.query()
            .subscribe((res: ResponseWrapper) => { this.markets = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.boxGroup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.boxGroupService.update(this.boxGroup), false);
        } else {
            this.subscribeToSaveResponse(
                this.boxGroupService.create(this.boxGroup), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BoxGroup>, isCreated: boolean) {
        result.subscribe((res: BoxGroup) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BoxGroup, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.boxGroup.created'
            : 'flowersApp.boxGroup.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'boxGroupListModification', content: 'OK'});
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
    selector: 'jhi-box-group-popup',
    template: ''
})
export class BoxGroupPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private boxGroupPopupService: BoxGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.boxGroupPopupService
                    .open(BoxGroupDialogComponent, params['id']);
            } else {
                this.modalRef = this.boxGroupPopupService
                    .open(BoxGroupDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
