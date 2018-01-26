import {TranslateLoader} from '@ngx-translate/core';
import {Observable} from 'rxjs';
import {Http} from '@angular/http';

/**
 * Created by platon on 29.03.17.
 */
export class EmployeeLoader implements TranslateLoader {

    constructor(
        private http: Http,
        private prefix: string,
        private entity: string,
        private suffix: string
    ) { }

    getTranslation(lang: string): Observable<any> {
        return this.http.get('/' + this.prefix + '/' + lang + '/' + this.entity + this.suffix)
            .map(function(res) { return res.json(); });
    }
}
