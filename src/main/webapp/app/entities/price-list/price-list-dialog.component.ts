import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PriceList } from './price-list.model';
import { PriceListPopupService } from './price-list-popup.service';
import { PriceListService } from './price-list.service';

@Component({
    selector: 'jhi-price-list-dialog',
    templateUrl: './price-list-dialog.component.html'
})
export class PriceListDialogComponent implements OnInit {

    priceList: PriceList;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private priceListService: PriceListService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.priceList.id !== undefined) {
            this.subscribeToSaveResponse(
                this.priceListService.update(this.priceList), false);
        } else {
            this.subscribeToSaveResponse(
                this.priceListService.create(this.priceList), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PriceList>, isCreated: boolean) {
        result.subscribe((res: PriceList) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PriceList, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.priceList.created'
            : 'flowersApp.priceList.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'priceListListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-price-list-popup',
    template: ''
})
export class PriceListPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private priceListPopupService: PriceListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.priceListPopupService
                    .open(PriceListDialogComponent, params['id']);
            } else {
                this.modalRef = this.priceListPopupService
                    .open(PriceListDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
