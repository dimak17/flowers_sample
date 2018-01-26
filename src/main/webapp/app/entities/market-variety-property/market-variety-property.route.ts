import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {MarketVarietyPropertyComponent} from './market-variety-property.component';
import {MarketVarietyPropertyDetailComponent} from './market-variety-property-detail.component';
import {MarketVarietyPropertyPopupComponent} from './market-variety-property-dialog.component';
import {MarketVarietyPropertyDeletePopupComponent} from './market-variety-property-delete-dialog.component';

export const marketVarietyPropertyRoute: Routes = [
    {
        path: 'market-variety-property',
        component: MarketVarietyPropertyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-variety-property/:id',
        component: MarketVarietyPropertyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketVarietyPropertyPopupRoute: Routes = [
    {
        path: 'market-variety-property-new',
        component: MarketVarietyPropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-variety-property/:id/edit',
        component: MarketVarietyPropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-variety-property/:id/delete',
        component: MarketVarietyPropertyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
