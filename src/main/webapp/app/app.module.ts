import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { FlowersSharedModule, UserRouteAccessService } from './shared';
import { FlowersHomeModule } from './home/home.module';
import { FlowersAccountModule } from './account/account.module';
import { FlowersEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import { FlowersCompanyConfigurationModule} from './company-configuration/company-configuration.module';
import {
    ActiveMenuDirective,
    ErrorComponent,
    FooterComponent,
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    ProfileInfoComponent,
    PageRibbonComponent,
    NavleftbarComponent,
    TabsComponent,
    ProfileService,
    CargoAgenciesComponent,
    CompanyInfoComponent,
    PriceListsComponent,
    PolicyInfoComponent
} from './layouts';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {FirstUpperCasePipe} from './shared/pipes/navbar-first-letter-to-upper-case.pipe';
import {FlowersAdminModule} from './admin/admin.module';
import {AirLinesInfoComponent} from './layouts/air-lines-info/air-lines.component-info';
import {EmployeeInfoComponent} from './layouts/employee-info/employee-info.component';
import {CheckboxModule, TabViewModule} from 'primeng/primeng';
import {SeasonsPinchInfoComponent} from './layouts/seasons-pinch-info/seasons-pinch-info.component';
import {ClientLabelInfoComponent} from './layouts/client-label-info/client-label-info.component';

@NgModule({
    imports: [
        BrowserModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        TabViewModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        FlowersSharedModule,
        FlowersHomeModule,
        FlowersAdminModule,
        FlowersAccountModule,
        FlowersEntityModule,
        FlowersCompanyConfigurationModule,
        CheckboxModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ProfileInfoComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        NavleftbarComponent,
        FooterComponent,
        TabsComponent,
        CompanyInfoComponent,
        CargoAgenciesComponent,
        PolicyInfoComponent,
        AirLinesInfoComponent,
        EmployeeInfoComponent,
        SeasonsPinchInfoComponent,
        PriceListsComponent,
        ClientLabelInfoComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class FlowersAppModule {}
