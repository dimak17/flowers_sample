import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    DiseaseService,
    DiseasePopupService,
    DiseaseComponent,
    DiseaseDetailComponent,
    DiseaseDialogComponent,
    DiseasePopupComponent,
    DiseaseDeletePopupComponent,
    DiseaseDeleteDialogComponent,
    diseaseRoute,
    diseasePopupRoute,
    DiseaseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...diseaseRoute,
    ...diseasePopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DiseaseComponent,
        DiseaseDetailComponent,
        DiseaseDialogComponent,
        DiseaseDeleteDialogComponent,
        DiseasePopupComponent,
        DiseaseDeletePopupComponent,
    ],
    entryComponents: [
        DiseaseComponent,
        DiseaseDialogComponent,
        DiseasePopupComponent,
        DiseaseDeleteDialogComponent,
        DiseaseDeletePopupComponent,
    ],
    providers: [
        DiseaseService,
        DiseasePopupService,
        DiseaseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersDiseaseModule {}
