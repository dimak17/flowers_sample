import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    MarketVarietyPropertyService,
    MarketVarietyPropertyPopupService,
    MarketVarietyPropertyComponent,
    MarketVarietyPropertyDetailComponent,
    MarketVarietyPropertyDialogComponent,
    MarketVarietyPropertyPopupComponent,
    MarketVarietyPropertyDeletePopupComponent,
    MarketVarietyPropertyDeleteDialogComponent,
    marketVarietyPropertyRoute,
    marketVarietyPropertyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketVarietyPropertyRoute,
    ...marketVarietyPropertyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketVarietyPropertyComponent,
        MarketVarietyPropertyDetailComponent,
        MarketVarietyPropertyDialogComponent,
        MarketVarietyPropertyDeleteDialogComponent,
        MarketVarietyPropertyPopupComponent,
        MarketVarietyPropertyDeletePopupComponent,
    ],
    entryComponents: [
        MarketVarietyPropertyComponent,
        MarketVarietyPropertyDialogComponent,
        MarketVarietyPropertyPopupComponent,
        MarketVarietyPropertyDeleteDialogComponent,
        MarketVarietyPropertyDeletePopupComponent,
    ],
    providers: [
        MarketVarietyPropertyService,
        MarketVarietyPropertyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersMarketVarietyPropertyModule {}
