import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    BoxTypeGroupService,
    BoxTypeGroupPopupService,
    BoxTypeGroupComponent,
    BoxTypeGroupDetailComponent,
    BoxTypeGroupDialogComponent,
    BoxTypeGroupPopupComponent,
    BoxTypeGroupDeletePopupComponent,
    BoxTypeGroupDeleteDialogComponent,
    boxTypeGroupRoute,
    boxTypeGroupPopupRoute,
} from './';

const ENTITY_STATES = [
    ...boxTypeGroupRoute,
    ...boxTypeGroupPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BoxTypeGroupComponent,
        BoxTypeGroupDetailComponent,
        BoxTypeGroupDialogComponent,
        BoxTypeGroupDeleteDialogComponent,
        BoxTypeGroupPopupComponent,
        BoxTypeGroupDeletePopupComponent,
    ],
    entryComponents: [
        BoxTypeGroupComponent,
        BoxTypeGroupDialogComponent,
        BoxTypeGroupPopupComponent,
        BoxTypeGroupDeleteDialogComponent,
        BoxTypeGroupDeletePopupComponent,
    ],
    providers: [
        BoxTypeGroupService,
        BoxTypeGroupPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersBoxTypeGroupModule {}
