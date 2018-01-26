import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {ImageDeletePopupComponent, VarietyDeleteImageComponent} from './variety-delete-image.component';
import {CommonModule} from '@angular/common';
import {varietyPopupRoute, varietyRoute} from './variety.route';
import {VarietyComponent} from './variety.component';
import {VarietyDialogComponent, VarietyPopupComponent} from './variety-dialog.component';
import {VarietyDeleteDialogComponent, VarietyDeletePopupComponent} from './variety-delete-dialog.component';
import {VarietyService} from './variety.service';
import {VarietyPopupService} from './variety-popup.service';
import {SelectModule} from 'ng2-select';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

const ENTITY_STATES = [
    ...varietyRoute,
    ...varietyPopupRoute,
];

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        CommonModule,
        FlowersSharedModule,
        SelectModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
    ],
    declarations: [
        VarietyComponent,
        VarietyDialogComponent,
        VarietyDeleteDialogComponent,
        VarietyPopupComponent,
        VarietyDeletePopupComponent,
        VarietyDeleteImageComponent,
        ImageDeletePopupComponent,
    ],
    entryComponents: [
        VarietyComponent,
        VarietyDialogComponent,
        VarietyPopupComponent,
        VarietyDeleteDialogComponent,
        VarietyDeletePopupComponent,
        VarietyDeleteImageComponent,
        ImageDeletePopupComponent,
    ],
    providers: [
        VarietyService,
        VarietyPopupService,
    ],
    exports: [VarietyComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersVarietyModule {}
