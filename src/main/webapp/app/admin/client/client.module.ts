import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {clientPopupRoute, ClientResolvePagingParams, clientRoute} from './client.route';
import {ClientComponent} from './client.component';
import {ClientDialogComponent, ClientPopupComponent} from './client-dialog.component';
import {ClientDeleteDialogComponent, ClientDeletePopupComponent} from './client-delete-dialog.component';
import {ClientService} from './client.service';
import {ClientPopupService} from './client-popup.service';

const ENTITY_STATES = [
    ...clientRoute,
    ...clientPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientComponent,
        ClientDialogComponent,
        ClientDeleteDialogComponent,
        ClientPopupComponent,
        ClientDeletePopupComponent,
    ],
    entryComponents: [
        ClientComponent,
        ClientDialogComponent,
        ClientPopupComponent,
        ClientDeleteDialogComponent,
        ClientDeletePopupComponent,
    ],
    providers: [
        ClientService,
        ClientPopupService,
        ClientResolvePagingParams,
    ],
    exports: [
        ClientComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersClientModule {}
