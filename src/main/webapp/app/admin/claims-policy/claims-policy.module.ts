import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    ClaimsPolicyService,
    ClaimsPolicyPopupService,
    ClaimsPolicyComponent,
    ClaimsPolicyDialogComponent,
    ClaimsPolicyPopupComponent,
    ClaimsPolicyDeletePopupComponent,
    ClaimsPolicyDeleteDialogComponent,
    claimsPolicyRoute,
    claimsPolicyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...claimsPolicyRoute,
    ...claimsPolicyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClaimsPolicyComponent,
        ClaimsPolicyDialogComponent,
        ClaimsPolicyDeleteDialogComponent,
        ClaimsPolicyPopupComponent,
        ClaimsPolicyDeletePopupComponent,
    ],
    entryComponents: [
        ClaimsPolicyComponent,
        ClaimsPolicyDialogComponent,
        ClaimsPolicyPopupComponent,
        ClaimsPolicyDeleteDialogComponent,
        ClaimsPolicyDeletePopupComponent,
    ],
    providers: [
        ClaimsPolicyService,
        ClaimsPolicyPopupService,
    ],
    exports : [ClaimsPolicyComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersClaimsPolicyModule {}
