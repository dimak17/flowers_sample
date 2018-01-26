import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { BoxGroup } from './box-group.model';
import { BoxGroupPopupService } from './box-group-popup.service';
import { BoxGroupService } from './box-group.service';

@Component({
    selector: 'jhi-box-group-delete-dialog',
    templateUrl: './box-group-delete-dialog.component.html'
})
export class BoxGroupDeleteDialogComponent {

    boxGroup: BoxGroup;

    constructor(
        private boxGroupService: BoxGroupService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.boxGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'boxGroupListModification',
                content: 'Deleted an boxGroup'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.boxGroup.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-box-group-delete-popup',
    template: ''
})
export class BoxGroupDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private boxGroupPopupService: BoxGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.boxGroupPopupService
                .open(BoxGroupDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
