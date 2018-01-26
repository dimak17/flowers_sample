import {Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';

import { BoxTypeComponent } from './box-type.component';
import { BoxTypePopupComponent } from './box-type-dialog.component';
import { BoxTypeDeletePopupComponent } from './box-type-delete-dialog.component';

export const boxTypeRoute: Routes = [
    {
        path: 'box-type',
        component: BoxTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.box-type.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const boxTypePopupRoute: Routes = [
    {
        path: 'box-type-new',
        component: BoxTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.box-type.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'box-type/:id/edit',
        component: BoxTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'box-type/:id/delete',
        component: BoxTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.box-type.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
