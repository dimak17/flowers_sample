import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { CargoEmployeePosition } from './cargo-employee-position.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CargoEmployeePositionService {

    private resourceUrl = 'api/cargo-employee-positions';

    constructor(private http: Http) { }

    create(cargoEmployeePosition: CargoEmployeePosition): Observable<CargoEmployeePosition> {
        const copy = this.convert(cargoEmployeePosition);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(cargoEmployeePosition: CargoEmployeePosition): Observable<CargoEmployeePosition> {
        const copy = this.convert(cargoEmployeePosition);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CargoEmployeePosition> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    getAllCargoEmployeePositionsByCurrentCompany(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
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

    private convert(cargoEmployeePosition: CargoEmployeePosition): CargoEmployeePosition {
        const copy: CargoEmployeePosition = Object.assign({}, cargoEmployeePosition);
        return copy;
    }
}
