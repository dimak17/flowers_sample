import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MarketBoxComponent } from './market-box.component';
import { MarketBoxDetailComponent } from './market-box-detail.component';
import { MarketBoxPopupComponent } from './market-box-dialog.component';
import { MarketBoxDeletePopupComponent } from './market-box-delete-dialog.component';

import { Principal } from '../../shared';

export const marketBoxRoute: Routes = [
    {
        path: 'market-box',
        component: MarketBoxComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketBox.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-box/:id',
        component: MarketBoxDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketBox.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketBoxPopupRoute: Routes = [
    {
        path: 'market-box-new',
        component: MarketBoxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketBox.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-box/:id/edit',
        component: MarketBoxPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketBox.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-box/:id/delete',
        component: MarketBoxDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketBox.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
