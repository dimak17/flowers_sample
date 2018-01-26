import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BoxTypeGroupComponent } from './box-type-group.component';
import { BoxTypeGroupDetailComponent } from './box-type-group-detail.component';
import { BoxTypeGroupPopupComponent } from './box-type-group-dialog.component';
import { BoxTypeGroupDeletePopupComponent } from './box-type-group-delete-dialog.component';

import { Principal } from '../../shared';

export const boxTypeGroupRoute: Routes = [
    {
        path: 'box-type-group',
        component: BoxTypeGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxTypeGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'box-type-group/:id',
        component: BoxTypeGroupDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxTypeGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const boxTypeGroupPopupRoute: Routes = [
    {
        path: 'box-type-group-new',
        component: BoxTypeGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxTypeGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'box-type-group/:id/edit',
        component: BoxTypeGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxTypeGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'box-type-group/:id/delete',
        component: BoxTypeGroupDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxTypeGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
