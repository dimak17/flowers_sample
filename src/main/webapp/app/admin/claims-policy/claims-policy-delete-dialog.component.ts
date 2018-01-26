import { Component, OnInit, OnDestroy, ViewChild, TemplateRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { ClaimsPolicyUi } from './claimsui-policy.model';
import { ClaimsPolicyPopupService } from './claims-policy-popup.service';
import { ClaimsPolicyService } from './claims-policy.service';

@Component({
    selector: 'jhi-claims-policy-delete-dialog',
    templateUrl: './claims-policy-delete-dialog.component.html',
    styleUrls: ['./claims-policy-delete-dialog.component.scss']

})
export class ClaimsPolicyDeleteDialogComponent {

    claimsPolicy: ClaimsPolicyUi;

    constructor(
        private claimsPolicyService: ClaimsPolicyService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.claimsPolicyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'claimsPolicyListModification',
                content: 'Deleted an claimsPolicy'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('flowersApp.claimsPolicy.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-claims-policy-delete-popup',
    template: ''
})
export class ClaimsPolicyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private claimsPolicyPopupService: ClaimsPolicyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.claimsPolicyPopupService
                .open(ClaimsPolicyDeleteDialogComponent, 'delete-claimsPolicy', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
