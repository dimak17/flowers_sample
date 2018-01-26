import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    PositionService,
    PositionPopupService,
    PositionComponent,
    PositionDetailComponent,
    PositionDialogComponent,
    PositionPopupComponent,
    PositionDeletePopupComponent,
    PositionDeleteDialogComponent,
    positionRoute,
    positionPopupRoute,
    PositionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...positionRoute,
    ...positionPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PositionComponent,
        PositionDetailComponent,
        PositionDialogComponent,
        PositionDeleteDialogComponent,
        PositionPopupComponent,
        PositionDeletePopupComponent,
    ],
    entryComponents: [
        PositionComponent,
        PositionDialogComponent,
        PositionPopupComponent,
        PositionDeleteDialogComponent,
        PositionDeletePopupComponent,
    ],
    providers: [
        PositionService,
        PositionPopupService,
        PositionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersPositionModule {}
