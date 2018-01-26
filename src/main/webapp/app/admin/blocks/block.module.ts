import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    BlockService,
    BlockPopupService,
    BlockComponent,
    BlockDialogComponent,
    BlockPopupComponent,
    BlockDeletePopupComponent,
    BlockDeleteDialogComponent,
    blockPopupRoute,
} from './';
import {SelectModule} from 'ng2-select';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
const ENTITY_STATES = [
    ...blockPopupRoute,
];

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        SelectModule,
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BlockComponent,
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
    exports: [BlockComponent ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersBlockModule {}
