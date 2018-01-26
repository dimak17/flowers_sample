import {Component, OnInit, OnDestroy, TemplateRef, ViewChild} from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { BoxTypePopupService } from './box-type-popup.service';
import { BoxTypeService } from './box-type.service';
import {BoxTypeUi} from './box-type-ui.model';
import {Response} from '@angular/http';

@Component({
    selector: 'jhi-box-type-delete-dialog',
    templateUrl: './box-type-delete-dialog.component.html'
})
export class BoxTypeDeleteDialogComponent {

    boxType: BoxTypeUi;
    usageGroupingsError = false;

    constructor(
        private boxTypeService: BoxTypeService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {

        this.boxTypeService.delete(id).subscribe(

            (response: Response) => {
                    this.eventManager.broadcast({
                        name: 'boxTypeListModification',
                        content: 'Deleted an boxType'
                    });
                    this.activeModal.dismiss(true);
                },
            (error) => {
                if (error.headers.get('x-flowersapp-error') === 'error.BoxTypeGrouping') {
                    this.usageGroupingsError = true;
                }
            });
        this.alertService.success('flowersApp.box-type.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-box-type-delete-popup',
    template: ''
})
export class BoxTypeDeletePopupComponent implements OnInit, OnDestroy {

    @ViewChild('modal-template') static modalTemplate: TemplateRef<any>;

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private boxTypePopupService: BoxTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.boxTypePopupService
                .open(BoxTypeDeleteDialogComponent, 'modal_medium',  params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
