import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { TypeOfFlower } from './type-of-flower.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TypeOfFlowerService {

    private resourceUrl = 'api/type-of-flowers';

    constructor(private http: Http) { }

    create(typeOfFlower: TypeOfFlower): Observable<TypeOfFlower> {
        const copy = this.convert(typeOfFlower);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(typeOfFlower: TypeOfFlower): Observable<TypeOfFlower> {
        const copy = this.convert(typeOfFlower);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<TypeOfFlower> {
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

    private convert(typeOfFlower: TypeOfFlower): TypeOfFlower {
        const copy: TypeOfFlower = Object.assign({}, typeOfFlower);
        return copy;
    }
}
