import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Position } from './position.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PositionService {

    private resourceUrl = 'api/positions';

    constructor(private http: Http) { }

    create(position: Position): Observable<Position> {
        const copy = this.convert(position);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(position: Position): Observable<Position> {
        const copy = this.convert(position);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Position> {
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

    private convert(position: Position): Position {
        const copy: Position = Object.assign({}, position);
        return copy;
    }
}
