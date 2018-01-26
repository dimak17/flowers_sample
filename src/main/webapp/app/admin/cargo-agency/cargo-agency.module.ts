import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTableModule, SharedModule } from 'primeng/primeng';
import { FlowersSharedModule } from '../../shared';
import { CargoAgencyService } from './cargo-agency.service';
import { CargoAgencyPopupService } from './cargo-agency-popup.service';
import { CargoAgencyComponent } from './cargo-agency.component';
import { CargoAgencyDialogComponent, CargoAgencyPopupComponent } from './cargo-agency-dialog.component';
import { CargoAgencyDeleteDialogComponent, CargoAgencyDeletePopupComponent } from './cargo-agency-delete-dialog.component';
import { cargoAgencyPopupRoute, cargoAgencyRoute } from './cargo-agency.route';
import { InputMaskModule } from 'primeng/primeng';

const ENTITY_STATES = [
    ...cargoAgencyRoute,
    ...cargoAgencyPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        DataTableModule,
        InputMaskModule,
        SharedModule
    ],
    declarations: [
        CargoAgencyComponent,
        CargoAgencyDialogComponent,
        CargoAgencyDeleteDialogComponent,
        CargoAgencyPopupComponent,
        CargoAgencyDeletePopupComponent,
    ],
    entryComponents: [
        CargoAgencyComponent,
        CargoAgencyDialogComponent,
        CargoAgencyPopupComponent,
        CargoAgencyDeleteDialogComponent,
        CargoAgencyDeletePopupComponent,
    ],
    providers: [
        CargoAgencyService,
        CargoAgencyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [CargoAgencyComponent]
})
export class FlowersCargoAgencyModule {}
