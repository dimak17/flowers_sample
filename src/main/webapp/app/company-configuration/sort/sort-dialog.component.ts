import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager, JhiLanguageService} from 'ng-jhipster';
import {Sort} from './sort.model';
import {SortPopupService} from './sort-popup.service';
import {SortService} from './sort.service';
import {Block} from '../block/block.model';
import {BlockService} from '../block/block.service';

@Component({
    selector: 'jhi-sort-dialog',
    templateUrl: './sort-dialog.component.html'
})
export class SortDialogComponent implements OnInit {

    sort: Sort;
    authorities: any[];
    isSaving: boolean;

    blocks: Block[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private sortService: SortService,
        private blockService: BlockService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['sort']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.blockService.query().subscribe(
            (res: Response) => { this.blocks = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sort.id !== undefined) {
            this.sortService.update(this.sort)
                .subscribe((res: Sort) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.sortService.create(this.sort)
                .subscribe((res: Sort) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Sort) {
        this.eventManager.broadcast({ name: 'sortListModification', content: 'OK'});
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

    trackBlockById(index: number, item: Block) {
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
    selector: 'jhi-sort-popup',
    template: ''
})
export class SortPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sortPopupService: SortPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.sortPopupService
                    .open(SortDialogComponent, params['id']);
            } else {
                this.modalRef = this.sortPopupService
                    .open(SortDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
