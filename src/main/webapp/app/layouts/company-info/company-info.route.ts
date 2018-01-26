import {Route} from '@angular/router';
import {UserRouteAccessService} from '../../shared';
import {CompanyInfoComponent} from './company-info.component';

export const companyInfoRoute: Route = {
        path: 'company-info',
        component: CompanyInfoComponent,
        data: {
            authorities: ['ROLE_USER']
        },
        canActivate: [UserRouteAccessService],
};
