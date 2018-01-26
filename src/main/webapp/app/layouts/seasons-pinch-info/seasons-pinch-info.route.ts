import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import {SeasonsPinchInfoComponent} from './seasons-pinch-info.component';

export const seasonsPinchInfoRoute: Route = {
        path: 'seasons-pinch-info',
        component: SeasonsPinchInfoComponent,
        data: {
            authorities: ['ROLE_USER']
        },
        canActivate: [UserRouteAccessService]
};
