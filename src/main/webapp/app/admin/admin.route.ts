import {Routes} from '@angular/router';
import {
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    companyCreatorEmailRoute,
    logsRoute,
    metricsRoute,
    userMgmtRoute,
    userDialogRoute
} from './';

import { UserRouteAccessService } from '../shared';
import {boxGroupConfirmationRoute} from './box-grouping/box-grouping.route';

const ADMIN_ROUTES = [
    auditsRoute,
    configurationRoute,
    docsRoute,
    healthRoute,
    companyCreatorEmailRoute,
    logsRoute,
    ...userMgmtRoute,
    metricsRoute
];

export const adminState: Routes = [{
        path: '',
        data: {
            authorities: ['ROLE_ADMIN']
        },
        canActivate: [UserRouteAccessService],
        children: ADMIN_ROUTES
    },
    ...userDialogRoute,
    boxGroupConfirmationRoute,
];
