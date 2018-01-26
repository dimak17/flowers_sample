import { Injectable } from '@angular/core';
import {Http, RequestOptions, Response, Headers} from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { BoxTypeUi } from './box-type-ui.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {Company} from '../../entities/company/company.model';
import ResponseUtil from '../../util/response-util';

@Injectable()
export class BoxTypeService {

    private resourceUrl = 'api/box-types';
    private resourceCompanyUrl = 'api/companies';

    headers: Headers;
    options: RequestOptions;

    constructor(private http: Http) {
        this.headers = new Headers({ 'Content-Type': 'application/json'});
        this.options = new RequestOptions({ headers: this.headers });
    }

    create(boxType: BoxTypeUi): Observable<BoxTypeUi> {
        const copy = this.convert(boxType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(boxType: BoxTypeUi): Observable<BoxTypeUi> {
        const copy = this.convert(boxType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<BoxTypeUi> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllBoxTypes(): Observable<BoxTypeUi[]> {
        return this.http.get(this.resourceUrl.concat('/company')
        ).map((response) => response.json());
    }

    getCompany(id: number): Observable<Company> {
        return this.http.get(`${this.resourceCompanyUrl}/${id}`, this.options).map((response) => response.json());
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`)
            .map((res) => {
            return ResponseUtil.toJson(res);
            })
            .catch((error) => {
                return Observable.throw(error);
            });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(boxType: BoxTypeUi): BoxTypeUi {
        const copy: BoxTypeUi = Object.assign({}, boxType);
        return copy;
    }

    private extractData(res: Response) {
        const body = res.json();
        return body || {};
    }

    private handleError(error: any) {
        return error;
    }
}
