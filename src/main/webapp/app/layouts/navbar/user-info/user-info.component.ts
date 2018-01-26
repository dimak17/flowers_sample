import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Principal} from '../../../shared/auth/principal.service';
import {CompanyUser} from '../../../entities/company-user/company-user.model';
import {UserInfoService} from './user-info.service';
import {EventManager} from 'ng-jhipster';
import {Subscription} from 'rxjs/Subscription';

@Component({
    selector: 'jhi-flowers-user-info',
    templateUrl: './user-info.component.html',
    providers: [
        UserInfoService
    ]
})
export class UserInfoComponent implements OnInit, OnDestroy {

    @Input() companyUser: CompanyUser;
    companyUserListModificationEvent: Subscription;
    comapnyUserFullName: string;
    companyFarmNameEvent: Subscription;
    companyUserNameEvent: Subscription;

    constructor(
        private principal: Principal,
        private userInfoService: UserInfoService,
        private eventManager: EventManager
    ) {}

    ngOnInit(): void {
        this.principal.identity().then((account) => {
            // if (account && account.login) {
            //     this.userInfoService.get(account.login).subscribe((params) => {
                    this.companyUser = account;
            //     });
            // }
        });
        this.changeCompanyName();
        this.registerEvents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.companyUserNameEvent);
        this.eventManager.destroy(this.companyFarmNameEvent);
    }

    registerEvents() {
        this.companyUserNameEvent = this.eventManager.subscribe('changedFullName', (response) => this.companyUser.fullName = response.content.fullName);
    }

    changeCompanyName() {
        this.companyFarmNameEvent = this.eventManager.subscribe('CompanyNameModification', (response) => this.companyUser.company.farmName = response.farmName);
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
}
