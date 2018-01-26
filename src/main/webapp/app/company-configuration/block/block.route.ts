import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {PaginationUtil} from 'ng-jhipster';
import {BlockComponent} from './block.component';
import {BlockDetailComponent} from './block-detail.component';
import {BlockPopupComponent} from './block-dialog.component';
import {BlockDeletePopupComponent} from './block-delete-dialog.component';

@Injectable()
export class BlockResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const blockRoute: Routes = [
    {
        path: 'block',
        component: BlockComponent,
        resolve: {
            'pagingParams': BlockResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.block.home.title'
        }
    }, {
        path: 'block/:id',
        component: BlockDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.block.home.title'
        }
    }
];

export const blockPopupRoute: Routes = [
    {
        path: 'block-new',
        component: BlockPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.block.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'block/:id/edit',
        component: BlockPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.block.home.title'
        },
        outlet: 'popup'
    },
    {
        path: 'block/:id/delete',
        component: BlockDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'flowersApp.block.home.title'
        },
        outlet: 'popup'
    }
];
