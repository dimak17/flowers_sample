import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    PinchVarietyPropertyService,
    PinchVarietyPropertyPopupService,
    PinchVarietyPropertyComponent,
    PinchVarietyPropertyDetailComponent,
    PinchVarietyPropertyDialogComponent,
    PinchVarietyPropertyPopupComponent,
    PinchVarietyPropertyDeletePopupComponent,
    PinchVarietyPropertyDeleteDialogComponent,
    pinchVarietyPropertyRoute,
    pinchVarietyPropertyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pinchVarietyPropertyRoute,
    ...pinchVarietyPropertyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PinchVarietyPropertyComponent,
        PinchVarietyPropertyDetailComponent,
        PinchVarietyPropertyDialogComponent,
        PinchVarietyPropertyDeleteDialogComponent,
        PinchVarietyPropertyPopupComponent,
        PinchVarietyPropertyDeletePopupComponent,
    ],
    entryComponents: [
        PinchVarietyPropertyComponent,
        PinchVarietyPropertyDialogComponent,
        PinchVarietyPropertyPopupComponent,
        PinchVarietyPropertyDeleteDialogComponent,
        PinchVarietyPropertyDeletePopupComponent,
    ],
    providers: [
        PinchVarietyPropertyService,
        PinchVarietyPropertyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersPinchVarietyPropertyModule {}
