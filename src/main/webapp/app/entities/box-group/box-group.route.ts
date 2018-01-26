import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BoxGroupComponent } from './box-group.component';
import { BoxGroupDetailComponent } from './box-group-detail.component';
import { BoxGroupPopupComponent } from './box-group-dialog.component';
import { BoxGroupDeletePopupComponent } from './box-group-delete-dialog.component';

import { Principal } from '../../shared';

export const boxGroupRoute: Routes = [
    {
        path: 'box-group',
        component: BoxGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'box-group/:id',
        component: BoxGroupDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const boxGroupPopupRoute: Routes = [
    {
        path: 'box-group-new',
        component: BoxGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'box-group/:id/edit',
        component: BoxGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'box-group/:id/delete',
        component: BoxGroupDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
