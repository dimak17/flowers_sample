import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Disease } from './disease.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DiseaseService {

    private resourceUrl = 'api/diseases';

    constructor(private http: Http) { }

    create(disease: Disease): Observable<Disease> {
        const copy = this.convert(disease);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(disease: Disease): Observable<Disease> {
        const copy = this.convert(disease);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Disease> {
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

    private convert(disease: Disease): Disease {
        const copy: Disease = Object.assign({}, disease);
        return copy;
    }
}
