import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {pinchPopupRoute, pinchRoute} from './pinch.route';
import {PinchComponent} from './pinch.component';
import {PinchDialogComponent, PinchPopupComponent} from './pinch-dialog.component';
import {PinchDeleteDialogComponent, PinchDeletePopupComponent} from './pinch-delete-dialog.component';
import {PinchService} from './pinch.service';
import {PinchPopupService} from './pinch-popup.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AccordionModule, DataTableModule, InputMaskModule, SharedModule} from 'primeng/primeng';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SelectModule} from 'ng2-select';

const ENTITY_STATES = [
    ...pinchRoute,
    ...pinchPopupRoute,
];

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        SelectModule,
        InputMaskModule,
        DataTableModule,
        SharedModule,
        BrowserAnimationsModule,
        AccordionModule,
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PinchComponent,
        PinchDialogComponent,
        PinchDeleteDialogComponent,
        PinchPopupComponent,
        PinchDeletePopupComponent,
    ],
    entryComponents: [
        PinchComponent,
        PinchDialogComponent,
        PinchPopupComponent,
        PinchDeleteDialogComponent,
        PinchDeletePopupComponent,
    ],
    providers: [
        PinchService,
        PinchPopupService,
    ],
    exports: [
        PinchComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersPinchModule {}
