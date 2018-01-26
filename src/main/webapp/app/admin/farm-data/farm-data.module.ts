import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {FarmDataComponent} from './farm-data.component';
import {LogoDeleteImageComponent, LogoDeletePopupComponent} from './logo-delete-image.component';
import {FarmDataService} from './farm-data.service';
import {FarmDataPopupService} from './farm-data-popup.service';
import {farmDataPopupRoute, farmDataRoute} from './farm-data.route';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {GrowlModule, MultiSelectModule} from 'primeng/primeng';
import {SelectModule} from 'ng2-select';

const ENTITY_STATES = [
    ...farmDataRoute,
    ...farmDataPopupRoute
];

@NgModule({
    imports: [
        GrowlModule,
        FormsModule,
        FlowersSharedModule,
        ReactiveFormsModule,
        MultiSelectModule,
        SelectModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FarmDataComponent,
        LogoDeleteImageComponent,
        LogoDeletePopupComponent
    ],
    entryComponents: [
        FarmDataComponent,
        LogoDeleteImageComponent,
        LogoDeletePopupComponent
    ],
    providers: [
        FarmDataService,
        FarmDataPopupService
    ],
    exports: [
        FarmDataComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersFarmDataModule {}
