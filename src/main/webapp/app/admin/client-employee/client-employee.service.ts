import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ResponseWrapper, createRequestOption } from '../../shared';
import {ClientEmployee} from '../../entities/client-employee/client-employee.model';

@Injectable()
export class ClientEmployeeService {

    private resourceUrl = 'api/client-employees';

    constructor(private http: Http) { }

    create(clientEmployee: ClientEmployee): Observable<ClientEmployee> {
        const copy = this.convert(clientEmployee);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(clientEmployee: ClientEmployee): Observable<ClientEmployee> {
        const copy = this.convert(clientEmployee);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ClientEmployee> {
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

    private convert(clientEmployee: ClientEmployee): ClientEmployee {
        const copy: ClientEmployee = Object.assign({}, clientEmployee);
        return copy;
    }
}
