import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { CompanyUser } from './company-user.model';
import { CompanyUserService } from './company-user.service';

@Component({
    selector: 'jhi-company-user-detail',
    templateUrl: './company-user-detail.component.html'
})
export class CompanyUserDetailComponent implements OnInit, OnDestroy {

    companyUser: CompanyUser;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private companyUserService: CompanyUserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyUsers();
    }

    load(id) {
        this.companyUserService.find(id).subscribe((companyUser) => {
            this.companyUser = companyUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyUserListModification',
            (response) => this.load(this.companyUser.id)
        );
    }
}
