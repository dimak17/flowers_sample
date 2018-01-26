import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Market } from './market.model';
import { MarketPopupService } from './market-popup.service';
import { MarketService } from './market.service';

@Component({
    selector: 'jhi-market-delete-dialog',
    templateUrl: './market-delete-dialog.component.html'
})
export class MarketDeleteDialogComponent {

    market: Market;

    constructor(
        private marketService: MarketService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketListModification',
                content: 'Deleted an market'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-delete-popup',
    template: ''
})
export class MarketDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketPopupService: MarketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketPopupService
                .open(MarketDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
