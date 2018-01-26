import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { MarketBox } from './market-box.model';
import { MarketBoxPopupService } from './market-box-popup.service';
import { MarketBoxService } from './market-box.service';

@Component({
    selector: 'jhi-market-box-delete-dialog',
    templateUrl: './market-box-delete-dialog.component.html'
})
export class MarketBoxDeleteDialogComponent {

    marketBox: MarketBox;

    constructor(
        private marketBoxService: MarketBoxService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.marketBoxService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketBoxListModification',
                content: 'Deleted an marketBox'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.marketBox.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-market-box-delete-popup',
    template: ''
})
export class MarketBoxDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketBoxPopupService: MarketBoxPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.marketBoxPopupService
                .open(MarketBoxDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
