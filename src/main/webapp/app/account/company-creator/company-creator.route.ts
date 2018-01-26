import {CompanyCreatorComponent} from './company-creator.component';
import {Route} from '@angular/router';
import {UserRouteAccessService} from '../../shared';
/**
 * Created by platon on 06.03.17.
 */
export const companyCreatorRoute: Route = {
    path: 'company-creator-page',
    component: CompanyCreatorComponent,
    data: {
        authorities: [],
        pageTitle: 'flowersApp.company-creator.title'
    },
    canActivate: [UserRouteAccessService]
};
