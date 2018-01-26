import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TypeOfFlowerComponent } from './type-of-flower.component';
import { TypeOfFlowerDetailComponent } from './type-of-flower-detail.component';
import { TypeOfFlowerPopupComponent } from './type-of-flower-dialog.component';
import { TypeOfFlowerDeletePopupComponent } from './type-of-flower-delete-dialog.component';

import { Principal } from '../../shared';

export const typeOfFlowerRoute: Routes = [
    {
        path: 'type-of-flower',
        component: TypeOfFlowerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.typeOfFlower.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-of-flower/:id',
        component: TypeOfFlowerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.typeOfFlower.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeOfFlowerPopupRoute: Routes = [
    {
        path: 'type-of-flower-new',
        component: TypeOfFlowerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.typeOfFlower.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-of-flower/:id/edit',
        component: TypeOfFlowerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.typeOfFlower.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-of-flower/:id/delete',
        component: TypeOfFlowerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.typeOfFlower.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
