import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DataTableModule, DropdownModule, MultiSelectModule, SharedModule } from 'primeng/primeng';
import { FlowersSharedModule } from '../../shared';
import { CargoEmployeeService } from './cargo-employee.service';
import { CargoEmployeePopupService } from './cargo-employee-popup.service';
import { CargoEmployeeComponent } from './cargo-employee.component';
import { CargoEmployeeDialogComponent, CargoEmployeePopupComponent } from './cargo-employee-dialog.component';
import { CargoEmployeeDeleteDialogComponent, CargoEmployeeDeletePopupComponent } from './cargo-employee-delete-dialog.component';
import { cargoEmployeePopupRoute, cargoEmployeeRoute } from './cargo-employee.route';
import { InputMaskModule } from 'primeng/primeng';

const ENTITY_STATES = [
    ...cargoEmployeeRoute,
    ...cargoEmployeePopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        DropdownModule,
        DataTableModule,
        SharedModule,
        InputMaskModule,
        MultiSelectModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
    ],
    declarations: [
        CargoEmployeeComponent,
        CargoEmployeeDialogComponent,
        CargoEmployeeDeleteDialogComponent,
        CargoEmployeePopupComponent,
        CargoEmployeeDeletePopupComponent,
    ],
    entryComponents: [
        CargoEmployeeComponent,
        CargoEmployeeDialogComponent,
        CargoEmployeePopupComponent,
        CargoEmployeeDeleteDialogComponent,
        CargoEmployeeDeletePopupComponent,
    ],
    providers: [
        CargoEmployeeService,
        CargoEmployeePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [CargoEmployeeComponent]
})
export class FlowersCargoEmployeeModule {}
