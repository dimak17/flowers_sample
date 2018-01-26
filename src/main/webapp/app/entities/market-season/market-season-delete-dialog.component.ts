import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {EventManager} from 'ng-jhipster';

import {MarketSeason} from './market-season.model';
import {MarketSeasonPopupService} from './market-season-popup.service';
import {MarketSeasonService} from './market-season.service';

@Component({
    selector: 'jhi-market-season-delete-dialog',
    templateUrl: './market-season-delete-dialog.component.html'
})
export class MarketSeasonDeleteDialogComponent {

    marketSeason: MarketSeason;

    constructor(
        private marketSeasonService: MarketSeasonService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketSeasonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketSeasonListModification',
                content: 'Deleted an marketSeason'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-season-delete-popup',
    template: ''
})
export class MarketSeasonDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketSeasonPopupService: MarketSeasonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketSeasonPopupService
                .open(MarketSeasonDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
