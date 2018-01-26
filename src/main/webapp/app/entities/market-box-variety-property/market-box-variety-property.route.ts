import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MarketBoxVarietyPropertyComponent } from './market-box-variety-property.component';
import { MarketBoxVarietyPropertyDetailComponent } from './market-box-variety-property-detail.component';
import { MarketBoxVarietyPropertyPopupComponent } from './market-box-variety-property-dialog.component';
import { MarketBoxVarietyPropertyDeletePopupComponent } from './market-box-variety-property-delete-dialog.component';

import { Principal } from '../../shared';

export const marketBoxVarietyPropertyRoute: Routes = [
    {
        path: 'market-box-variety-property',
        component: MarketBoxVarietyPropertyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketBoxVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-box-variety-property/:id',
        component: MarketBoxVarietyPropertyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketBoxVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketBoxVarietyPropertyPopupRoute: Routes = [
    {
        path: 'market-box-variety-property-new',
        component: MarketBoxVarietyPropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketBoxVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-box-variety-property/:id/edit',
        component: MarketBoxVarietyPropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketBoxVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-box-variety-property/:id/delete',
        component: MarketBoxVarietyPropertyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketBoxVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
