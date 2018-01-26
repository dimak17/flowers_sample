import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    PaymentPolicyService,
    PaymentPolicyPopupService,
    PaymentPolicyComponent,
    PaymentPolicyDetailComponent,
    PaymentPolicyDialogComponent,
    PaymentPolicyPopupComponent,
    PaymentPolicyDeletePopupComponent,
    PaymentPolicyDeleteDialogComponent,
    paymentPolicyRoute,
    paymentPolicyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...paymentPolicyRoute,
    ...paymentPolicyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PaymentPolicyComponent,
        PaymentPolicyDetailComponent,
        PaymentPolicyDialogComponent,
        PaymentPolicyDeleteDialogComponent,
        PaymentPolicyPopupComponent,
        PaymentPolicyDeletePopupComponent,
    ],
    entryComponents: [
        PaymentPolicyComponent,
        PaymentPolicyDialogComponent,
        PaymentPolicyPopupComponent,
        PaymentPolicyDeleteDialogComponent,
        PaymentPolicyDeletePopupComponent,
    ],
    providers: [
        PaymentPolicyService,
        PaymentPolicyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersPaymentPolicyModule {}
