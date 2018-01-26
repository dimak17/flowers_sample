import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {PaginationUtil} from 'ng-jhipster';
import {SortComponent} from './sort.component';
import {SortDetailComponent} from './sort-detail.component';
import {SortPopupComponent} from './sort-dialog.component';
import {SortDeletePopupComponent} from './sort-delete-dialog.component';

@Injectable()
export class SortResolvePagingParams implements Resolve<any> {

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

export const sortRoute: Routes = [
  {
    path: 'sort',
    component: SortComponent,
    resolve: {
      'pagingParams': SortResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'flowersApp.sort.home.title'
    }
  }, {
    path: 'sort/:id',
    component: SortDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'flowersApp.sort.home.title'
    }
  }
];

export const sortPopupRoute: Routes = [
  {
    path: 'sort-new',
    component: SortPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'flowersApp.sort.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sort/:id/edit',
    component: SortPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'flowersApp.sort.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'sort/:id/delete',
    component: SortDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'flowersApp.sort.home.title'
    },
    outlet: 'popup'
  }
];
