import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {JhiLanguageService} from 'ng-jhipster';
import {Employee} from './employee.model';
import {EmployeeService} from './employee.service';

@Component({
    selector: 'jhi-employee-detail',
    templateUrl: './employee-detail.component.html'
})
export class EmployeeDetailComponent implements OnInit, OnDestroy {

    employee: Employee;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private employeeService: EmployeeService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['employee']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.employeeService.find(id).subscribe((employee) => {
            this.employee = employee;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
