import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';
import {BreakWordPipe} from './pipes/break-word.pipe';

import { CookieService } from 'angular2-cookie/services/cookies.service';
import {
    FlowersSharedLibsModule,
    FlowersSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    HasAnyAuthorityDirective,
    JhiLoginModalComponent,
    ClipboardService,
    ClickOutsideDirective,
    DropdownArrowChangeDirective
} from './';
import {ReplaceWithNbsp} from './pipes/replace-with-nbsp.pipe';
import {FirstUpperCasePipe} from './pipes/navbar-first-letter-to-upper-case.pipe';
import {CheckboxModule} from 'primeng/primeng';

@NgModule({
    imports: [
        FlowersSharedLibsModule,
        FlowersSharedCommonModule,
        CheckboxModule
    ],
    declarations: [
        FirstUpperCasePipe,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        ClickOutsideDirective,
        DropdownArrowChangeDirective,
        HasAnyAuthorityDirective,
        BreakWordPipe,
        ReplaceWithNbsp,
    ],
    providers: [
        CookieService,
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe,
        ClipboardService
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        FirstUpperCasePipe,
        FlowersSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe,
        ClickOutsideDirective,
        DropdownArrowChangeDirective,
        DatePipe,
        BreakWordPipe,
        ReplaceWithNbsp,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class FlowersSharedModule {}
