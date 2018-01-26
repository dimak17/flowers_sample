import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { EmployeeInfoComponent } from './employee-info.component';

export const employeeInfoRoute: Route = {
        path: 'employee-info',
        component: EmployeeInfoComponent,
        data: {
            authorities: ['ROLE_USER']
        },
        canActivate: [UserRouteAccessService]
};
