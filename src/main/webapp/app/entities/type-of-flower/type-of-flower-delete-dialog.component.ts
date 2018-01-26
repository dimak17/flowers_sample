import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { TypeOfFlower } from './type-of-flower.model';
import { TypeOfFlowerPopupService } from './type-of-flower-popup.service';
import { TypeOfFlowerService } from './type-of-flower.service';

@Component({
    selector: 'jhi-type-of-flower-delete-dialog',
    templateUrl: './type-of-flower-delete-dialog.component.html'
})
export class TypeOfFlowerDeleteDialogComponent {

    typeOfFlower: TypeOfFlower;

    constructor(
        private typeOfFlowerService: TypeOfFlowerService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeOfFlowerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeOfFlowerListModification',
                content: 'Deleted an typeOfFlower'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.typeOfFlower.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-type-of-flower-delete-popup',
    template: ''
})
export class TypeOfFlowerDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeOfFlowerPopupService: TypeOfFlowerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.typeOfFlowerPopupService
                .open(TypeOfFlowerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
