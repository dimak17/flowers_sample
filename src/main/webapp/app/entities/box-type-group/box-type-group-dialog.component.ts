import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { BoxTypeGroup } from './box-type-group.model';
import { BoxTypeGroupPopupService } from './box-type-group-popup.service';
import { BoxTypeGroupService } from './box-type-group.service';
import { BoxGroup, BoxGroupService } from '../box-group';
import { ResponseWrapper } from '../../shared';
import {BoxTypeUi} from '../../admin/box-type/box-type-ui.model';
import {BoxTypeService} from '../../admin/box-type/box-type.service';

@Component({
    selector: 'jhi-box-type-group-dialog',
    templateUrl: './box-type-group-dialog.component.html'
})
export class BoxTypeGroupDialogComponent implements OnInit {

    boxTypeGroup: BoxTypeGroup;
    authorities: any[];
    isSaving: boolean;

    boxgroups: BoxGroup[];

    boxtypes: BoxTypeUi[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private boxTypeGroupService: BoxTypeGroupService,
        private boxGroupService: BoxGroupService,
        private boxTypeService: BoxTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.boxGroupService.query()
            .subscribe((res: ResponseWrapper) => { this.boxgroups = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.boxTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.boxtypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.boxTypeGroup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.boxTypeGroupService.update(this.boxTypeGroup), false);
        } else {
            this.subscribeToSaveResponse(
                this.boxTypeGroupService.create(this.boxTypeGroup), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BoxTypeGroup>, isCreated: boolean) {
        result.subscribe((res: BoxTypeGroup) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BoxTypeGroup, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.boxTypeGroup.created'
            : 'flowersApp.boxTypeGroup.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'boxTypeGroupListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackBoxGroupById(index: number, item: BoxGroup) {
        return item.id;
    }

    trackBoxTypeById(index: number, item: BoxTypeUi) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-box-type-group-popup',
    template: ''
})
export class BoxTypeGroupPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private boxTypeGroupPopupService: BoxTypeGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.boxTypeGroupPopupService
                    .open(BoxTypeGroupDialogComponent, params['id']);
            } else {
                this.modalRef = this.boxTypeGroupPopupService
                    .open(BoxTypeGroupDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
