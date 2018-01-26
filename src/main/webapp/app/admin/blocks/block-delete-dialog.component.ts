import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { BlockPopupService } from './block-popup.service';
import { BlockService } from './block.service';
import { Block } from '../../entities/block/block.model';

@Component({
    selector: 'jhi-block-delete-dialog',
    templateUrl: './block-delete-dialog.component.html',
    styleUrls: ['block-delete-dialog.component.scss']
})
export class BlockDeleteDialogComponent {

    block: Block;

    constructor(
        private blockService: BlockService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.blockService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'blockListModification',
                content: 'Deleted an block'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.block.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-block-delete-popup',
    template: ''
})
export class BlockDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private blockPopupService: BlockPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.blockPopupService
                .open(BlockDeleteDialogComponent, 'delete-block', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
