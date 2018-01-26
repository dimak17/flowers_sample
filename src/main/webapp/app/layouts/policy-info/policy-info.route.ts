import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { PolicyInfoComponent } from './policy-info.component';

export const policyInfoRoute: Route = {
    path: 'policy-info',
    component: PolicyInfoComponent,
    data: {
        authorities: ['ROLE_USER']
    },
    canActivate: [UserRouteAccessService]
};
