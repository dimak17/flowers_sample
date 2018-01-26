import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    ClientPaymentPolicyService,
    ClientPaymentPolicyPopupService,
    ClientPaymentPolicyComponent,
    ClientPaymentPolicyDetailComponent,
    ClientPaymentPolicyDialogComponent,
    ClientPaymentPolicyPopupComponent,
    ClientPaymentPolicyDeletePopupComponent,
    ClientPaymentPolicyDeleteDialogComponent,
    clientPaymentPolicyRoute,
    clientPaymentPolicyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...clientPaymentPolicyRoute,
    ...clientPaymentPolicyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientPaymentPolicyComponent,
        ClientPaymentPolicyDetailComponent,
        ClientPaymentPolicyDialogComponent,
        ClientPaymentPolicyDeleteDialogComponent,
        ClientPaymentPolicyPopupComponent,
        ClientPaymentPolicyDeletePopupComponent,
    ],
    entryComponents: [
        ClientPaymentPolicyComponent,
        ClientPaymentPolicyDialogComponent,
        ClientPaymentPolicyPopupComponent,
        ClientPaymentPolicyDeleteDialogComponent,
        ClientPaymentPolicyDeletePopupComponent,
    ],
    providers: [
        ClientPaymentPolicyService,
        ClientPaymentPolicyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersClientPaymentPolicyModule {}
