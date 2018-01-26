import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    MarketSeasonService,
    MarketSeasonPopupService,
    MarketSeasonComponent,
    MarketSeasonDetailComponent,
    MarketSeasonDialogComponent,
    MarketSeasonPopupComponent,
    MarketSeasonDeletePopupComponent,
    MarketSeasonDeleteDialogComponent,
    marketSeasonRoute,
    marketSeasonPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketSeasonRoute,
    ...marketSeasonPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketSeasonComponent,
        MarketSeasonDetailComponent,
        MarketSeasonDialogComponent,
        MarketSeasonDeleteDialogComponent,
        MarketSeasonPopupComponent,
        MarketSeasonDeletePopupComponent,
    ],
    entryComponents: [
        MarketSeasonComponent,
        MarketSeasonDialogComponent,
        MarketSeasonPopupComponent,
        MarketSeasonDeleteDialogComponent,
        MarketSeasonDeletePopupComponent,
    ],
    providers: [
        MarketSeasonService,
        MarketSeasonPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersMarketSeasonModule {}
