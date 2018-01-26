import {Component, ElementRef, OnDestroy, OnInit, Renderer2, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {EventManager, JhiLanguageService} from 'ng-jhipster';

import {ProfileService} from '../profiles/profile.service';
import {JhiLanguageHelper, LoginModalService, LoginService, Principal} from '../../shared';
import {VERSION} from '../../app.constants';
import {CompanyUser} from '../../entities/company-user/company-user.model';
import {Subscription} from 'rxjs/Subscription';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html'
})
export class NavbarComponent implements OnInit, OnDestroy {
    static isNavleftbarCollapsed: boolean;

    companyUser: CompanyUser;
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    showProfileInfo: boolean;
    innerWidth: number;
    isNavleftbarHide: boolean;
    accountEmailLogoutEvent: Subscription;
    @ViewChild('settings') settings: ElementRef;

    constructor(private loginService: LoginService,
                private languageHelper: JhiLanguageHelper,
                private languageService: JhiLanguageService,
                private principal: Principal,
                private eventManager: EventManager,
                private loginModalService: LoginModalService,
                private profileService: ProfileService,
                private router: Router,
                private rd: Renderer2) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
        this.languageService.addLocation('home');
        NavbarComponent.isNavleftbarCollapsed = false;

        window.onresize = () => {
            this.innerWidth = window.innerWidth;
            if (1250 > this.innerWidth && !this.isNavleftbarHide) {
                NavbarComponent.isNavleftbarCollapsed = true;
                this.isNavleftbarHide = true;
            } else if (1250 < this.innerWidth && this.isNavleftbarHide) {
                NavbarComponent.isNavleftbarCollapsed = false;
                this.isNavleftbarHide = false;
            }
        };
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
        this.registerEvents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.accountEmailLogoutEvent);
    }

    registerEvents() {
        this.accountEmailLogoutEvent = this.eventManager.subscribe('changedAccountEmail', (response) => this.logout());
    }

    changeLanguage(languageKey: string) {
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleProfileInfo() {
        this.showProfileInfo = !this.showProfileInfo;
        return this.showProfileInfo;
    }

    getProfileInfoToggledClass() {
        return {
            'show': this.showProfileInfo,
            'hide': !this.showProfileInfo,
        };
    }

    receiveProfileInfoChanges(companyUser) {
        this.companyUser = companyUser;
        this.showProfileInfo = false;
    }

    authDataChanged(event: boolean) {
        this.showProfileInfo = event;
        this.logout();
    }

    toggleNavleftbar() {
        NavbarComponent.isNavleftbarCollapsed = !NavbarComponent.isNavleftbarCollapsed;
    }

    collapseNavleftbar() {
        return NavbarComponent.isNavleftbarCollapsed;
    }

    onClickOutsideProfileInfo(value) {
        this.showProfileInfo = value;
    }

    mouseEnterSettings() {
        this.rd.addClass(this.settings.nativeElement, 'fa-spin');
    }

    mouseLeaveSettings() {
        this.rd.removeClass(this.settings.nativeElement, 'fa-spin');
    }
}
