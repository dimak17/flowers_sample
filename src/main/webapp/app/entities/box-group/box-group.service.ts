import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { BoxGroup } from './box-group.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BoxGroupService {

    private resourceUrl = 'api/box-groups';

    constructor(private http: Http) { }

    create(boxGroup: BoxGroup): Observable<BoxGroup> {
        const copy = this.convert(boxGroup);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(boxGroup: BoxGroup): Observable<BoxGroup> {
        const copy = this.convert(boxGroup);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<BoxGroup> {
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

    private convert(boxGroup: BoxGroup): BoxGroup {
        const copy: BoxGroup = Object.assign({}, boxGroup);
        return copy;
    }
}
