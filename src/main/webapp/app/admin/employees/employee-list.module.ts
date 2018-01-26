/**
 * Created by dima on 13.07.17.
 */
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {employeeListPopupRoute, EmployeeListResolvePagingParams, employeeListRoute} from './employee-list.route';
import {EmployeeListComponent} from './employee-list.component';
import {EmployeeListDialogComponent, EmployeeListPopupComponent} from './employee-list-dialog.component';
import {EmployeeListService} from './employee-list.service';
import {EmployeeListPopupService} from './employee-list-popup.service';
import {Ng2TableModule} from 'ng2-table';
import {PaginationModule} from 'ngx-bootstrap';
import {CheckboxModule, ButtonModule, InputSwitchModule, MultiSelectModule} from 'primeng/primeng';
import {EmployeeListDialogOnLangChangeService} from './employee-list-dialog-on-lang-change.service';
import {MissingTranslationHandler, TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {Http} from '@angular/http';
import {EmployeeListOnLangChangeService} from './employee-list-on-lang-change.service';
import {PositionsTranslateService} from './employee-list-positions-translate.service';
import {TranslationLoader} from '../../shared/language/translation-loader';
import {MissingTranslationHandlerImpl} from '../../shared/language/missing-translation-handler';
import {TranslationService} from '../../shared/language/translation-service';

const ENTITY_STATES = [
    ...employeeListRoute,
    ...employeeListPopupRoute,
];

export function createEmployeeTranslateLoader(http: Http) {
    return new TranslationLoader(http, 'i18n', 'companyUser', '.json');
}

@NgModule({
    imports: [
        TranslateModule.forRoot({
            loader: {provide: TranslateLoader, useFactory: (createEmployeeTranslateLoader), deps: [Http], },
            missingTranslationHandler: {provide: MissingTranslationHandler, useClass: MissingTranslationHandlerImpl},
            isolate: true
        }),
        FlowersSharedModule,
        Ng2TableModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        PaginationModule.forRoot(),
        CheckboxModule,
        ButtonModule,
        InputSwitchModule,
        MultiSelectModule,
    ],
    declarations: [
        EmployeeListComponent,
        EmployeeListDialogComponent,
        EmployeeListPopupComponent,
    ],
    entryComponents: [
        EmployeeListComponent,
        EmployeeListDialogComponent,
        EmployeeListPopupComponent,
    ],
    providers: [
        EmployeeListService,
        EmployeeListPopupService,
        EmployeeListResolvePagingParams,
        EmployeeListDialogOnLangChangeService,
        EmployeeListOnLangChangeService,
        PositionsTranslateService,
        TranslationService
    ],
    exports: [
        EmployeeListComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersEmployeeListModule {}
