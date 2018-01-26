import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    MarketBoxVarietyPropertyService,
    MarketBoxVarietyPropertyPopupService,
    MarketBoxVarietyPropertyComponent,
    MarketBoxVarietyPropertyDetailComponent,
    MarketBoxVarietyPropertyDialogComponent,
    MarketBoxVarietyPropertyPopupComponent,
    MarketBoxVarietyPropertyDeletePopupComponent,
    MarketBoxVarietyPropertyDeleteDialogComponent,
    marketBoxVarietyPropertyRoute,
    marketBoxVarietyPropertyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketBoxVarietyPropertyRoute,
    ...marketBoxVarietyPropertyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketBoxVarietyPropertyComponent,
        MarketBoxVarietyPropertyDetailComponent,
        MarketBoxVarietyPropertyDialogComponent,
        MarketBoxVarietyPropertyDeleteDialogComponent,
        MarketBoxVarietyPropertyPopupComponent,
        MarketBoxVarietyPropertyDeletePopupComponent,
    ],
    entryComponents: [
        MarketBoxVarietyPropertyComponent,
        MarketBoxVarietyPropertyDialogComponent,
        MarketBoxVarietyPropertyPopupComponent,
        MarketBoxVarietyPropertyDeleteDialogComponent,
        MarketBoxVarietyPropertyDeletePopupComponent,
    ],
    providers: [
        MarketBoxVarietyPropertyService,
        MarketBoxVarietyPropertyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersMarketBoxVarietyPropertyModule {}
