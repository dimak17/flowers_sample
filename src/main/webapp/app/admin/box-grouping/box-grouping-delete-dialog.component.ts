import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {BoxGroupDTO} from './box-group-dto.model';
import {BoxGroupingService} from './box-grouping.service';
import {BoxGroupingPopupService} from './box-grouping-popup.service';

@Component({
    selector: 'jhi-box-grouping-delete-dialog',
    templateUrl: './box-grouping-delete-dialog.component.html',
    styleUrls: ['./box-grouping-delete-dialog.scss'],
    providers: [BoxGroupingService]
})
export class BoxGroupingDeleteDialogComponent {

    boxGroup: BoxGroupDTO;
    groupIndex: number;
    groupName: string;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private boxGroupingService: BoxGroupingService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['box-grouping']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        if (id) {
            this.boxGroupingService.delete(id).subscribe((response) => {
                this.afterRemoveHook();
            });
        } else {
            this.afterRemoveHook();
        }
    }

    private afterRemoveHook() {
        this.eventManager.broadcast({
            name: 'boxGroupingRemoveControls',
            groupIndex: this.groupIndex
        });
        this.activeModal.dismiss(true);
    }

    getDeleteMsg() {
        return this.groupName
            ? decodeURIComponent(this.groupName)
            : '';
    }
}

@Component({
    selector: 'jhi-box-grouping-delete-popup',
    template: ''
})
export class BoxGroupingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private boxGroupPopupService: BoxGroupingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.boxGroupPopupService
                .open(BoxGroupingDeleteDialogComponent, params['id'], params['groupIndex'], params['groupName'], 'delete-box-type');
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
