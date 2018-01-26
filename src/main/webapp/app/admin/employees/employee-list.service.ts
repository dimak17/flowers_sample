/**
 * Created by dima on 11.07.17.
 */
import { Injectable } from '@angular/core';
import {Http, Response, URLSearchParams} from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ResponseWrapper, createRequestOption } from '../../shared';
import {CompanyUser} from '../../entities/company-user/company-user.model';
import {Company} from '../../entities/company/company.model';
import {Position} from '../../entities/position/position.model';
import {Market} from '../../entities/market/market.model';

@Injectable()
export class EmployeeListService {

    private resourceUrl = 'api/company-users';
    private resourceCompanyUrl = 'api/companies';
    private positionResourceUrl = 'api/positions';
    private marketResourceUrl = 'api/markets';

    constructor(private http: Http) { }

    create(companyUser: CompanyUser): Observable<CompanyUser> {
        const copy = this.convert(companyUser);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(companyUser: CompanyUser): Observable<CompanyUser> {
        const copy = this.convert(companyUser);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CompanyUser> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getCompanyUsersFromCurrentCompany(): Observable<CompanyUser[]> {
        return this.http.get(this.resourceUrl.concat('/company')).map((res: Response) => {
            return res.json();
        });
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(companyUser: CompanyUser): CompanyUser {
        const copy: CompanyUser = Object.assign({}, companyUser);
        return copy;
    }

    getAllPositionsByCompany(): Observable<Position[]> {
        return this.http.get(this.positionResourceUrl + '/company').map((res: Response) => {
            return res.json();
        });
    }

    getAllMarketsByCurrentCompany(): Observable<Market[]> {
        return this.http.get(this.marketResourceUrl + '/company').map((res: Response) => {
            return res.json();
        });
    }

    getCurrentUserByCurrentCompany(): Observable<CompanyUser> {
        return this.http.get(this.resourceUrl + '/current').map((res: Response) => {
            return res.json();
        });
    }
}
