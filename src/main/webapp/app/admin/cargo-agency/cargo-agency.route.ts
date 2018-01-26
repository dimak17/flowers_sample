import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { CargoAgencyComponent } from './cargo-agency.component';
import { CargoAgencyPopupComponent } from './cargo-agency-dialog.component';
import { CargoAgencyDeletePopupComponent } from './cargo-agency-delete-dialog.component';

export const cargoAgencyRoute: Routes = [
    {
        path: 'cargo-agency',
        component: CargoAgencyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoAgency.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];

export const cargoAgencyPopupRoute: Routes = [
    {
        path: 'cargo-agency-new',
        component: CargoAgencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoAgency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cargo-agency/:id/edit',
        component: CargoAgencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoAgency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cargo-agency/:id/delete',
        component: CargoAgencyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoAgency.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
