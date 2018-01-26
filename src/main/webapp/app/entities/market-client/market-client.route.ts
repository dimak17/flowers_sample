import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MarketClientComponent } from './market-client.component';
import { MarketClientDetailComponent } from './market-client-detail.component';
import { MarketClientPopupComponent } from './market-client-dialog.component';
import { MarketClientDeletePopupComponent } from './market-client-delete-dialog.component';

import { Principal } from '../../shared';

export const marketClientRoute: Routes = [
    {
        path: 'market-client',
        component: MarketClientComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketClient.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-client/:id',
        component: MarketClientDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketClient.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketClientPopupRoute: Routes = [
    {
        path: 'market-client-new',
        component: MarketClientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketClient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-client/:id/edit',
        component: MarketClientPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketClient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-client/:id/delete',
        component: MarketClientDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketClient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
