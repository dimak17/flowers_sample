import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FlowersSharedModule } from '../../shared';
import {
    TypeOfFlowerService,
    TypeOfFlowerPopupService,
    TypeOfFlowerComponent,
    TypeOfFlowerDetailComponent,
    TypeOfFlowerDialogComponent,
    TypeOfFlowerPopupComponent,
    TypeOfFlowerDeletePopupComponent,
    TypeOfFlowerDeleteDialogComponent,
    typeOfFlowerRoute,
    typeOfFlowerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...typeOfFlowerRoute,
    ...typeOfFlowerPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TypeOfFlowerComponent,
        TypeOfFlowerDetailComponent,
        TypeOfFlowerDialogComponent,
        TypeOfFlowerDeleteDialogComponent,
        TypeOfFlowerPopupComponent,
        TypeOfFlowerDeletePopupComponent,
    ],
    entryComponents: [
        TypeOfFlowerComponent,
        TypeOfFlowerDialogComponent,
        TypeOfFlowerPopupComponent,
        TypeOfFlowerDeleteDialogComponent,
        TypeOfFlowerDeletePopupComponent,
    ],
    providers: [
        TypeOfFlowerService,
        TypeOfFlowerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersTypeOfFlowerModule {}
