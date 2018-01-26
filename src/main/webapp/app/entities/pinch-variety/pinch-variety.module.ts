import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    PinchVarietyService,
    PinchVarietyPopupService,
    PinchVarietyComponent,
    PinchVarietyDetailComponent,
    PinchVarietyDialogComponent,
    PinchVarietyPopupComponent,
    PinchVarietyDeletePopupComponent,
    PinchVarietyDeleteDialogComponent,
    pinchVarietyRoute,
    pinchVarietyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pinchVarietyRoute,
    ...pinchVarietyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PinchVarietyComponent,
        PinchVarietyDetailComponent,
        PinchVarietyDialogComponent,
        PinchVarietyDeleteDialogComponent,
        PinchVarietyPopupComponent,
        PinchVarietyDeletePopupComponent,
    ],
    entryComponents: [
        PinchVarietyComponent,
        PinchVarietyDialogComponent,
        PinchVarietyPopupComponent,
        PinchVarietyDeleteDialogComponent,
        PinchVarietyDeletePopupComponent,
    ],
    providers: [
        PinchVarietyService,
        PinchVarietyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersPinchVarietyModule {}
