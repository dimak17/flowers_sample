import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import {ClientLabelInfoComponent} from './client-label-info.component';

export const clientLabelInfoRoute: Route = {
        path: 'client-label-info',
        component: ClientLabelInfoComponent,
        data: {
            authorities: ['ROLE_USER']
        },
        canActivate: [UserRouteAccessService]
};
