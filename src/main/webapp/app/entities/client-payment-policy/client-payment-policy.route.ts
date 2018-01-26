import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClientPaymentPolicyComponent } from './client-payment-policy.component';
import { ClientPaymentPolicyDetailComponent } from './client-payment-policy-detail.component';
import { ClientPaymentPolicyPopupComponent } from './client-payment-policy-dialog.component';
import { ClientPaymentPolicyDeletePopupComponent } from './client-payment-policy-delete-dialog.component';

import { Principal } from '../../shared';

export const clientPaymentPolicyRoute: Routes = [
    {
        path: 'client-payment-policy',
        component: ClientPaymentPolicyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientPaymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'client-payment-policy/:id',
        component: ClientPaymentPolicyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientPaymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientPaymentPolicyPopupRoute: Routes = [
    {
        path: 'client-payment-policy-new',
        component: ClientPaymentPolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientPaymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-payment-policy/:id/edit',
        component: ClientPaymentPolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientPaymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-payment-policy/:id/delete',
        component: ClientPaymentPolicyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientPaymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
