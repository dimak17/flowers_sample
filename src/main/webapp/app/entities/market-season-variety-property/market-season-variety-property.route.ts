import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {MarketSeasonVarietyPropertyComponent} from './market-season-variety-property.component';
import {MarketSeasonVarietyPropertyDetailComponent} from './market-season-variety-property-detail.component';
import {MarketSeasonVarietyPropertyPopupComponent} from './market-season-variety-property-dialog.component';
import {MarketSeasonVarietyPropertyDeletePopupComponent} from './market-season-variety-property-delete-dialog.component';

export const marketSeasonVarietyPropertyRoute: Routes = [
    {
        path: 'market-season-variety-property',
        component: MarketSeasonVarietyPropertyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketSeasonVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-season-variety-property/:id',
        component: MarketSeasonVarietyPropertyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketSeasonVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketSeasonVarietyPropertyPopupRoute: Routes = [
    {
        path: 'market-season-variety-property-new',
        component: MarketSeasonVarietyPropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketSeasonVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-season-variety-property/:id/edit',
        component: MarketSeasonVarietyPropertyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketSeasonVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-season-variety-property/:id/delete',
        component: MarketSeasonVarietyPropertyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketSeasonVarietyProperty.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
