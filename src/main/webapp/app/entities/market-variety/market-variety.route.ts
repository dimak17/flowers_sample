import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MarketVarietyComponent } from './market-variety.component';
import { MarketVarietyDetailComponent } from './market-variety-detail.component';
import { MarketVarietyPopupComponent } from './market-variety-dialog.component';
import { MarketVarietyDeletePopupComponent } from './market-variety-delete-dialog.component';

import { Principal } from '../../shared';

export const marketVarietyRoute: Routes = [
    {
        path: 'market-variety',
        component: MarketVarietyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketVariety.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-variety/:id',
        component: MarketVarietyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketVariety.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketVarietyPopupRoute: Routes = [
    {
        path: 'market-variety-new',
        component: MarketVarietyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketVariety.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-variety/:id/edit',
        component: MarketVarietyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketVariety.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-variety/:id/delete',
        component: MarketVarietyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketVariety.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
