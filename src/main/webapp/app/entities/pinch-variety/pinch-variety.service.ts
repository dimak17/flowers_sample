import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PinchVariety } from './pinch-variety.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PinchVarietyService {

    private resourceUrl = 'api/pinch-varieties';

    constructor(private http: Http) { }

    create(pinchVariety: PinchVariety): Observable<PinchVariety> {
        const copy = this.convert(pinchVariety);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(pinchVariety: PinchVariety): Observable<PinchVariety> {
        const copy = this.convert(pinchVariety);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PinchVariety> {
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

    private convert(pinchVariety: PinchVariety): PinchVariety {
        const copy: PinchVariety = Object.assign({}, pinchVariety);
        return copy;
    }
}
