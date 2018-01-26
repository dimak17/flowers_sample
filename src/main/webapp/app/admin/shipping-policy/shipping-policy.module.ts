import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    ShippingPolicyService,
    ShippingPolicyPopupService,
    ShippingPolicyComponent,
    ShippingPolicyDialogComponent,
    ShippingPolicyPopupComponent,
    ShippingPolicyDeletePopupComponent,
    ShippingPolicyDeleteDialogComponent,
    shippingPolicyRoute,
    shippingPolicyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...shippingPolicyRoute,
    ...shippingPolicyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShippingPolicyComponent,
        ShippingPolicyDialogComponent,
        ShippingPolicyDeleteDialogComponent,
        ShippingPolicyPopupComponent,
        ShippingPolicyDeletePopupComponent,
    ],
    entryComponents: [
        ShippingPolicyComponent,
        ShippingPolicyDialogComponent,
        ShippingPolicyPopupComponent,
        ShippingPolicyDeleteDialogComponent,
        ShippingPolicyDeletePopupComponent,
    ],
    providers: [
        ShippingPolicyService,
        ShippingPolicyPopupService,
    ],
    exports : [ShippingPolicyComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class FlowersShippingPolicyModule {}
