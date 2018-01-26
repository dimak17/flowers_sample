import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BlockComponent } from './block.component';
import { BlockDetailComponent } from './block-detail.component';
import { BlockPopupComponent } from './block-dialog.component';
import { BlockDeletePopupComponent } from './block-delete-dialog.component';

import { Principal } from '../../shared';

export const blockRoute: Routes = [
    {
        path: 'block',
        component: BlockComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.block.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'block/:id',
        component: BlockDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.block.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const blockPopupRoute: Routes = [
    {
        path: 'block-new',
        component: BlockPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.block.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'block/:id/edit',
        component: BlockPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.block.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'block/:id/delete',
        component: BlockDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.block.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
