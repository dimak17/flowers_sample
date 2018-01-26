import { UserRouteAccessService } from '../../shared';
import { EmployeeListComponent } from './employee-list.component';
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { PaginationUtil } from 'ng-jhipster';
import {EmployeeListPopupComponent} from './employee-list-dialog.component';

@Injectable()
export class EmployeeListResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const employeeListRoute: Routes = [
    {
        path: 'employee-user',
        component: EmployeeListComponent,
        resolve: {
            'pagingParams': EmployeeListResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.companyUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeListPopupRoute: Routes = [
    {
        path: 'employee-user-new',
        component: EmployeeListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.companyUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-user/:id/edit',
        component: EmployeeListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.companyUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
