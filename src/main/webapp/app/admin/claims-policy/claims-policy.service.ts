import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import {Http, RequestOptions, Response} from '@angular/http';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {ClaimsPolicyUi} from './claimsui-policy.model';
import {ClaimsPolicy} from '../../entities/claims-policy/claims-policy.model';

@Injectable()
export class ClaimsPolicyService {

    private resourceUrl = 'api/claims-policies';

    options: RequestOptions;

    constructor(private http: Http) { }

    create(claimsPolicy: ClaimsPolicy): Observable<ClaimsPolicy> {
        const copy = this.convert(claimsPolicy);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(claimsPolicy: ClaimsPolicy): Observable<ClaimsPolicy> {
        const copy = this.convert(claimsPolicy);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ClaimsPolicyUi> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllClaimsPolicies(): Observable<ClaimsPolicyUi[]> {
        return this.http.get(this.resourceUrl.concat('/company')
        ).map((response) => response.json());
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(claimsPolicy: ClaimsPolicy): ClaimsPolicy {
        const copy: ClaimsPolicy = Object.assign({}, claimsPolicy);
        return copy;
    }
}
