import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClientEmployeeComponent } from './client-employee.component';
import { ClientEmployeePopupComponent } from './client-employee-dialog.component';
import { ClientEmployeeDeletePopupComponent } from './client-employee-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ClientEmployeeResolvePagingParams implements Resolve<any> {

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

export const clientEmployeeRoute: Routes = [
    {
        path: 'client-employee',
        component: ClientEmployeeComponent,
        resolve: {
            'pagingParams': ClientEmployeeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientEmployee.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientEmployeePopupRoute: Routes = [
    {
        path: 'client-employee-new',
        component: ClientEmployeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientEmployee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-employee/:id/edit',
        component: ClientEmployeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientEmployee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-employee/:id/delete',
        component: ClientEmployeeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientEmployee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
