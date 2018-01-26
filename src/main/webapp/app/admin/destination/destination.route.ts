import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DestinationComponent } from './destination.component';
import { DestinationPopupComponent } from './destination-dialog.component';
import { DestinationDeletePopupComponent } from './destination-delete-dialog.component';

import { Principal } from '../../shared';

export const destinationRoute: Routes = [
    {
        path: 'destination',
        component: DestinationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.destination.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const destinationPopupRoute: Routes = [
    {
        path: 'destination-new',
        component: DestinationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.destination.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'destination/:id/edit',
        component: DestinationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.destination.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'destination/:id/delete',
        component: DestinationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.destination.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
