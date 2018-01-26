import {Routes} from '@angular/router';
import {UserRouteAccessService} from '../../shared';
import {BankDetailsComponent} from './bank-details.component';
import {BankDetailsPopupComponent} from './bank-details-dialog.component';
import {BankDetailsPopupComponentPreview} from './bank-details-preview-dialog.component';
import {BankDetailsDeletePopupComponent} from './bank-details-delete-dialog.component';

export const bankDetailsRoute: Routes = [
    {
        path: 'bank-details',
        component: BankDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.bankDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bank-details/:id',
        component: BankDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.bankDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bankDetailsPopupRoute: Routes = [
    {
        path: 'bank-details-new',
        component: BankDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.bankDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bank-details/:id/edit',
        component: BankDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.bankDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bank-details/:id/delete',
        component: BankDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.bankDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bank-details/:id/type/:type/delete',
        component: BankDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.bankDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

export const bankDetailsReportRoute: Routes = [
    {
        path: 'bank-details-preview/:data',
        component: BankDetailsPopupComponentPreview,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.bankDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
];
