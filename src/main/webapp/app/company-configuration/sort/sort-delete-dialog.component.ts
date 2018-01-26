import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {Sort} from './sort.model';
import {SortPopupService} from './sort-popup.service';
import {SortService} from './sort.service';

@Component({
    selector: 'jhi-sort-delete-dialog',
    templateUrl: './sort-delete-dialog.component.html'
})
export class SortDeleteDialogComponent {

    sort: Sort;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private sortService: SortService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['sort']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sortService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sortListModification',
                content: 'Deleted an sort'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sort-delete-popup',
    template: ''
})
export class SortDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sortPopupService: SortPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.sortPopupService
                .open(SortDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
