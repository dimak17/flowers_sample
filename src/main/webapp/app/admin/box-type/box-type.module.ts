import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {BoxTypeService} from './box-type.service';
import {boxTypePopupRoute, boxTypeRoute} from './box-type.route';
import {BoxTypeComponent} from './box-type.component';
import {BoxTypeDialogComponent, BoxTypePopupComponent} from './box-type-dialog.component';
import {BoxTypeDeleteDialogComponent, BoxTypeDeletePopupComponent} from './box-type-delete-dialog.component';
import {BoxTypePopupService} from './box-type-popup.service';

const ENTITY_STATES = [
    ...boxTypeRoute,
    ...boxTypePopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BoxTypeComponent,
        BoxTypeDialogComponent,
        BoxTypeDeleteDialogComponent,
        BoxTypePopupComponent,
        BoxTypeDeletePopupComponent,
    ],
    entryComponents: [
        BoxTypeComponent,
        BoxTypeDialogComponent,
        BoxTypePopupComponent,
        BoxTypeDeleteDialogComponent,
        BoxTypeDeletePopupComponent,
    ],
    providers: [
        BoxTypeService,
        BoxTypePopupService,
    ],

    exports: [ BoxTypeComponent, ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersBoxTypeModule {}
