
import {Routes} from '@angular/router';
import {LogoDeletePopupComponent} from './logo-delete-image.component';
import {UserRouteAccessService} from '../../shared/auth/user-route-access-service';
import {FarmDataComponent} from './farm-data.component';

export const farmDataRoute: Routes = [
    {
        path: 'farm-data',
        component: FarmDataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.farm-data.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const farmDataPopupRoute: Routes = [
    {
        path: 'farm-data/image/delete',
        component: LogoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
