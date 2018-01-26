import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { PaginationUtil } from 'ng-jhipster';

import { DiseaseComponent } from './disease.component';
import { DiseaseDetailComponent } from './disease-detail.component';
import { DiseasePopupComponent } from './disease-dialog.component';
import { DiseaseDeletePopupComponent } from './disease-delete-dialog.component';

@Injectable()
export class DiseaseResolvePagingParams implements Resolve<any> {

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

export const diseaseRoute: Routes = [
  {
    path: 'disease',
    component: DiseaseComponent,
    resolve: {
      'pagingParams': DiseaseResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'flowersApp.disease.home.title'
    }
  }, {
    path: 'disease/:id',
    component: DiseaseDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'flowersApp.disease.home.title'
    }
  }
];

export const diseasePopupRoute: Routes = [
  {
    path: 'disease-new',
    component: DiseasePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'flowersApp.disease.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'disease/:id/edit',
    component: DiseasePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'flowersApp.disease.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'disease/:id/delete',
    component: DiseaseDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'flowersApp.disease.home.title'
    },
    outlet: 'popup'
  }
];
