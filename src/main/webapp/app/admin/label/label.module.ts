import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {labelPopupRoute, LabelResolvePagingParams, labelRoute} from './label.route';
import {LabelComponent} from './label.component';
import {LabelDialogComponent, LabelPopupComponent} from './label-dialog.component';
import {LabelDeleteDialogComponent, LabelDeletePopupComponent} from './label-delete-dialog.component';
import {LabelService} from './label.service';
import {LabelPopupService} from './label-popup.service';

const ENTITY_STATES = [
    ...labelRoute,
    ...labelPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LabelComponent,
        LabelDialogComponent,
        LabelDeleteDialogComponent,
        LabelPopupComponent,
        LabelDeletePopupComponent,
    ],
    entryComponents: [
        LabelComponent,
        LabelDialogComponent,
        LabelPopupComponent,
        LabelDeleteDialogComponent,
        LabelDeletePopupComponent,
    ],
    providers: [
        LabelService,
        LabelPopupService,
        LabelResolvePagingParams,
    ],
    exports: [
        LabelComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersLabelModule {}
