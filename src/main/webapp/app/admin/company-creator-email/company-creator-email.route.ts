import {Route} from '@angular/router';
import {CompanyCreatorEmailComponent} from './company-creator-email.component';
/**
 * Created by platon on 06.03.17.
 */
export const companyCreatorEmailRoute: Route = {
    path: 'company-create-email',
    component: CompanyCreatorEmailComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'flowersApp.company-creator-email.title'
    }
};
