import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTableModule, SharedModule } from 'primeng/primeng';
import { FlowersSharedModule } from '../../shared';
import { CargoEmployeePositionService } from './cargo-employee-position.service';
import { CargoEmployeePositionPopupService } from './cargo-employee-position-popup.service';
import { CargoEmployeePositionComponent } from './cargo-employee-position.component';
import { CargoEmployeePositionDialogComponent, CargoEmployeePositionPopupComponent } from './cargo-employee-position-dialog.component';
import { CargoEmployeePositionDeleteDialogComponent, CargoEmployeePositionDeletePopupComponent } from './cargo-employee-position-delete-dialog.component';
import { cargoEmployeePositionPopupRoute, cargoEmployeePositionRoute } from './cargo-employee-position.route';

const ENTITY_STATES = [
    ...cargoEmployeePositionRoute,
    ...cargoEmployeePositionPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        DataTableModule,
        SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CargoEmployeePositionComponent,
        CargoEmployeePositionDialogComponent,
        CargoEmployeePositionDeleteDialogComponent,
        CargoEmployeePositionPopupComponent,
        CargoEmployeePositionDeletePopupComponent,
    ],
    entryComponents: [
        CargoEmployeePositionComponent,
        CargoEmployeePositionDialogComponent,
        CargoEmployeePositionPopupComponent,
        CargoEmployeePositionDeleteDialogComponent,
        CargoEmployeePositionDeletePopupComponent,
    ],
    providers: [
        CargoEmployeePositionService,
        CargoEmployeePositionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [CargoEmployeePositionComponent]
})
export class FlowersCargoEmployeePositionModule {}
