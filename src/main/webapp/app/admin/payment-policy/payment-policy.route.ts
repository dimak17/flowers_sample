import {Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaymentPolicyComponent } from './payment-policy.component';

export const paymentPolicyRoute: Routes = [
    {
        path: 'payment-policy',
        component: PaymentPolicyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.paymentPolicy.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
