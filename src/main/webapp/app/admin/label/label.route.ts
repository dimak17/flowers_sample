import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LabelComponent } from './label.component';
import { LabelPopupComponent } from './label-dialog.component';
import { LabelDeletePopupComponent } from './label-delete-dialog.component';

@Injectable()
export class LabelResolvePagingParams implements Resolve<any> {

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

export const labelRoute: Routes = [
    {
        path: 'label',
        component: LabelComponent,
        resolve: {
            'pagingParams': LabelResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.label.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const labelPopupRoute: Routes = [
    {
        path: 'label-new',
        component: LabelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.label.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'label/:id/edit',
        component: LabelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.label.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'label/:id/delete',
        component: LabelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.label.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
