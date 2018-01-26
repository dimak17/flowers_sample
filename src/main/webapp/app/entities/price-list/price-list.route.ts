import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {PriceListComponent} from './price-list.component';
import {PriceListDetailComponent} from './price-list-detail.component';
import {PriceListPopupComponent} from './price-list-dialog.component';
import {PriceListDeletePopupComponent} from './price-list-delete-dialog.component';

export const priceListRoute: Routes = [
    {
        path: 'price-list',
        component: PriceListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.priceList.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'price-list/:id',
        component: PriceListDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.priceList.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const priceListPopupRoute: Routes = [
    {
        path: 'price-list-new',
        component: PriceListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.priceList.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'price-list/:id/edit',
        component: PriceListPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.priceList.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'price-list/:id/delete',
        component: PriceListDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.priceList.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
