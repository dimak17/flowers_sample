import {Component, OnInit} from '@angular/core';
import {NavleftbarService} from './navleftbar.service';
import {Navleftbar} from './navleftbar';
import {NavbarComponent} from '../navbar/navbar.component';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {Subscription} from 'rxjs/Subscription';

@Component({
    selector: 'jhi-flowers-navleftbar',
    templateUrl: './navleftbar.component.html',
    providers: [NavleftbarService],
    styleUrls: [
        'navleftbar.scss'
    ]
})
export class NavleftbarComponent implements OnInit {
    private rows: Navleftbar[];
    private languageSubscriber: Subscription;

    constructor(private navleftbarService: NavleftbarService,
                private jhiLanguageService: JhiLanguageService,
                private eventManager: EventManager, ) {
        this.jhiLanguageService.setLocations(['navleftbar']);
    }

    ngOnInit(): void {

        this.registerLangChange();
        this.getMenuData();
    }

    collapseNavleftbar() {
        return NavbarComponent.isNavleftbarCollapsed;
    }

    getMenuData() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.navleftbarService.onLangChange(currentLang);
            this.navleftbarService.getRows(currentLang).subscribe((rows) => {
                this.rows = rows;
            });
        });
    }

    registerLangChange() {
        this.languageSubscriber = this.eventManager.subscribe('languageChanged', (response) => {
        this.getMenuData();
        });
    }

}
