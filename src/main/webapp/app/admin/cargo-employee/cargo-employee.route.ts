import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { CargoEmployeeComponent } from './cargo-employee.component';
import { CargoEmployeePopupComponent } from './cargo-employee-dialog.component';
import { CargoEmployeeDeletePopupComponent } from './cargo-employee-delete-dialog.component';

export const cargoEmployeeRoute: Routes = [
    {
        path: 'cargo-employee',
        component: CargoEmployeeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoEmployee.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
];

export const cargoEmployeePopupRoute: Routes = [
    {
        path: 'cargo-employee-new',
        component: CargoEmployeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoEmployee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cargo-employee/:cargoEmployeeId/edit',
        component: CargoEmployeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoEmployee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cargo-employee/:cargoEmployeeId/cargo-agency/:cargoAgencyId/delete',
        component: CargoEmployeeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoEmployee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cargo-employee/:cargoEmployeeId/cargo-agency/:cargoAgencyId/edit',
        component: CargoEmployeePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.cargoEmployee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
];
