import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {EventManager} from 'ng-jhipster';

import {MarketSeasonVarietyProperty} from './market-season-variety-property.model';
import {MarketSeasonVarietyPropertyPopupService} from './market-season-variety-property-popup.service';
import {MarketSeasonVarietyPropertyService} from './market-season-variety-property.service';

@Component({
    selector: 'jhi-market-season-variety-property-delete-dialog',
    templateUrl: './market-season-variety-property-delete-dialog.component.html'
})
export class MarketSeasonVarietyPropertyDeleteDialogComponent {

    marketSeasonVarietyProperty: MarketSeasonVarietyProperty;

    constructor(private marketSeasonVarietyPropertyService: MarketSeasonVarietyPropertyService,
                public activeModal: NgbActiveModal,
                private eventManager: EventManager) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketSeasonVarietyPropertyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketSeasonVarietyPropertyListModification',
                content: 'Deleted an marketSeasonVarietyProperty'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-season-variety-property-delete-popup',
    template: ''
})
export class MarketSeasonVarietyPropertyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private marketSeasonVarietyPropertyPopupService: MarketSeasonVarietyPropertyPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketSeasonVarietyPropertyPopupService
                .open(MarketSeasonVarietyPropertyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
