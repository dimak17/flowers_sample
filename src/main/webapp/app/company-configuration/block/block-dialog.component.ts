import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager, JhiLanguageService} from 'ng-jhipster';
import {Block} from './block.model';
import {BlockPopupService} from './block-popup.service';
import {BlockService} from './block.service';
import {Sort} from '../sort/sort.model';
import {SortService} from '../sort/sort.service';

@Component({
    selector: 'jhi-block-dialog',
    templateUrl: './block-dialog.component.html'
})
export class BlockDialogComponent implements OnInit {

    block: Block;
    authorities: any[];
    isSaving: boolean;

    sorts: Sort[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private blockService: BlockService,
        private sortService: SortService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['block']);
    }

    ngOnInit() {
        console.log('---Block!!!!');
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.sortService.query().subscribe(
            (res: Response) => { this.sorts = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.block.id !== undefined) {
            this.blockService.update(this.block)
                .subscribe((res: Block) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.blockService.create(this.block)
                .subscribe((res: Block) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Block) {
        this.eventManager.broadcast({ name: 'blockListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackSortById(index: number, item: Sort) {
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
