import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    PriceListService,
    PriceListPopupService,
    PriceListComponent,
    PriceListDetailComponent,
    PriceListDialogComponent,
    PriceListPopupComponent,
    PriceListDeletePopupComponent,
    PriceListDeleteDialogComponent,
    priceListRoute,
    priceListPopupRoute,
} from './';

const ENTITY_STATES = [
    ...priceListRoute,
    ...priceListPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PriceListComponent,
        PriceListDetailComponent,
        PriceListDialogComponent,
        PriceListDeleteDialogComponent,
        PriceListPopupComponent,
        PriceListDeletePopupComponent,
    ],
    entryComponents: [
        PriceListComponent,
        PriceListDialogComponent,
        PriceListPopupComponent,
        PriceListDeleteDialogComponent,
        PriceListDeletePopupComponent,
    ],
    providers: [
        PriceListService,
        PriceListPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [
        PriceListComponent,
        PriceListDetailComponent,
        PriceListDialogComponent,
        PriceListDeleteDialogComponent,
        PriceListPopupComponent,
        PriceListDeletePopupComponent,
    ],
})
export class FlowersPriceListModule {}
