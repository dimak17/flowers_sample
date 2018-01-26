import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import {AirLinesInfoComponent} from './air-lines.component-info';

export const airLinesInfoRoute: Route = {
    path: 'air-lines-info',
    component: AirLinesInfoComponent,
    data: {
        authorities: ['ROLE_USER']
    },
    canActivate: [UserRouteAccessService]
};
