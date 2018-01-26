import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { CargoEmployeePositionComponent } from './cargo-employee-position.component';
import { CargoEmployeePositionPopupComponent } from './cargo-employee-position-dialog.component';
import { CargoEmployeePositionDeletePopupComponent } from './cargo-employee-position-delete-dialog.component';

export const cargoEmployeePositionRoute: Routes = [
    {
        path: 'cargo-employee-position',
        component: CargoEmployeePositionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoEmployeePosition.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];

export const cargoEmployeePositionPopupRoute: Routes = [
    {
        path: 'cargo-employee-position-new',
        component: CargoEmployeePositionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoEmployeePosition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cargo-employee-position/:id/edit',
        component: CargoEmployeePositionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoEmployeePosition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cargo-employee-position/:id/delete',
        component: CargoEmployeePositionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoEmployeePosition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
