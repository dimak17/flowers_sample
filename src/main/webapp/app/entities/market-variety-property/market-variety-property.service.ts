import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MarketVarietyProperty } from './market-variety-property.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MarketVarietyPropertyService {

    private resourceUrl = 'api/market-variety-properties';

    constructor(private http: Http) { }

    create(marketVarietyProperty: MarketVarietyProperty): Observable<MarketVarietyProperty> {
        const copy = this.convert(marketVarietyProperty);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(marketVarietyProperty: MarketVarietyProperty): Observable<MarketVarietyProperty> {
        const copy = this.convert(marketVarietyProperty);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MarketVarietyProperty> {
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

    private convert(marketVarietyProperty: MarketVarietyProperty): MarketVarietyProperty {
        const copy: MarketVarietyProperty = Object.assign({}, marketVarietyProperty);
        return copy;
    }
}
