import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    BlockService,
    BlockPopupService,
    BlockComponent,
    BlockDetailComponent,
    BlockDialogComponent,
    BlockPopupComponent,
    BlockDeletePopupComponent,
    BlockDeleteDialogComponent,
    blockRoute,
    blockPopupRoute,
} from './';

const ENTITY_STATES = [
    ...blockRoute,
    ...blockPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BlockComponent,
        BlockDetailComponent,
        BlockDialogComponent,
        BlockDeleteDialogComponent,
        BlockPopupComponent,
        BlockDeletePopupComponent,
    ],
    entryComponents: [
        BlockComponent,
        BlockDialogComponent,
        BlockPopupComponent,
        BlockDeleteDialogComponent,
        BlockDeletePopupComponent,
    ],
    providers: [
        BlockService,
        BlockPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersBlockModule {}
