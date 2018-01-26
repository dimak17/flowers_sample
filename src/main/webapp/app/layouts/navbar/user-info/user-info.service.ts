import {Observable} from 'rxjs/Observable';
import {Http, Response} from '@angular/http';
import {Injectable} from '@angular/core';

@Injectable()
export class UserInfoService {

    private resourceUrl = 'api/company-users/current';

    constructor(private http: Http) {}

    get(): Observable<any> {
        return this.http.get(this.resourceUrl).map((res: Response) => res.json());
    }
}
