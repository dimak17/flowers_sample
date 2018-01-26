import {Route} from '@angular/router';
import {UserRouteAccessService} from '../../shared';
import {CargoAgenciesComponent} from './cargo-agencies.component';

export const cargoAgenciesRoute: Route = {
    path: 'cargo-agencies',
    component: CargoAgenciesComponent,
    data: {
        authorities: ['ROLE_USER']
    },
    canActivate: [UserRouteAccessService],
};
