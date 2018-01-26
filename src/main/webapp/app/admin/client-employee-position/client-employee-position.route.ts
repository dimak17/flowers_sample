import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClientEmployeePositionComponent } from './client-employee-position.component';
import { ClientEmployeePositionPopupComponent } from './client-employee-position-dialog.component';
import { ClientEmployeePositionDeletePopupComponent } from './client-employee-position-delete-dialog.component';

import { Principal } from '../../shared';

export const clientEmployeePositionRoute: Routes = [
    {
        path: 'client-employee-position',
        component: ClientEmployeePositionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientEmployeePosition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientEmployeePositionPopupRoute: Routes = [
    {
        path: 'client-employee-position-new',
        component: ClientEmployeePositionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientEmployeePosition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-employee-position/:id/edit',
        component: ClientEmployeePositionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientEmployeePosition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-employee-position/:id/delete',
        component: ClientEmployeePositionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.clientEmployeePosition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
