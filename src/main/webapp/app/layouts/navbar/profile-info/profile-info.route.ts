import { Routes} from '@angular/router';
import { UserRouteAccessService } from '../../../shared';
import {ImageDeletePopupComponent} from './profile-info-delete-image.component';

export const profileInfoPopupRoute: Routes = [

    {
        path: 'company-user/image/delete',
        component: ImageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },

    {
        path: 'company-user/:id/image/delete',
        component: ImageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
];
