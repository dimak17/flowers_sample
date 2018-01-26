/**
 * Created by dima on 27.06.17.
 */
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';
import { VarietyPopupService } from './variety-popup.service';
import { VarietyService } from './variety.service';
import {Variety} from '../../entities/variety/variety.model';

@Component({
    selector: 'jhi-variety-delete-image',
    templateUrl: './variety-delete-image.component.html',
    styleUrls: ['./variety-delete-dialog.component.scss']
})
export class VarietyDeleteImageComponent {

    variety: Variety;

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

    confirmImageDelete(id: number) {
        if (id) {
            this.varietyService.deleteImage(id).subscribe((response) => {
                this.eventManager.broadcast({
                    name: 'varietyImageDeleted',
                    content: 'Deleted an variety'
                });
                this.activeModal.dismiss(true);
            });
            this.alertService.success('flowersApp.variety.deleted', {param: id}, null);
        } else {
            this.eventManager.broadcast({
                name: 'newVarietyImageDeleted',
                content: 'Deleted an variety'
            });
            this.activeModal.dismiss(true);
        }
    }
}

@Component({
    selector: 'jhi-variety-delete-popup',
    template: ''
})
export class ImageDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private varietyPopupService: VarietyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.varietyPopupService
                .open(VarietyDeleteImageComponent, 'delete-image', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
