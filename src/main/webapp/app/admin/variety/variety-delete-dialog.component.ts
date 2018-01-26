import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';
import { VarietyPopupService } from './variety-popup.service';
import { VarietyService } from './variety.service';
import {Variety} from '../../entities/variety/variety.model';
import {Response} from '@angular/http';

@Component({
    selector: 'jhi-variety-delete-dialog',
    templateUrl: './variety-delete-dialog.component.html',
    styleUrls: ['./variety-delete-dialog.component.scss']
})
export class VarietyDeleteDialogComponent {

    variety: Variety;
    usageBlocksError = false;
    blocksUsageNames: string;

    constructor(
        private varietyService: VarietyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {

        this.varietyService.delete(id).subscribe(

            (response: Response) => {
                    this.eventManager.broadcast({
                        name: 'varietyListModification',
                        content: 'Deleted an variety'
                    });
                    this.activeModal.dismiss(true);
                },
            (error) => {
                if (error.headers.get('x-flowersapp-error') === 'error.Blocks') {
                    this.blocksUsageNames = error.headers.get('defaultMessage').substring(1, error.headers.get('defaultMessage').length - 1);
                    this.usageBlocksError = true;
                }
            });
        this.alertService.success('flowersApp.variety.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-variety-delete-popup',
    template: ''
})
export class VarietyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private varietyPopupService: VarietyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.varietyPopupService
                .open(VarietyDeleteDialogComponent, 'delete-variety', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
