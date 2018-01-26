import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {MarketComponent} from './market.component';
import {MarketDetailComponent} from './market-detail.component';
import {MarketPopupComponent} from './market-dialog.component';
import {MarketDeletePopupComponent} from './market-delete-dialog.component';

export const marketRoute: Routes = [
    {
        path: 'market',
        component: MarketComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.market.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market/:id',
        component: MarketDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.market.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketPopupRoute: Routes = [
    {
        path: 'market-new',
        component: MarketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.market.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market/:id/edit',
        component: MarketPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.market.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market/:id/delete',
        component: MarketDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.market.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
