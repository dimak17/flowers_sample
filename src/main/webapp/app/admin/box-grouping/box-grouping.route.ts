import {Route} from '@angular/router';
import {BoxGroupingDeletePopupComponent} from './box-grouping-delete-dialog.component';
import {UserRouteAccessService} from '../../shared/auth/user-route-access-service';
export const boxGroupConfirmationRoute: Route = {
    path: 'box-grouping/:id/delete/:groupIndex/groupName/:groupName',
    component: BoxGroupingDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
};
