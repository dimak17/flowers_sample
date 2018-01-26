import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {MarketSeasonComponent} from './market-season.component';
import {MarketSeasonDetailComponent} from './market-season-detail.component';
import {MarketSeasonPopupComponent} from './market-season-dialog.component';
import {MarketSeasonDeletePopupComponent} from './market-season-delete-dialog.component';

export const marketSeasonRoute: Routes = [
    {
        path: 'market-season',
        component: MarketSeasonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketSeason.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-season/:id',
        component: MarketSeasonDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketSeason.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketSeasonPopupRoute: Routes = [
    {
        path: 'market-season-new',
        component: MarketSeasonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketSeason.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-season/:id/edit',
        component: MarketSeasonPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketSeason.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-season/:id/delete',
        component: MarketSeasonDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.marketSeason.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
