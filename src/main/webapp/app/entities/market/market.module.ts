import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    MarketService,
    MarketPopupService,
    MarketComponent,
    MarketDetailComponent,
    MarketDialogComponent,
    MarketPopupComponent,
    MarketDeletePopupComponent,
    MarketDeleteDialogComponent,
    marketRoute,
    marketPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketRoute,
    ...marketPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketComponent,
        MarketDetailComponent,
        MarketDialogComponent,
        MarketDeleteDialogComponent,
        MarketPopupComponent,
        MarketDeletePopupComponent,
    ],
    entryComponents: [
        MarketComponent,
        MarketDialogComponent,
        MarketPopupComponent,
        MarketDeleteDialogComponent,
        MarketDeletePopupComponent,
    ],
    providers: [
        MarketService,
        MarketPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersMarketModule {}
