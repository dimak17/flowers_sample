import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {GrowlModule} from 'primeng/primeng';
import {
    BoxGroupingDeletePopupComponent,
    BoxGroupingService,
    BoxGroupingPopupService,
    BoxGroupingComponent,
    BoxGroupingGroupComponent,
    BoxGroupingDeleteDialogComponent,
    boxGroupConfirmationRoute,
} from './';

const ENTITY_STATES = [
    boxGroupConfirmationRoute
];

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        GrowlModule,
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BoxGroupingDeletePopupComponent,
        BoxGroupingComponent,
        BoxGroupingGroupComponent,
        BoxGroupingDeleteDialogComponent
    ],
    providers: [
        BoxGroupingService,
        BoxGroupingPopupService,
    ],
    exports: [
        BoxGroupingComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersBoxGroupingModule {}
