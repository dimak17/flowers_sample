import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Navleftbar} from './navleftbar';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';
import {OnLangChange} from '../../shared/language/language.helper';
import {EventManager} from 'ng-jhipster';
import {TranslateService} from '@ngx-translate/core';

@Injectable()
export class NavleftbarService implements OnLangChange {

    private language = '';

    constructor(private http: Http,
                private translateService: TranslateService,
                private eventManager: EventManager) {
    }

    getRows(lang): Observable<Navleftbar[]> {
        return this.http.get('i18n/' + lang + '/navleftbar.json')
                .map((response) => response.json()['navleftbar']);
    }

    onLangChange(lang: string) {

        if (this.language !== lang) {
            this.eventManager.broadcast({name: 'languageChanged', content: 'OK'});
            this.language = lang;

        }

    }
}
