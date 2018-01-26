import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { ResponseWrapper, createRequestOption } from '../../shared';
import {Season} from '../../entities/season/season.model';
import {Market} from '../../entities/market/market.model';
import {Position} from '../../entities/position/position.model';

@Injectable()
export class SeasonService {

    private resourceUrl = 'api/seasons';
    private marketResourceUrl = 'api/markets';
    private positionResourceUrl = 'api/positions';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(season: Season): Observable<Season> {
        const copy = this.convert(season);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(season: Season): Observable<Season> {
        const copy = this.convert(season);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Season> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getAllByCurrentCompany(): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrl).map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(entity.endDate);
        entity.notifyStartDate = this.dateUtils
            .convertLocalDateFromServer(entity.notifyStartDate);
    }

    private convert(season: Season): Season {
        const copy: Season = Object.assign({}, season);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(season.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(season.endDate);
        copy.notifyStartDate = this.dateUtils
            .convertLocalDateToServer(season.notifyStartDate);
        return copy;
    }

    getAllMarketsByCurrentCompany(): Observable<Market[]> {
        return this.http.get(this.marketResourceUrl + '/company').map((res: Response) => {
            return res.json();
        });
    }

    getAllPositionsByCompany(): Observable<Position[]> {
        return this.http.get(this.positionResourceUrl + '/company').map((res: Response) => {
            return res.json();
        });
    }

}
