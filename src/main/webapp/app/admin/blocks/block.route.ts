import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { BlockPopupComponent } from './block-dialog.component';
import { BlockDeletePopupComponent } from './block-delete-dialog.component';

export const blockPopupRoute: Routes = [
    {
        path: 'block-new',
        component: BlockPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'block/:id/edit',
        component: BlockPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.boxType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'block/:id/delete',
        component: BlockDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.box-type.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }

];
