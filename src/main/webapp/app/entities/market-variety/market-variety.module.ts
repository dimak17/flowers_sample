import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    MarketVarietyService,
    MarketVarietyPopupService,
    MarketVarietyComponent,
    MarketVarietyDetailComponent,
    MarketVarietyDialogComponent,
    MarketVarietyPopupComponent,
    MarketVarietyDeletePopupComponent,
    MarketVarietyDeleteDialogComponent,
    marketVarietyRoute,
    marketVarietyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...marketVarietyRoute,
    ...marketVarietyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketVarietyComponent,
        MarketVarietyDetailComponent,
        MarketVarietyDialogComponent,
        MarketVarietyDeleteDialogComponent,
        MarketVarietyPopupComponent,
        MarketVarietyDeletePopupComponent,
    ],
    entryComponents: [
        MarketVarietyComponent,
        MarketVarietyDialogComponent,
        MarketVarietyPopupComponent,
        MarketVarietyDeleteDialogComponent,
        MarketVarietyDeletePopupComponent,
    ],
    providers: [
        MarketVarietyService,
        MarketVarietyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersMarketVarietyModule {}
