import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {MixType} from './mix-type.model';
import {createRequestOption, ResponseWrapper} from '../../shared';

@Injectable()
export class MixTypeService {

    private resourceUrl = 'api/mix-types';
    private byCompanyUrl = 'api/mix-types/by-company';

    constructor(private http: Http) { }

    create(mixType: MixType): Observable<MixType> {
        const copy = this.convert(mixType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(mixType: MixType): Observable<MixType> {
        const copy = this.convert(mixType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<MixType> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getMixTypes(): Observable<MixType[]> {
        return this.http.get(this.byCompanyUrl).map((response) => response.json());
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(mixType: MixType): MixType {
        const copy: MixType = Object.assign({}, mixType);
        return copy;
    }
}
