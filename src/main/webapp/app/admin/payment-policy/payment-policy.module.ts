import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    PaymentPolicyService,
    PaymentPolicyComponent,
    paymentPolicyRoute
} from './';

const ENTITY_STATES = [
    ...paymentPolicyRoute
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PaymentPolicyComponent
    ],
    entryComponents: [
        PaymentPolicyComponent
    ],
    providers: [
        PaymentPolicyService
    ],
    exports: [
        PaymentPolicyComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersPaymentPolicyModule {}
