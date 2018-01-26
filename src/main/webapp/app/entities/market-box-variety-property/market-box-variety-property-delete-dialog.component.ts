import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { MarketBoxVarietyProperty } from './market-box-variety-property.model';
import { MarketBoxVarietyPropertyPopupService } from './market-box-variety-property-popup.service';
import { MarketBoxVarietyPropertyService } from './market-box-variety-property.service';

@Component({
    selector: 'jhi-market-box-variety-property-delete-dialog',
    templateUrl: './market-box-variety-property-delete-dialog.component.html'
})
export class MarketBoxVarietyPropertyDeleteDialogComponent {

    marketBoxVarietyProperty: MarketBoxVarietyProperty;

    constructor(
        private marketBoxVarietyPropertyService: MarketBoxVarietyPropertyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketBoxVarietyPropertyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketBoxVarietyPropertyListModification',
                content: 'Deleted an marketBoxVarietyProperty'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.marketBoxVarietyProperty.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-market-box-variety-property-delete-popup',
    template: ''
})
export class MarketBoxVarietyPropertyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketBoxVarietyPropertyPopupService: MarketBoxVarietyPropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.marketBoxVarietyPropertyPopupService
                .open(MarketBoxVarietyPropertyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
