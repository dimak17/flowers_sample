import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';

@Injectable()
export class UserActionService {
    constructor(
        private http: Http
    ) {
    }

    get(login: string): Observable<any> {
        return this.http.get('api/company-users/query?login=' + login).map((res: Response) => res.json());
    }
}
