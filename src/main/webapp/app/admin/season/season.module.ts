import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {seasonPopupRoute, seasonRoute} from './season.route';
import {SeasonComponent} from './season.component';
import {SeasonDialogComponent, SeasonPopupComponent} from './season-dialog.component';
import {SeasonDeleteDialogComponent, SeasonDeletePopupComponent} from './season-delete-dialog.component';
import {SeasonService} from './season.service';
import {SeasonPopupService} from './season-popup.service';
import {DataTableModule, SharedModule, MessagesModule, CalendarModule, MultiSelectModule, InputMaskModule} from 'primeng/primeng';
import {TranslationLoader} from '../../shared/language/translation-loader';
import {Http} from '@angular/http';
import {MissingTranslationHandler, TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {MissingTranslationHandlerImpl} from '../../shared/language/missing-translation-handler';

const ENTITY_STATES = [
    ...seasonRoute,
    ...seasonPopupRoute,
];

export function createSeasonTranslateLoader(http: Http) {
    return new TranslationLoader(http, 'i18n', 'season', '.json');
}

@NgModule({
    imports: [
        TranslateModule.forRoot({
            loader: {provide: TranslateLoader, useFactory: (createSeasonTranslateLoader), deps: [Http], },
            missingTranslationHandler: {provide: MissingTranslationHandler, useClass: MissingTranslationHandlerImpl},
            isolate: true
        }),
        DataTableModule,
        MessagesModule,
        SharedModule,
        CalendarModule,
        FlowersSharedModule,
        MultiSelectModule,
        InputMaskModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SeasonComponent,
        SeasonDialogComponent,
        SeasonDeleteDialogComponent,
        SeasonPopupComponent,
        SeasonDeletePopupComponent,
    ],
    entryComponents: [
        SeasonComponent,
        SeasonDialogComponent,
        SeasonPopupComponent,
        SeasonDeleteDialogComponent,
        SeasonDeletePopupComponent,
    ],
    providers: [
        SeasonService,
        SeasonPopupService
    ],
    exports: [
        SeasonComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersSeasonModule {}
