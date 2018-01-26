import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { MarketPopupComponent } from './market-dialog.component';
import {MarketDeletePopupComponent} from './market-delete-dialog.component';

export const marketPopupRoute: Routes = [
    {
        path: 'market-new',
        component: MarketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market/:id/edit',
        component: MarketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market/:id/delete',
        component: MarketDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
