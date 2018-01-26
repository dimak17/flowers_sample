import {Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { PinchComponent } from './pinch.component';
import { PinchPopupComponent } from './pinch-dialog.component';
import { PinchDeletePopupComponent } from './pinch-delete-dialog.component';

export const pinchRoute: Routes = [
    {
        path: 'pinch',
        component: PinchComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinch.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pinchPopupRoute: Routes = [
    {
        path: 'pinch-new',
        component: PinchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pinch/:id/edit',
        component: PinchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pinch/:id/delete',
        component: PinchDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.pinch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
