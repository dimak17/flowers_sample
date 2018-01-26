import {Routes} from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { ClaimsPolicyComponent } from './claims-policy.component';
import { ClaimsPolicyPopupComponent } from './claims-policy-dialog.component';
import { ClaimsPolicyDeletePopupComponent } from './claims-policy-delete-dialog.component';

export const claimsPolicyRoute: Routes = [
    {
        path: 'claims-policy',
        component: ClaimsPolicyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.claimsPolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];

export const claimsPolicyPopupRoute: Routes = [
    {
        path: 'claims-policy-new',
        component: ClaimsPolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.claimsPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'claims-policy/:id/edit',
        component: ClaimsPolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.claimsPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'claims-policy/:id/delete',
        component: ClaimsPolicyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.claimsPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
