import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager} from 'ng-jhipster';

import {MixType} from './mix-type.model';
import {MixTypePopupService} from './mix-type-popup.service';
import {MixTypeService} from './mix-type.service';

@Component({
    selector: 'jhi-flowers-mix-type-delete-dialog',
    templateUrl: './mix-type-delete-dialog.component.html'
})
export class MixTypeDeleteDialogComponent {

    mixType: MixType;

    constructor(
        private mixTypeService: MixTypeService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mixTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mixTypeListModification',
                content: 'Deleted an mixType'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.mixType.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-flowers-mix-type-delete-popup',
    template: ''
})
export class MixTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mixTypePopupService: MixTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.mixTypePopupService
                .open(MixTypeDeleteDialogComponent, 'modal_small', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
