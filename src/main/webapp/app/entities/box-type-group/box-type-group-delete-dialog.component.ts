import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { BoxTypeGroup } from './box-type-group.model';
import { BoxTypeGroupPopupService } from './box-type-group-popup.service';
import { BoxTypeGroupService } from './box-type-group.service';

@Component({
    selector: 'jhi-box-type-group-delete-dialog',
    templateUrl: './box-type-group-delete-dialog.component.html'
})
export class BoxTypeGroupDeleteDialogComponent {

    boxTypeGroup: BoxTypeGroup;

    constructor(
        private boxTypeGroupService: BoxTypeGroupService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.boxTypeGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'boxTypeGroupListModification',
                content: 'Deleted an boxTypeGroup'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.boxTypeGroup.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-box-type-group-delete-popup',
    template: ''
})
export class BoxTypeGroupDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private boxTypeGroupPopupService: BoxTypeGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.boxTypeGroupPopupService
                .open(BoxTypeGroupDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
