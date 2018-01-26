import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    LabelCountryService,
    LabelCountryPopupService,
    LabelCountryComponent,
    LabelCountryDetailComponent,
    LabelCountryDialogComponent,
    LabelCountryPopupComponent,
    LabelCountryDeletePopupComponent,
    LabelCountryDeleteDialogComponent,
    labelCountryRoute,
    labelCountryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...labelCountryRoute,
    ...labelCountryPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LabelCountryComponent,
        LabelCountryDetailComponent,
        LabelCountryDialogComponent,
        LabelCountryDeleteDialogComponent,
        LabelCountryPopupComponent,
        LabelCountryDeletePopupComponent,
    ],
    entryComponents: [
        LabelCountryComponent,
        LabelCountryDialogComponent,
        LabelCountryPopupComponent,
        LabelCountryDeleteDialogComponent,
        LabelCountryDeletePopupComponent,
    ],
    providers: [
        LabelCountryService,
        LabelCountryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersLabelCountryModule {}
