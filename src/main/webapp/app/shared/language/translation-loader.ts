import {TranslateLoader} from '@ngx-translate/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
export class TranslationLoader implements TranslateLoader {
    constructor(
        private http: Http,
        private prefix: string,
        private entity: string,
        private suffix: string
    ) {
    }

    getTranslation(lang: string): Observable<any> {
        return this.http.get('/' + this.prefix + '/' + lang + '/' + this.entity + this.suffix)
            .map(function(res) { return res.json(); });
    }
}
