import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {MultiSelectModule, TabViewModule, SelectButtonModule, CheckboxModule, DropdownModule, DataTableModule, SharedModule, InputMaskModule} from 'primeng/primeng';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
    MarketService,
    MarketPopupService,
    MarketDeletePopupDialogComponent,
    MarketComponent,
    MarketDialogComponent,
    MarketPopupComponent,
    MarketDeletePopupComponent,
    marketPopupRoute,

} from './';
import {SelectModule} from 'ng2-select';

const ENTITY_STATES = [
    ...marketPopupRoute,
];

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        MultiSelectModule,
        DropdownModule,
        TabViewModule,
        SelectModule,
        SelectButtonModule,
        CheckboxModule,
        DataTableModule,
        InputMaskModule,
        SharedModule,
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MarketComponent,
        MarketDialogComponent,
        MarketPopupComponent,
        MarketDeletePopupComponent,
        MarketDeletePopupDialogComponent
    ],
    entryComponents: [
        MarketComponent,
        MarketDialogComponent,
        MarketPopupComponent,
        MarketDeletePopupComponent,
        MarketDeletePopupDialogComponent,
    ],
    providers: [
        MarketService,
        MarketPopupService
    ],
    exports: [
        MarketComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersMarketModule {}
