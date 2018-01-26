import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AirLinesComponent } from './air-lines.component';
import { AirLinesPopupComponent } from './air-lines-dialog.component';
import { AirLinesDeletePopupComponent } from './air-lines-delete-dialog.component';

import { Principal } from '../../shared';

export const airLinesRoute: Routes = [
    {
        path: 'air-lines',
        component: AirLinesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.airLines.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const airLinesPopupRoute: Routes = [
    {
        path: 'air-lines-new',
        component: AirLinesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.airLines.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'air-lines/:id/edit',
        component: AirLinesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.airLines.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'air-lines/:id/delete',
        component: AirLinesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.airLines.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
