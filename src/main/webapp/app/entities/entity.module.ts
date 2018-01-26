import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FlowersBrandModule } from './brand/brand.module';
import { FlowersCompanyModule } from './company/company.module';
import { FlowersContactModule } from './contact/contact.module';
import { FlowersCompanyUserModule } from './company-user/company-user.module';
import { FlowersDiseaseModule } from './disease/disease.module';
import { FlowersPositionModule } from './position/position.module';
import { FlowersBlockModule } from './block/block.module';
import { FlowersBoxTypeGroupModule } from './box-type-group/box-type-group.module';
import { FlowersBoxGroupModule } from './box-group/box-group.module';
import { FlowersMarketVarietyModule } from './market-variety/market-variety.module';
import { FlowersMarketBoxModule } from './market-box/market-box.module';
import { FlowersPriceListModule } from './price-list/price-list.module';
import { FlowersMarketVarietyPropertyModule } from './market-variety-property/market-variety-property.module';
import { FlowersTypeOfFlowerModule } from './type-of-flower/type-of-flower.module';
import { FlowersPinchVarietyModule } from './pinch-variety/pinch-variety.module';
import { FlowersPinchVarietyPropertyModule } from './pinch-variety-property/pinch-variety-property.module';
import {FlowersMarketModule} from './market/market.module';
import {FlowersMarketSeasonModule} from './market-season/market-season.module';
import { FlowersMarketSeasonVarietyPropertyModule } from './market-season-variety-property/market-season-variety-property.module';
import { FlowersLabelCountryModule } from './label-country/label-country.module';
import { FlowersMarketClientModule } from './market-client/market-client.module';
import { FlowersClientPaymentPolicyModule } from './client-payment-policy/client-payment-policy.module';
import {FlowersPaymentPolicyModule} from './payment-policy/payment-policy.module';
import { FlowersMarketBoxVarietyPropertyModule } from './market-box-variety-property/market-box-variety-property.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FlowersBrandModule,
        FlowersCompanyModule,
        FlowersContactModule,
        FlowersCompanyUserModule,
        FlowersDiseaseModule,
        FlowersPositionModule,
        FlowersBlockModule,
        FlowersBoxTypeGroupModule,
        FlowersBoxGroupModule,
        FlowersMarketModule,
        FlowersMarketVarietyModule,
        FlowersMarketBoxModule,
        FlowersPriceListModule,
        FlowersMarketVarietyPropertyModule,
        FlowersTypeOfFlowerModule,
        FlowersPinchVarietyModule,
        FlowersPinchVarietyPropertyModule,
        FlowersMarketSeasonModule,
        FlowersMarketSeasonVarietyPropertyModule,
        FlowersLabelCountryModule,
        FlowersMarketClientModule,
        FlowersClientPaymentPolicyModule,
        FlowersPaymentPolicyModule,
        FlowersMarketBoxVarietyPropertyModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    exports: [
        FlowersPriceListModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersEntityModule {}
