import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LabelCountryComponent } from './label-country.component';
import { LabelCountryDetailComponent } from './label-country-detail.component';
import { LabelCountryPopupComponent } from './label-country-dialog.component';
import { LabelCountryDeletePopupComponent } from './label-country-delete-dialog.component';

import { Principal } from '../../shared';

export const labelCountryRoute: Routes = [
    {
        path: 'label-country',
        component: LabelCountryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.labelCountry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'label-country/:id',
        component: LabelCountryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.labelCountry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const labelCountryPopupRoute: Routes = [
    {
        path: 'label-country-new',
        component: LabelCountryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.labelCountry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'label-country/:id/edit',
        component: LabelCountryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.labelCountry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'label-country/:id/delete',
        component: LabelCountryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.labelCountry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
