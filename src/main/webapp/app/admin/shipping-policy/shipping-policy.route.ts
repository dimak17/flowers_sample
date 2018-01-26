import {Routes} from '@angular/router';
import {UserRouteAccessService} from '../../shared';
import {ShippingPolicyComponent} from './shipping-policy.component';
import {ShippingPolicyPopupComponent} from './shipping-policy-dialog.component';
import {ShippingPolicyDeletePopupComponent} from './shipping-policy-delete-dialog.component';

export const shippingPolicyRoute: Routes = [
    {
        path: 'shipping-policy',
        component: ShippingPolicyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.shippingPolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];

export const shippingPolicyPopupRoute: Routes = [
    {
        path: 'shipping-policy-new',
        component: ShippingPolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.shippingPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shipping-policy/:id/edit',
        component: ShippingPolicyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.shippingPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shipping-policy/:id/delete',
        component: ShippingPolicyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.shippingPolicy.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
