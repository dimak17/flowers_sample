import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MarketVariety } from './market-variety.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MarketVarietyService {

    private resourceUrl = 'api/market-varieties';

    constructor(private http: Http) { }

    create(marketVariety: MarketVariety): Observable<MarketVariety> {
        const copy = this.convert(marketVariety);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(marketVariety: MarketVariety): Observable<MarketVariety> {
        const copy = this.convert(marketVariety);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MarketVariety> {
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

    private convert(marketVariety: MarketVariety): MarketVariety {
        const copy: MarketVariety = Object.assign({}, marketVariety);
        return copy;
    }
}
