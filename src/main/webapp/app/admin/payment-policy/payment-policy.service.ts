import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {createRequestOption, ResponseWrapper} from '../../shared';
import {PaymentPolicy} from '../../entities/payment-policy/payment-policy.model';

@Injectable()
export class PaymentPolicyService {

    private resourceUrl = 'api/payment-policies';

    constructor(private http: Http) { }

    create(paymentPolicy: PaymentPolicy): Observable<PaymentPolicy> {
        const copy = this.convert(paymentPolicy);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(paymentPolicy: PaymentPolicy): Observable<PaymentPolicy> {
        const copy = this.convert(paymentPolicy);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PaymentPolicy> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(paymentPolicy: PaymentPolicy): PaymentPolicy {
        const copy: PaymentPolicy = Object.assign({}, paymentPolicy);
        return copy;
    }
}
