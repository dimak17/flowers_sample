import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';
import {FlowersSharedModule} from '../../shared';
import {
    BankDetailsService,
    BankDetailsPopupService,
    BankDetailsComponent,
    BankDetailsDialogComponent,
    BankDetailsPopupComponent,
    bankDetailsPopupRoute,

} from './';
import {bankDetailsReportRoute, bankDetailsRoute} from './bank-details.route';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
    BankDetailsDialogComponentPreview,
    BankDetailsPopupComponentPreview
} from './bank-details-preview-dialog.component';
import {BrowserModule} from '@angular/platform-browser';
import {PdfViewerComponent} from 'ng2-pdf-viewer';
import {CommonModule} from '@angular/common';
import {
    ButtonModule, CheckboxModule, CodeHighlighterModule, FileUploadModule, GrowlModule, TooltipModule,
    ConfirmDialogModule, ConfirmationService
} from 'primeng/primeng';
import {HttpModule} from '@angular/http';
import {MessagesModule} from 'primeng/primeng';
import {TabViewModule} from 'primeng/primeng';
import {
    BankDetailsDeleteDialogComponent,
    BankDetailsDeletePopupComponent
} from './bank-details-delete-dialog.component';

const ENTITY_STATES = [
    ...bankDetailsRoute,
    ...bankDetailsReportRoute,
    ...bankDetailsPopupRoute,
];

@NgModule({
    imports: [
        FlowersSharedModule,
        RouterModule.forRoot(ENTITY_STATES, {useHash: true}),
        ReactiveFormsModule,
        BrowserModule,
        CommonModule,
        FileUploadModule,
        GrowlModule,
        ButtonModule,
        CodeHighlighterModule,
        FormsModule,
        HttpModule,
        MessagesModule,
        TabViewModule,
        CheckboxModule,
        TooltipModule,
        ConfirmDialogModule
    ],
    declarations: [
        BankDetailsComponent,
        BankDetailsDialogComponent,
        BankDetailsDialogComponentPreview,
        BankDetailsPopupComponent,
        BankDetailsPopupComponentPreview,
        PdfViewerComponent,
        BankDetailsDeletePopupComponent,
        BankDetailsDeleteDialogComponent
    ],
    entryComponents: [
        BankDetailsComponent,
        BankDetailsDialogComponent,
        BankDetailsDialogComponentPreview,
        BankDetailsPopupComponent,
        BankDetailsPopupComponentPreview,
        BankDetailsDeletePopupComponent,
        BankDetailsDeleteDialogComponent
    ],
    providers: [
        BankDetailsService,
        BankDetailsPopupService,
        ConfirmationService
    ],
    exports: [
        BankDetailsComponent,
        RouterModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FlowersBankDetailsModule {
}
