import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { CargoEmployee } from './cargo-employee.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { CargoEmployeePosition } from '../cargo-employee-position/cargo-employee-position.model';
import { Market } from '../../entities/market/market.model';
import { CargoAgency } from '../cargo-agency/cargo-agency.model';

@Injectable()
export class CargoEmployeeService {

    private resourceUrl = 'api/cargo-employees';
    private cargoResourseUrl = 'api/cargo-employee-positions';
    private marketResourceUrl = 'api/markets';
    private cargoAgencyResourseUrl = 'api/cargo-agencies';

    constructor(private http: Http) { }

    create(cargoEmployee: CargoEmployee): Observable<CargoEmployee> {
        const copy = this.convert(cargoEmployee);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(cargoEmployee: CargoEmployee): Observable<CargoEmployee> {
        const copy = this.convert(cargoEmployee);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(cargoEmployeeId: number, cargoAgencyId: number): Observable<CargoEmployee> {
        return this.http.get(`${this.resourceUrl}/${cargoEmployeeId}/cargo-agency/${cargoAgencyId}`).map((res: Response) => {
            return res.json();
        });
    }

    findOne(cargoEmployeeId: number): Observable<CargoEmployee> {
            return this.http.get(`${this.resourceUrl}/${cargoEmployeeId}`).map((res: Response) => {
                return res.json();
            });
    }

    getAllCargoEmployeeByCurrentCompany(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllCargoEmployeePositions(): Observable<CargoEmployeePosition[]> {
        return this.http.get(`${this.cargoResourseUrl}`).map((res: Response) => {
            return res.json();
        });
    }

    findAllCargoAgencies(): Observable<CargoAgency[]> {
        return this.http.get(`${this.cargoAgencyResourseUrl}`).map((res: Response) => {
            return res.json();
        });
    }

    getAllMarketsByCurrentCompany(): Observable<Market[]> {
        return this.http.get(this.marketResourceUrl + '/company').map((res: Response) => {
            return res.json();
        });
    }

    delete(cargoEmployeeId: number, cargoAgencyId: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${cargoEmployeeId}/cargo-agency/${cargoAgencyId}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(cargoEmployee: CargoEmployee): CargoEmployee {
        const copy: CargoEmployee = Object.assign({}, cargoEmployee);
        return copy;
    }
}
