import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MarketSeasonVarietyProperty } from './market-season-variety-property.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MarketSeasonVarietyPropertyService {

    private resourceUrl = 'api/market-season-variety-properties';

    constructor(private http: Http) { }

    create(marketSeasonVarietyProperty: MarketSeasonVarietyProperty): Observable<MarketSeasonVarietyProperty> {
        const copy = this.convert(marketSeasonVarietyProperty);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(marketSeasonVarietyProperty: MarketSeasonVarietyProperty): Observable<MarketSeasonVarietyProperty> {
        const copy = this.convert(marketSeasonVarietyProperty);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MarketSeasonVarietyProperty> {
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

    private convert(marketSeasonVarietyProperty: MarketSeasonVarietyProperty): MarketSeasonVarietyProperty {
        const copy: MarketSeasonVarietyProperty = Object.assign({}, marketSeasonVarietyProperty);
        return copy;
    }
}
