import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CompanyUserComponent } from './company-user.component';
import { CompanyUserDetailComponent } from './company-user-detail.component';
import { CompanyUserPopupComponent } from './company-user-dialog.component';
import { CompanyUserDeletePopupComponent } from './company-user-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CompanyUserResolvePagingParams implements Resolve<any> {

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

export const companyUserRoute: Routes = [
    {
        path: 'company-user',
        component: CompanyUserComponent,
        resolve: {
            'pagingParams': CompanyUserResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.companyUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-user/:id',
        component: CompanyUserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.companyUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyUserPopupRoute: Routes = [
    {
        path: 'company-user-new',
        component: CompanyUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.companyUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-user/:id/edit',
        component: CompanyUserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.companyUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-user/:id/delete',
        component: CompanyUserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.companyUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
