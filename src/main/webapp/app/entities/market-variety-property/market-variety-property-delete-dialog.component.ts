import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {EventManager} from 'ng-jhipster';

import {MarketVarietyProperty} from './market-variety-property.model';
import {MarketVarietyPropertyPopupService} from './market-variety-property-popup.service';
import {MarketVarietyPropertyService} from './market-variety-property.service';

@Component({
    selector: 'jhi-market-variety-property-delete-dialog',
    templateUrl: './market-variety-property-delete-dialog.component.html'
})
export class MarketVarietyPropertyDeleteDialogComponent {

    marketVarietyProperty: MarketVarietyProperty;

    constructor(
        private marketVarietyPropertyService: MarketVarietyPropertyService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketVarietyPropertyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketVarietyPropertyListModification',
                content: 'Deleted an marketVarietyProperty'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-variety-property-delete-popup',
    template: ''
})
export class MarketVarietyPropertyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketVarietyPropertyPopupService: MarketVarietyPropertyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketVarietyPropertyPopupService
                .open(MarketVarietyPropertyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
