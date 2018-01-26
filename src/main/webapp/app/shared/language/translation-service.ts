import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Http} from '@angular/http';
import {OnLangChange} from '../../shared/language/language.helper';
import {EventManager} from 'ng-jhipster';
import {TranslateService} from '@ngx-translate/core';

@Injectable()
export class TranslationService implements OnLangChange {

    private language = '';

    constructor(private http: Http,
                private translateService: TranslateService,
                private eventManager: EventManager) {
    }

    getTranslation(lang, namePath): Observable<any> {
        return this.http.get('i18n/' + lang + '/' + namePath + '.json')
            .map((response) => response.json()['flowersApp'][namePath]);
    }

    onLangChange(lang: string) {

        if (this.language !== lang) {
            this.eventManager.broadcast({name: 'languageChanged', content: 'OK'});
            this.language = lang;

        }

    }

}
