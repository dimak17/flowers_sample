import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ResponseWrapper, createRequestOption } from '../../shared';
import {Market} from '../../entities/market/market.model';
import {Variety} from '../../entities/variety/variety.model';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import {BoxGroupDTO} from '../box-grouping/box-group-dto.model';
import {MarketBoxVarietyProperty} from '../../entities/market-box-variety-property/market-box-variety-property.model';
import {
    MarketBoxVarietyPropertyDto,
    MarketBoxVarietyPropertyDtoResponse
} from '../../shared/dto/market-box-variety-property-dto.model';
import ResponseUtil from '../../util/response-util';

@Injectable()
export class MarketService {
    private varietyResourceUrl = 'api/varieties';
    private resourceUrl = 'api/markets';
    private typeOfFlowerUrl = 'api/type-of-flowers';
    private marketVarietyUrl = 'api/market-varieties';
    private boxGroupsResourceUrl = 'api/box-groups/company';
    private marketBoxVarietyPropertyResourceUrl = 'api/market-box-variety-properties';

    constructor(private http: Http) { }

    create(market: Market): Observable<Market> {
        const copy = this.convert(market);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(market: Market): Observable<Market> {
        const copy = this.convert(market);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Market> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findAll(): Observable<Market[]> {
        return this.http.get(this.resourceUrl).map((res: Response) => res.json());
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    findVarietiesFromCurrentCompany(): Observable<Variety[]> {
        return this.http.get(`${this.varietyResourceUrl}/company`).map((res: Response) => {
            return res.json();
        });
    }

    findAllTypeOfFlowerCurrentCompany(): Observable<TypeOfFlower[]> {
        return this.http.get(`${this.typeOfFlowerUrl}/company`).map((res: Response) => {
            return res.json();
        });
    }

    findMarketVarietyByMarketId(id: number): Observable<Market> {
        return this.http.get(`${this.marketVarietyUrl}/market/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findBoxGroupsDtoByCurrentCompany(): Observable<BoxGroupDTO[]> {
        return this.http.get(`${this.boxGroupsResourceUrl}/dto`).map((res: Response) => {
            return res.json();
        });
    }

    findMarketBoxVarietyProperties(marketBoxId: number): Observable<MarketBoxVarietyPropertyDtoResponse[]> {
        return this.http.get(`${this.marketBoxVarietyPropertyResourceUrl}/market-box/${marketBoxId}`)
            .map((res: Response) => {
                return res.json();
            });
    }

    findMarketBoxVarietyPropertiesByMarket(marketId: number): Observable<MarketBoxVarietyPropertyDtoResponse[]> {
        return this.http.get(`${this.marketBoxVarietyPropertyResourceUrl}/market/${marketId}`)
            .map((res: Response) => {
                return res.json();
            });
    }

    updateMarketBoxVarietyProperties(dtos: MarketBoxVarietyPropertyDto[]) {
        return this.http.put(`${this.marketBoxVarietyPropertyResourceUrl}/dtos`, dtos).map((res: Response) => {
            return ResponseUtil.toJson(res);
        });
    }

    findBoxGroupsByCurrentCompany() {
        return this.http.get(`${this.boxGroupsResourceUrl}`).map((res: Response) => {
            return res.json();
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(market: Market): Market {
        const copy: Market = Object.assign({}, market);
        return copy;
    }
}
