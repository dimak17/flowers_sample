import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {
    AccordionModule, CheckboxModule, DataTableModule, DropdownModule, InputMaskModule,
    SharedModule
} from 'primeng/primeng';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {FlowersSharedModule} from '../../shared';
import {DefaultPriceListComponent, PriceListService} from './';
import {SeasonsPriceListComponent} from './seasons-price-list.component';
import {SeasonsPriceListService} from './seasons-price-list.service';
import {SelectModule} from 'ng2-select';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LowMarketPriceListComponent} from './low-market-price-list.component';
import {HighMarketPriceListComponent} from './high-market-price-list.component';

const ENTITY_STATES = [];

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        SelectModule,
        InputMaskModule,
        DataTableModule,
        SharedModule,
        BrowserAnimationsModule,
        AccordionModule,
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true}),
        CheckboxModule,
        DropdownModule
    ],
    declarations: [
        DefaultPriceListComponent,
        LowMarketPriceListComponent,
        HighMarketPriceListComponent,
        SeasonsPriceListComponent,
    ],
    entryComponents: [
        DefaultPriceListComponent,
        LowMarketPriceListComponent,
        HighMarketPriceListComponent,
        SeasonsPriceListComponent
    ],
    providers: [
        PriceListService,
        SeasonsPriceListService
    ],
    exports: [
        DefaultPriceListComponent,
        LowMarketPriceListComponent,
        HighMarketPriceListComponent,
        SeasonsPriceListComponent,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersPriceListModule {
}
