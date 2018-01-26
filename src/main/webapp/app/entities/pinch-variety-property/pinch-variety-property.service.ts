import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PinchVarietyProperty } from './pinch-variety-property.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PinchVarietyPropertyService {

    private resourceUrl = 'api/pinch-variety-properties';

    constructor(private http: Http) { }

    create(pinchVarietyProperty: PinchVarietyProperty): Observable<PinchVarietyProperty> {
        const copy = this.convert(pinchVarietyProperty);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(pinchVarietyProperty: PinchVarietyProperty): Observable<PinchVarietyProperty> {
        const copy = this.convert(pinchVarietyProperty);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PinchVarietyProperty> {
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

    private convert(pinchVarietyProperty: PinchVarietyProperty): PinchVarietyProperty {
        const copy: PinchVarietyProperty = Object.assign({}, pinchVarietyProperty);
        return copy;
    }
}
