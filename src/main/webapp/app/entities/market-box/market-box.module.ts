import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    MarketBoxService,
    MarketBoxPopupService,
    MarketBoxComponent,
    MarketBoxDetailComponent,
    MarketBoxDialogComponent,
    MarketBoxPopupComponent,
    MarketBoxDeletePopupComponent,
    MarketBoxDeleteDialogComponent,
    marketBoxRoute,
    marketBoxPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketBoxRoute,
    ...marketBoxPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketBoxComponent,
        MarketBoxDetailComponent,
        MarketBoxDialogComponent,
        MarketBoxDeleteDialogComponent,
        MarketBoxPopupComponent,
        MarketBoxDeletePopupComponent,
    ],
    entryComponents: [
        MarketBoxComponent,
        MarketBoxDialogComponent,
        MarketBoxPopupComponent,
        MarketBoxDeleteDialogComponent,
        MarketBoxDeletePopupComponent,
    ],
    providers: [
        MarketBoxService,
        MarketBoxPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersMarketBoxModule {}
