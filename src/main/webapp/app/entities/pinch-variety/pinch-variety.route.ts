import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PinchVarietyComponent } from './pinch-variety.component';
import { PinchVarietyDetailComponent } from './pinch-variety-detail.component';
import { PinchVarietyPopupComponent } from './pinch-variety-dialog.component';
import { PinchVarietyDeletePopupComponent } from './pinch-variety-delete-dialog.component';

import { Principal } from '../../shared';

export const pinchVarietyRoute: Routes = [
    {
        path: 'pinch-variety',
        component: PinchVarietyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinchVariety.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pinch-variety/:id',
        component: PinchVarietyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinchVariety.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pinchVarietyPopupRoute: Routes = [
    {
        path: 'pinch-variety-new',
        component: PinchVarietyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinchVariety.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pinch-variety/:id/edit',
        component: PinchVarietyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinchVariety.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pinch-variety/:id/delete',
        component: PinchVarietyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinchVariety.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
