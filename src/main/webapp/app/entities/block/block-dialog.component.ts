import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Block } from './block.model';
import { BlockPopupService } from './block-popup.service';
import { BlockService } from './block.service';
import { Company, CompanyService } from '../company';
import { ResponseWrapper } from '../../shared';
import {Variety} from '../variety/variety.model';
import {VarietyService} from '../../admin/variety/variety.service';

@Component({
    selector: 'jhi-block-dialog',
    templateUrl: './block-dialog.component.html'
})
export class BlockDialogComponent implements OnInit {

    block: Block;
    authorities: any[];
    isSaving: boolean;

    varieties: Variety[];

    companies: Company[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private blockService: BlockService,
        private varietyService: VarietyService,
        private companyService: CompanyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.varietyService.query()
            .subscribe((res: ResponseWrapper) => { this.varieties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.block.id !== undefined) {
            this.subscribeToSaveResponse(
                this.blockService.update(this.block), false);
        } else {
            this.subscribeToSaveResponse(
                this.blockService.create(this.block), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Block>, isCreated: boolean) {
        result.subscribe((res: Block) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Block, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.block.created'
            : 'flowersApp.block.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'blockListModification', content: 'OK'});
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

    trackVarietyById(index: number, item: Variety) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-block-popup',
    template: ''
})
export class BlockPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private blockPopupService: BlockPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.blockPopupService
                    .open(BlockDialogComponent, params['id']);
            } else {
                this.modalRef = this.blockPopupService
                    .open(BlockDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
