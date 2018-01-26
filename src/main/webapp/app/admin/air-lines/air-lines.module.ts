import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FlowersSharedModule } from '../../shared';
import {AirLinesComponent} from './air-lines.component';
import {airLinesPopupRoute, airLinesRoute} from './air-lines.route';
import {AirLinesDialogComponent, AirLinesPopupComponent} from './air-lines-dialog.component';
import {AirLinesDeleteDialogComponent, AirLinesDeletePopupComponent} from './air-lines-delete-dialog.component';
import {AirLinesService} from './air-lines.service';
import {AirLinesPopupService} from './air-lines-popup.service';

const ENTITY_STATES = [
    ...airLinesRoute,
    ...airLinesPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AirLinesComponent,
        AirLinesDialogComponent,
        AirLinesDeleteDialogComponent,
        AirLinesPopupComponent,
        AirLinesDeletePopupComponent,
    ],
    entryComponents: [
        AirLinesComponent,
        AirLinesDialogComponent,
        AirLinesPopupComponent,
        AirLinesDeleteDialogComponent,
        AirLinesDeletePopupComponent,
    ],
    providers: [
        AirLinesService,
        AirLinesPopupService,
    ],
    exports: [
        AirLinesComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersAirLinesModule {}
