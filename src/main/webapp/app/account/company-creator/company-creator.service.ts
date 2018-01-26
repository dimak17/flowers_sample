import {Injectable} from '@angular/core';
import {Http, Response, URLSearchParams} from '@angular/http';
import {CompanyCreator} from './company-creator.model';
import {Observable} from 'rxjs/Rx';
import ResponseUtil from '../../util/response-util';
/**
 * Created by platon on 08.03.17.
 */

@Injectable()
export class CompanyCreatorService {
    private resourceUrl = 'api/company-creator';
    private isKeyActiveUrl = 'api/company-creator-page';

    constructor(private http: Http) { }

    create(companyCreator: CompanyCreator): Observable<CompanyCreator> {
        const copy: CompanyCreator = Object.assign({}, companyCreator);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return ResponseUtil.toJson(res);
        });
    }

    isActiveKey(key: string): Observable<string> {
        const params: URLSearchParams = new URLSearchParams();
        params.set('key', key);
        return this.http.get(this.isKeyActiveUrl, {
            search: params
        }).map((res) => ResponseUtil.toJson(res));
    }

}
