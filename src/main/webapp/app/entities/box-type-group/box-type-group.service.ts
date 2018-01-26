import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { BoxTypeGroup } from './box-type-group.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BoxTypeGroupService {

    private resourceUrl = 'api/box-type-groups';

    constructor(private http: Http) { }

    create(boxTypeGroup: BoxTypeGroup): Observable<BoxTypeGroup> {
        const copy = this.convert(boxTypeGroup);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(boxTypeGroup: BoxTypeGroup): Observable<BoxTypeGroup> {
        const copy = this.convert(boxTypeGroup);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<BoxTypeGroup> {
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

    private convert(boxTypeGroup: BoxTypeGroup): BoxTypeGroup {
        const copy: BoxTypeGroup = Object.assign({}, boxTypeGroup);
        return copy;
    }
}
