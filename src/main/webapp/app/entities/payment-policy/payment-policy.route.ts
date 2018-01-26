import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PaymentPolicyComponent } from './payment-policy.component';
import { PaymentPolicyDetailComponent } from './payment-policy-detail.component';
import { PaymentPolicyPopupComponent } from './payment-policy-dialog.component';
import { PaymentPolicyDeletePopupComponent } from './payment-policy-delete-dialog.component';

import { Principal } from '../../shared';

export const paymentPolicyRoute: Routes = [
    {
        path: 'payment-policy',
        component: PaymentPolicyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.paymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'payment-policy/:id',
        component: PaymentPolicyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.paymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentPolicyPopupRoute: Routes = [
    {
        path: 'payment-policy-new',
        component: PaymentPolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.paymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payment-policy/:id/edit',
        component: PaymentPolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.paymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'payment-policy/:id/delete',
        component: PaymentPolicyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.paymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
