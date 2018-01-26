import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {countryPopupRoute, countryRoute} from './country.route';
import {CountryComponent} from './country.component';
import {CountryDialogComponent, CountryPopupComponent} from './country-dialog.component';
import {CountryDeleteDialogComponent, CountryDeletePopupComponent} from './country-delete-dialog.component';
import {CountryService} from './country.service';
import {CountryPopupService} from './country-popup.service';

const ENTITY_STATES = [
    ...countryRoute,
    ...countryPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CountryComponent,
        CountryDialogComponent,
        CountryDeleteDialogComponent,
        CountryPopupComponent,
        CountryDeletePopupComponent
    ],
    entryComponents: [
        CountryComponent,
        CountryDialogComponent,
        CountryPopupComponent,
        CountryDeleteDialogComponent,
        CountryDeletePopupComponent
    ],
    providers: [
        CountryService,
        CountryPopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersCountryModule {}
