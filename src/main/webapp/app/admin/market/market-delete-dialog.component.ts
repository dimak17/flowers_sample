import {Component, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager} from 'ng-jhipster';

import {MarketUi} from './market-ui.model';
import {MarketPopupService} from './market-popup.service';
import {MarketService} from './market.service';

@Component({
    selector: 'jhi-market-delete-dialog',
    templateUrl: './market-delete-dialog.component.html',
})
export class MarketDeletePopupDialogComponent {

    market: MarketUi;

    constructor(
        private marketService: MarketService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
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
        this.alertService.success('flowersApp.market.deleted', { param : id }, null);
    }

}

@Component({
    selector: 'jhi-market-delete-popup',
    template: ''
})
export class MarketDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketPopupService: MarketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.marketPopupService
                .open(MarketDeletePopupDialogComponent, 'modal_small',  params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
