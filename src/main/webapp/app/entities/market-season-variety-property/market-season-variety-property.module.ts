import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    MarketSeasonVarietyPropertyService,
    MarketSeasonVarietyPropertyPopupService,
    MarketSeasonVarietyPropertyComponent,
    MarketSeasonVarietyPropertyDetailComponent,
    MarketSeasonVarietyPropertyDialogComponent,
    MarketSeasonVarietyPropertyPopupComponent,
    MarketSeasonVarietyPropertyDeletePopupComponent,
    MarketSeasonVarietyPropertyDeleteDialogComponent,
    marketSeasonVarietyPropertyRoute,
    marketSeasonVarietyPropertyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketSeasonVarietyPropertyRoute,
    ...marketSeasonVarietyPropertyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketSeasonVarietyPropertyComponent,
        MarketSeasonVarietyPropertyDetailComponent,
        MarketSeasonVarietyPropertyDialogComponent,
        MarketSeasonVarietyPropertyDeleteDialogComponent,
        MarketSeasonVarietyPropertyPopupComponent,
        MarketSeasonVarietyPropertyDeletePopupComponent,
    ],
    entryComponents: [
        MarketSeasonVarietyPropertyComponent,
        MarketSeasonVarietyPropertyDialogComponent,
        MarketSeasonVarietyPropertyPopupComponent,
        MarketSeasonVarietyPropertyDeleteDialogComponent,
        MarketSeasonVarietyPropertyDeletePopupComponent,
    ],
    providers: [
        MarketSeasonVarietyPropertyService,
        MarketSeasonVarietyPropertyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersMarketSeasonVarietyPropertyModule {}
