import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {clientEmployeePositionPopupRoute, clientEmployeePositionRoute} from './client-employee-position.route';
import {ClientEmployeePositionComponent} from './client-employee-position.component';
import {
    ClientEmployeePositionDialogComponent,
    ClientEmployeePositionPopupComponent
} from './client-employee-position-dialog.component';
import {
    ClientEmployeePositionDeleteDialogComponent,
    ClientEmployeePositionDeletePopupComponent
} from './client-employee-position-delete-dialog.component';
import {ClientEmployeePositionService} from './client-employee-position.service';
import {ClientEmployeePositionPopupService} from './client-employee-position-popup.service';

const ENTITY_STATES = [
    ...clientEmployeePositionRoute,
    ...clientEmployeePositionPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientEmployeePositionComponent,
        ClientEmployeePositionDialogComponent,
        ClientEmployeePositionDeleteDialogComponent,
        ClientEmployeePositionPopupComponent,
        ClientEmployeePositionDeletePopupComponent,
    ],
    entryComponents: [
        ClientEmployeePositionComponent,
        ClientEmployeePositionDialogComponent,
        ClientEmployeePositionPopupComponent,
        ClientEmployeePositionDeleteDialogComponent,
        ClientEmployeePositionDeletePopupComponent,
    ],
    providers: [
        ClientEmployeePositionService,
        ClientEmployeePositionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersClientEmployeePositionModule {}
