import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FlowersSharedModule} from '../shared';
import {
    adminState,
    AuditsComponent,
    CompanyCreatorEmailComponent,
    CompanyCreatorEmailService,
    JhiConfigurationComponent,
    JhiConfigurationService,
    JhiHealthCheckComponent,
    JhiHealthModalComponent,
    JhiHealthService,
    JhiMetricsMonitoringComponent,
    JhiMetricsMonitoringModalComponent,
    JhiMetricsService,
    LogsComponent,
    LogsService,
    UserDeleteDialogComponent,
    UserDialogComponent,
    UserMgmtComponent,
    UserMgmtDeleteDialogComponent,
    UserMgmtDetailComponent,
    UserMgmtDialogComponent,
    UserModalService,
    UserResolve,
    UserResolvePagingParams,
} from './';

import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FlowersMixTypeModule} from './mix-type/mix-type.module';
import {FlowersCargoAgencyModule} from './cargo-agency/cargo-agency.module';
import {FlowersCargoEmployeeModule} from './cargo-employee/cargo-employee.module';
import {FlowersCargoEmployeePositionModule} from './cargo-employee-position/cargo-employee-position.module';
import {BoxGroupingDeleteDialogComponent} from './box-grouping/box-grouping-delete-dialog.component';
import {FlowersMarketModule} from './market/market.module';
import {FlowersAirLinesModule} from './air-lines/air-lines.module';
import {FlowersBoxGroupingModule} from './box-grouping/box-grouping.module';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */
import {FlowersEmployeeListModule} from './employees/employee-list.module';
import {FlowersBoxTypeModule} from './box-type/box-type.module';
import {BoxGroupingComponent} from './box-grouping/box-grouping.component';
import {JhiDocsComponent} from './docs/docs.component';
import {AuditsService} from './audits/audits.service';
import {FlowersBlockModule} from './blocks/block.module';
import {FlowersVarietyModule} from './variety/variety.module';
import {FlowersPriceListModule} from './price-list/price-list.module';
import {FlowersPinchModule} from './pinch/pinch.module';
import {FlowersSeasonModule} from './season/season.module';
import {FlowersFarmDataModule} from './farm-data/farm-data.module';
import {FlowersPaymentPolicyModule} from './payment-policy/payment-policy.module';
import {FlowersClaimsPolicyModule} from './claims-policy/claims-policy.module';
import {FlowersShippingPolicyModule} from './shipping-policy/shipping-policy.module';
import {FlowersClientModule} from './client/client.module';
import {FlowersLabelModule} from './label/label.module';
import {FlowersDestinationModule} from './destination/destination.module';
import {FlowersCountryModule} from './country/country.module';
import {FlowersClientEmployeeModule} from './client-employee/client-employee.module';
import {FlowersClientEmployeePositionModule} from './client-employee-position/client-employee-position.module';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */
import {MultiSelectModule} from 'primeng/primeng';

import {FlowersBankDetailsModule} from './bank-details/bank-details.module';
import {SelectModule} from 'ng2-select';
@NgModule({
    imports: [
        MultiSelectModule,
        FlowersFarmDataModule,
        FlowersCargoAgencyModule,
        FlowersMixTypeModule,
        SelectModule,
        CommonModule,
        FormsModule,
        FlowersMixTypeModule,
        ReactiveFormsModule,
        FlowersSharedModule,
        FlowersPriceListModule,
        FlowersBlockModule,
        FlowersBankDetailsModule,
        FlowersAirLinesModule,
        FlowersBoxGroupingModule,
        FlowersEmployeeListModule,
        FlowersBoxTypeModule,
        FlowersBlockModule,
        FlowersBoxTypeModule,
        FlowersVarietyModule,
        FlowersMarketModule,
        FlowersPinchModule,
        FlowersSeasonModule,
        FlowersPaymentPolicyModule,
        FlowersClaimsPolicyModule,
        FlowersShippingPolicyModule,
        FlowersClientModule,
        FlowersLabelModule,
        FlowersDestinationModule,
        FlowersCountryModule,
        FlowersClientEmployeeModule,
        FlowersClientEmployeePositionModule,
        RouterModule.forRoot(adminState, { useHash: true }),
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [
        AuditsComponent,
        UserMgmtComponent,
        UserDialogComponent,
        UserDeleteDialogComponent,
        UserMgmtDetailComponent,
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        CompanyCreatorEmailComponent,
        LogsComponent,
        JhiConfigurationComponent,
        JhiHealthCheckComponent,
        JhiHealthModalComponent,
        JhiDocsComponent,
        JhiMetricsMonitoringComponent,
        JhiMetricsMonitoringModalComponent,
    ],
    entryComponents: [
        BoxGroupingDeleteDialogComponent,
        UserMgmtDialogComponent,
        UserMgmtDeleteDialogComponent,
        JhiHealthModalComponent,
        JhiMetricsMonitoringModalComponent,
    ],
    providers: [
        AuditsService,
        JhiConfigurationService,
        CompanyCreatorEmailService,
        JhiHealthService,
        JhiMetricsService,
        LogsService,
        UserResolvePagingParams,
        UserResolve,
        UserModalService
    ],
    exports: [
        FlowersAirLinesModule,
        BoxGroupingComponent,
        FlowersMixTypeModule,
        FlowersCargoAgencyModule,
        FlowersCargoEmployeeModule,
        FlowersCargoEmployeePositionModule,
        FlowersBlockModule,
        FlowersBankDetailsModule,
        FlowersPriceListModule,
        FlowersEmployeeListModule,
        FlowersBoxTypeModule,
        FlowersPinchModule,
        FlowersSeasonModule,
        FlowersFarmDataModule,
        FlowersPaymentPolicyModule,
        FlowersClaimsPolicyModule,
        FlowersShippingPolicyModule,
        FlowersClientModule,
        FlowersLabelModule,
        FlowersDestinationModule,
        FlowersCountryModule,
        FlowersClientEmployeeModule,
        FlowersClientEmployeePositionModule,
        FlowersVarietyModule,
        FlowersMarketModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersAdminModule {}
