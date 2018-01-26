import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PinchVarietyPropertyComponent } from './pinch-variety-property.component';
import { PinchVarietyPropertyDetailComponent } from './pinch-variety-property-detail.component';
import { PinchVarietyPropertyPopupComponent } from './pinch-variety-property-dialog.component';
import { PinchVarietyPropertyDeletePopupComponent } from './pinch-variety-property-delete-dialog.component';

import { Principal } from '../../shared';

export const pinchVarietyPropertyRoute: Routes = [
    {
        path: 'pinch-variety-property',
        component: PinchVarietyPropertyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinchVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pinch-variety-property/:id',
        component: PinchVarietyPropertyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinchVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pinchVarietyPropertyPopupRoute: Routes = [
    {
        path: 'pinch-variety-property-new',
        component: PinchVarietyPropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinchVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pinch-variety-property/:id/edit',
        component: PinchVarietyPropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinchVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pinch-variety-property/:id/delete',
        component: PinchVarietyPropertyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinchVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
