import {Route} from '@angular/router';
import {UserRouteAccessService} from '../../shared';
import {PriceListsComponent} from './price-lists.component';

export const priceListsRoute: Route = {
    path: 'price-lists',
    component: PriceListsComponent,
    data: {
        authorities: ['ROLE_USER']
    },
    canActivate: [UserRouteAccessService],
};
