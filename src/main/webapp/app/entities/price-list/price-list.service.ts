import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PriceList } from './price-list.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PriceListService {

    private resourceUrl = 'api/price-lists';

    constructor(private http: Http) { }

    create(priceList: PriceList): Observable<PriceList> {
        const copy = this.convert(priceList);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(priceList: PriceList): Observable<PriceList> {
        const copy = this.convert(priceList);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PriceList> {
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

    private convert(priceList: PriceList): PriceList {
        const copy: PriceList = Object.assign({}, priceList);
        return copy;
    }
}
