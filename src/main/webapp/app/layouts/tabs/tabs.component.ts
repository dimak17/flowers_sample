import {Component} from '@angular/core';
import {NavbarComponent} from '../navbar/navbar.component';

@Component({
    selector: 'jhi-flowers-tabs',
    templateUrl: './tabs.component.html',
    styleUrls: ['tab.scss']
})
export class TabsComponent{
    collapseNavleftbar() {
        return NavbarComponent.isNavleftbarCollapsed;
    }
}
