/**
 * Created by dima on 27.06.17.
 */
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';
import {Company} from '../../entities/company/company.model';
import {FarmDataService} from './farm-data.service';
import {FarmDataPopupService} from './farm-data-popup.service';
import {Subscription} from 'rxjs/Subscription';

@Component({
    selector: 'jhi-company-delete-image',
    templateUrl: './logo-delete-image.component.html'
})
export class LogoDeleteImageComponent implements OnDestroy {

    company: Company;
    private deleteImage: Subscription;

    constructor(
        private farmDataService: FarmDataService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmImageDelete() {
        this.deleteImage = this.farmDataService.deleteImage().subscribe(() => {
            this.eventManager.broadcast({
                name: 'companyImageDeleted',
                content: 'Deleted an company'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.company.deleted', null, null);
    }

    ngOnDestroy(): void {
        this.deleteImage.unsubscribe();
    }
}

@Component({
    selector: 'jhi-company-delete-image',
    template: ''
})
export class LogoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private farmDataPopupService: FarmDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(() => {
            this.modalRef = this.farmDataPopupService
                .open(LogoDeleteImageComponent, 'modal_medium');
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
