import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import { FlowersAdminModule } from '../../admin/admin.module';
import {
    CompanyUserService,
    CompanyUserPopupService,
    CompanyUserComponent,
    CompanyUserDetailComponent,
    CompanyUserDialogComponent,
    CompanyUserPopupComponent,
    CompanyUserDeletePopupComponent,
    CompanyUserDeleteDialogComponent,
    companyUserRoute,
    companyUserPopupRoute,
    CompanyUserResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...companyUserRoute,
    ...companyUserPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        FlowersAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyUserComponent,
        CompanyUserDetailComponent,
        CompanyUserDialogComponent,
        CompanyUserDeleteDialogComponent,
        CompanyUserPopupComponent,
        CompanyUserDeletePopupComponent,
    ],
    entryComponents: [
        CompanyUserComponent,
        CompanyUserDialogComponent,
        CompanyUserPopupComponent,
        CompanyUserDeleteDialogComponent,
        CompanyUserDeletePopupComponent,
    ],
    providers: [
        CompanyUserService,
        CompanyUserPopupService,
        CompanyUserResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersCompanyUserModule {}
