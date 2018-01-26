import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {destinationPopupRoute, destinationRoute} from './destination.route';
import {DestinationComponent} from './destination.component';
import {DestinationDialogComponent, DestinationPopupComponent} from './destination-dialog.component';
import {DestinationDeleteDialogComponent, DestinationDeletePopupComponent} from './destination-delete-dialog.component';
import {DestinationService} from './destination.service';
import {DestinationPopupService} from './destination-popup.service';

const ENTITY_STATES = [
    ...destinationRoute,
    ...destinationPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DestinationComponent,
        DestinationDialogComponent,
        DestinationDeleteDialogComponent,
        DestinationPopupComponent,
        DestinationDeletePopupComponent,
    ],
    entryComponents: [
        DestinationComponent,
        DestinationDialogComponent,
        DestinationPopupComponent,
        DestinationDeleteDialogComponent,
        DestinationDeletePopupComponent,
    ],
    providers: [
        DestinationService,
        DestinationPopupService,
    ],
    exports: [
        DestinationComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersDestinationModule {}
