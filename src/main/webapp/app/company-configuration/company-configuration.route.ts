import {Routes} from '@angular/router';
import {employeePopupRoute, employeeRoute} from './';
import {diseasePopupRoute, diseaseRoute} from './';
import {blockPopupRoute, blockRoute} from './';
import {sortPopupRoute, sortRoute} from './';

const COMPANY_CONFIGURATION_ROUTES = [
    ...blockRoute,
    ...blockPopupRoute,
    ...sortRoute,
    ...sortPopupRoute,
    ...employeeRoute,
    ...employeePopupRoute,
    ...diseaseRoute,
    ...diseasePopupRoute,
];

export const companyConfigurationState: Routes = [...COMPANY_CONFIGURATION_ROUTES];
