import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    MixTypeService,
    MixTypePopupService,
    MixTypeComponent,
    MixTypeDialogComponent,
    MixTypePopupComponent,
    MixTypeDeletePopupComponent,
    MixTypeDeleteDialogComponent,
    mixTypeRoute,
    mixTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...mixTypeRoute,
    ...mixTypePopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MixTypeComponent,
        MixTypeDialogComponent,
        MixTypeDeleteDialogComponent,
        MixTypePopupComponent,
        MixTypeDeletePopupComponent,
    ],
    entryComponents: [
        MixTypeComponent,
        MixTypeDialogComponent,
        MixTypePopupComponent,
        MixTypeDeleteDialogComponent,
        MixTypeDeletePopupComponent,
    ],
    providers: [
        MixTypeService,
        MixTypePopupService,
    ],
    exports: [
        MixTypeComponent,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersMixTypeModule {}
