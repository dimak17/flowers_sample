import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    BoxGroupService,
    BoxGroupPopupService,
    BoxGroupComponent,
    BoxGroupDetailComponent,
    BoxGroupDialogComponent,
    BoxGroupPopupComponent,
    BoxGroupDeletePopupComponent,
    BoxGroupDeleteDialogComponent,
    boxGroupRoute,
    boxGroupPopupRoute,
} from './';

const ENTITY_STATES = [
    ...boxGroupRoute,
    ...boxGroupPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BoxGroupComponent,
        BoxGroupDetailComponent,
        BoxGroupDialogComponent,
        BoxGroupDeleteDialogComponent,
        BoxGroupPopupComponent,
        BoxGroupDeletePopupComponent,
    ],
    entryComponents: [
        BoxGroupComponent,
        BoxGroupDialogComponent,
        BoxGroupPopupComponent,
        BoxGroupDeleteDialogComponent,
        BoxGroupDeletePopupComponent,
    ],
    providers: [
        BoxGroupService,
        BoxGroupPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersBoxGroupModule {}
