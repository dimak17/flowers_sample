import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { MarketClient } from './market-client.model';
import { MarketClientPopupService } from './market-client-popup.service';
import { MarketClientService } from './market-client.service';

@Component({
    selector: 'jhi-market-client-delete-dialog',
    templateUrl: './market-client-delete-dialog.component.html'
})
export class MarketClientDeleteDialogComponent {

    marketClient: MarketClient;

    constructor(
        private marketClientService: MarketClientService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketClientService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketClientListModification',
                content: 'Deleted an marketClient'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.marketClient.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-market-client-delete-popup',
    template: ''
})
export class MarketClientDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketClientPopupService: MarketClientPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.marketClientPopupService
                .open(MarketClientDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
