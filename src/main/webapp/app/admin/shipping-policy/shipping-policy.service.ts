import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ShippingPolicy } from './shipping-policy.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {ShippingPolicyUi} from './shippingui-policy.model';

@Injectable()
export class ShippingPolicyService {

    private resourceUrl = 'api/shipping-policies';

    constructor(private http: Http) { }

    create(shippingPolicy: ShippingPolicy): Observable<ShippingPolicy> {
        const copy = this.convert(shippingPolicy);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(shippingPolicy: ShippingPolicy): Observable<ShippingPolicy> {
        const copy = this.convert(shippingPolicy);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ShippingPolicy> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllShippingPolicies(): Observable<ShippingPolicyUi[]> {
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

    private convert(shippingPolicy: ShippingPolicy): ShippingPolicy {
        const copy: ShippingPolicy = Object.assign({}, shippingPolicy);
        return copy;
    }
}
