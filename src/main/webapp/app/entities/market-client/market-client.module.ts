import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    MarketClientService,
    MarketClientPopupService,
    MarketClientComponent,
    MarketClientDetailComponent,
    MarketClientDialogComponent,
    MarketClientPopupComponent,
    MarketClientDeletePopupComponent,
    MarketClientDeleteDialogComponent,
    marketClientRoute,
    marketClientPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketClientRoute,
    ...marketClientPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketClientComponent,
        MarketClientDetailComponent,
        MarketClientDialogComponent,
        MarketClientDeleteDialogComponent,
        MarketClientPopupComponent,
        MarketClientDeletePopupComponent,
    ],
    entryComponents: [
        MarketClientComponent,
        MarketClientDialogComponent,
        MarketClientPopupComponent,
        MarketClientDeleteDialogComponent,
        MarketClientDeletePopupComponent,
    ],
    providers: [
        MarketClientService,
        MarketClientPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersMarketClientModule {}
