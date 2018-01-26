import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    clientEmployeePopupRoute, ClientEmployeeResolvePagingParams,
    clientEmployeeRoute
} from './client-employee.route';
import {ClientEmployeeComponent} from './client-employee.component';
import {ClientEmployeeDialogComponent, ClientEmployeePopupComponent} from './client-employee-dialog.component';
import {
    ClientEmployeeDeleteDialogComponent,
    ClientEmployeeDeletePopupComponent
} from './client-employee-delete-dialog.component';
import {ClientEmployeeService} from './client-employee.service';
import {ClientEmployeePopupService} from './client-employee-popup.service';

const ENTITY_STATES = [
    ...clientEmployeeRoute,
    ...clientEmployeePopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientEmployeeComponent,
        ClientEmployeeDialogComponent,
        ClientEmployeeDeleteDialogComponent,
        ClientEmployeePopupComponent,
        ClientEmployeeDeletePopupComponent,
    ],
    entryComponents: [
        ClientEmployeeComponent,
        ClientEmployeeDialogComponent,
        ClientEmployeePopupComponent,
        ClientEmployeeDeleteDialogComponent,
        ClientEmployeeDeletePopupComponent,
    ],
    providers: [
        ClientEmployeeService,
        ClientEmployeePopupService,
        ClientEmployeeResolvePagingParams,
    ],
    exports: [
        ClientEmployeeComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersClientEmployeeModule {}
