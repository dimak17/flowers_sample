import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager, JhiLanguageService} from 'ng-jhipster';
import {EmployeePopupService} from './employee-popup.service';
import {Employee} from './employee.model';
import {EmployeeService} from './employee.service';
import {User} from '../../shared/user/user.model';
import {Company} from '../../entities/company/company.model';
import {JhiLanguageHelper} from '../../shared/index';
@Component({
    selector: 'jhi-employee-dialog',
    templateUrl: './employee-dialog.component.html'
})
export class EmployeeDialogComponent implements OnInit {

    employee: Employee;
    positions: any[];
    selectedPositionsIds: number[];
    selectedPositions: any[];
    isSaving: boolean;
    users: User[];

    companies: Company[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private employeeService: EmployeeService,
        private eventManager: EventManager,
        private languageHelper: JhiLanguageHelper,
    ) {
        this.jhiLanguageService.setLocations(['employee']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.employeeService.queryPostions().subscribe(
            (res: Response) => {
                this.positions = res.json();
                // preselection from server
                this.selectedPositionsIds = this.employee.positions.map((pos) => pos.id);
            },
            (res: Response) => this.onError(res.json()));
    }

    onChange() {
        console.log('selected positions: ' + this.selectedPositionsIds);
        this.selectedPositions = this.positions
            .filter((p) => this.selectedPositionsIds.some((opt) => opt === p.id));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.employee.positions = this.selectedPositions;
        if (this.employee.id !== undefined) {
            this.employeeService.update(this.employee)
                .subscribe((res: Employee) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.employeeService.create(this.employee)
                .subscribe((res: Employee) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess(result: Employee) {
        this.eventManager.broadcast({ name: 'employeeListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackPositionById(index: number, item: any) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-employee-popup',
    template: ''
})
export class EmployeePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeePopupService: EmployeePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.employeePopupService
                    .open(EmployeeDialogComponent, params['id']);
            } else {
                this.modalRef = this.employeePopupService
                    .open(EmployeeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
