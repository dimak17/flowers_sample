import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MarketBox } from './market-box.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MarketBoxService {

    private resourceUrl = 'api/market-boxes';

    constructor(private http: Http) { }

    create(marketBox: MarketBox): Observable<MarketBox> {
        const copy = this.convert(marketBox);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(marketBox: MarketBox): Observable<MarketBox> {
        const copy = this.convert(marketBox);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MarketBox> {
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

    private convert(marketBox: MarketBox): MarketBox {
        const copy: MarketBox = Object.assign({}, marketBox);
        return copy;
    }
}
