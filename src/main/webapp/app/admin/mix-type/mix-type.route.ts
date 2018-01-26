import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {MixTypeComponent} from './mix-type.component';
import {MixTypePopupComponent} from './mix-type-dialog.component';
import {MixTypeDeletePopupComponent} from './mix-type-delete-dialog.component';

export const mixTypeRoute: Routes = [
    {
        path: 'mix-type',
        component: MixTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.mixType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];

export const mixTypePopupRoute: Routes = [
    {
        path: 'mix-type-new',
        component: MixTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.mixType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mix-type/:id/edit',
        component: MixTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.mixType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mix-type/:id/delete',
        component: MixTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.mixType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
