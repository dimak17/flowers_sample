import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { MarketVariety } from './market-variety.model';
import { MarketVarietyPopupService } from './market-variety-popup.service';
import { MarketVarietyService } from './market-variety.service';

@Component({
    selector: 'jhi-market-variety-delete-dialog',
    templateUrl: './market-variety-delete-dialog.component.html'
})
export class MarketVarietyDeleteDialogComponent {

    marketVariety: MarketVariety;

    constructor(
        private marketVarietyService: MarketVarietyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketVarietyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketVarietyListModification',
                content: 'Deleted an marketVariety'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.marketVariety.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-market-variety-delete-popup',
    template: ''
})
export class MarketVarietyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketVarietyPopupService: MarketVarietyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.marketVarietyPopupService
                .open(MarketVarietyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
