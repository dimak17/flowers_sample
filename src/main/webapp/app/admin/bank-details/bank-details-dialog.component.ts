import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {BankDetailsPopupService} from './bank-details-popup.service';
import {BankDetailsService} from './bank-details.service';
import {ResponseWrapper} from '../../shared';
import {Company} from '../../entities/company/company.model';
import {BankDetails} from '../../entities/bank-details/bank-details.model';
import {CompanyService} from '../../entities/company/company.service';
import {AlertService, EventManager} from 'ng-jhipster';
import {Variety} from '../../entities/variety/variety.model';

@Component({
    selector: 'jhi-bank-details-dialog',
    templateUrl: './bank-details-dialog.component.html',
    styleUrls: ['./bank-details-dialog.component.scss']

})
export class BankDetailsDialogComponent implements OnInit {

    bankDetails: BankDetails;
    authorities: any[];
    isSaving: boolean;
    file: File;
    variety: Variety;
    companies: Company[];

    constructor(public activeModal: NgbActiveModal,
                private alertService: AlertService,
                private bankDetailsService: BankDetailsService,
                private companyService: CompanyService,
                private eventManager: EventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.companyService
            .query({filter: 'bankdetails-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.bankDetails.company || !this.bankDetails.company.id) {
                    this.companies = res.json;
                } else {
                    this.companyService
                        .find(this.bankDetails.company.id)
                        .subscribe((subRes: Company) => {
                            this.companies = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bankDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bankDetailsService.update(this.bankDetails), false);
        } else {
            this.subscribeToSaveResponse(
                this.bankDetailsService.create(this.bankDetails), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<BankDetails>, isCreated: boolean) {
        result.subscribe((res: BankDetails) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BankDetails, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.bankDetails.created'
                : 'flowersApp.bankDetails.updated',
            {param: result.id}, null);
        this.eventManager.broadcast({name: 'bankDetailsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-bank-details-popup',
    template: ''
})
export class BankDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private bankDetailsPopupService: BankDetailsPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.modalRef = this.bankDetailsPopupService
                    .open(BankDetailsDialogComponent, 'bank-details-modal-window', params['id']);
            } else {
                this.modalRef = this.bankDetailsPopupService
                    .open(BankDetailsDialogComponent, 'bank-details-modal-window');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
