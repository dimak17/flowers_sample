import {NgModule} from '@angular/core';
import {RouterModule } from '@angular/router';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';

import { navbarRoute } from '../app.route';
import { errorRoute } from './';
import {companyInfoRoute} from './company-info/company-info.route';
import {cargoAgenciesRoute} from './cargo-agencies/cargo-agencies.route';

import {MissingTranslationHandler, TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {Http} from '@angular/http';
import {TranslationLoader} from '../shared/language/translation-loader';
import {MissingTranslationHandlerImpl} from '../shared/language/missing-translation-handler';
import {AvatarComponent} from './navbar/avatar/avatar.component';
import {UserInfoComponent} from './navbar/user-info/user-info.component';
import {LogoComponent} from './navbar/logo/logo.component';
import {UserActionsComponent} from './navbar/user-actions/user-actions.component';
import {WordsToUpperCasePipe} from '../shared/pipes/navbar-words-to-upper-case.pipe';
import {SplitByPipe} from './navbar/navbar-split-by.pipe';
import {FlowersSharedModule} from '../shared/shared.module';
import {profileInfoPopupRoute} from './navbar/profile-info/profile-info.route';
import {
    ImageDeletePopupComponent,
    ProfileInfoDeleteImageComponent
} from './navbar/profile-info/profile-info-delete-image.component';
import {priceListsRoute} from './price-lists/price-lists.route';
import {employeeInfoRoute} from './employee-info/employee-info.route';
import {airLinesInfoRoute} from './air-lines-info/air-lines-info.route';
import {DropdownModule} from 'primeng/primeng';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {policyInfoRoute} from './policy-info/policy-info.route';
import {seasonsPinchInfoRoute} from './seasons-pinch-info/seasons-pinch-info.route';
import {clientLabelInfoRoute} from './client-label-info/client-label-info.route';

export function createTranslateLoader(http: Http) {
    return new TranslationLoader(http, 'i18n', 'profile-info', '.json');
}

const LAYOUT_ROUTES = [
    cargoAgenciesRoute,
    companyInfoRoute,
    policyInfoRoute,
    airLinesInfoRoute,
    employeeInfoRoute,
    seasonsPinchInfoRoute,
    navbarRoute,
    priceListsRoute,
    clientLabelInfoRoute,
    ...errorRoute,
    ...profileInfoPopupRoute,
];

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        FormsModule,
        DropdownModule,
        BrowserAnimationsModule,
        FlowersSharedModule,
        TranslateModule.forRoot({
            loader: {provide: TranslationLoader, useFactory: (createTranslateLoader), deps: [Http]},
            missingTranslationHandler: {provide: MissingTranslationHandler, useClass: MissingTranslationHandlerImpl},
            isolate: true
        }),
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true })
    ],

    declarations: [
        SplitByPipe,
        WordsToUpperCasePipe,
        AvatarComponent,
        UserInfoComponent,
        LogoComponent,
        UserActionsComponent,
        ProfileInfoDeleteImageComponent,
        ImageDeletePopupComponent,
    ],
    exports: [
        RouterModule,
        AvatarComponent,
        UserInfoComponent,
        LogoComponent,
        UserActionsComponent,

    ],
    entryComponents: [ProfileInfoDeleteImageComponent]
})
export class LayoutRoutingModule {}
