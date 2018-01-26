import { Routes} from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { VarietyComponent } from './variety.component';
import { VarietyPopupComponent } from './variety-dialog.component';
import { VarietyDeletePopupComponent } from './variety-delete-dialog.component';
import {ImageDeletePopupComponent} from './variety-delete-image.component';

export const varietyRoute: Routes = [
    {
        path: 'variety',
        component: VarietyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.variety.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const varietyPopupRoute: Routes = [
    {
        path: 'variety-new',
        component: VarietyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.variety.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'variety/:id/edit',
        component: VarietyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.variety.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'variety/:id/image/delete',
        component: ImageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'variety/image/delete',
        component: ImageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'variety/:id/delete',
        component: VarietyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.variety.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
];
