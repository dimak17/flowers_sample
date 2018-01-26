import {Component, OnInit, OnDestroy} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';
import { SeasonPopupService } from './season-popup.service';
import { SeasonService } from './season.service';
import {Season} from '../../entities/season/season.model';

@Component({
    selector: 'jhi-season-delete-dialog',
    templateUrl: './season-delete-dialog.component.html',
    styleUrls: ['./season-delete-dialog.component.scss']
})
export class SeasonDeleteDialogComponent {

    season: Season;

    constructor(
        private seasonService: SeasonService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seasonService.delete(id).subscribe(() => {
            this.eventManager.broadcast({
                name: 'seasonListModification',
                content: 'Deleted an season'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.season.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-season-delete-popup',
    template: ''
})
export class SeasonDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonPopupService: SeasonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.seasonPopupService
                .open(SeasonDeleteDialogComponent, 'delete-season', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
