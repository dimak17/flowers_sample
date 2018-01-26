import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { ResponseWrapper, createRequestOption } from '../../shared';
import {Pinch} from '../../entities/pinch/pinch.model';
import {Season} from '../../entities/season/season.model';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import {PinchListDTO} from './pinch-list-dto';
import ResponseUtil from '../../util/response-util';
import {Variety} from '../../entities/variety/variety.model';
import {MarketSeasonVarietyProperty} from '../../entities/market-season-variety-property/market-season-variety-property.model';

@Injectable()
export class PinchService {

    private resourceUrl = 'api/pinches';
    private seasonsUrl = 'api/seasons';
    private typeOfFlowersUrl = 'api/type-of-flowers';
    private marketSeasonVarietyPropertiesUrl = 'api/market-season-variety-properties';
    private marketUrl = 'api/markets';
    private url = 'i18n/resources/pinch/columns.json';
    private varietyUrl = 'api/varieties';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(pinch: Pinch): Observable<Pinch> {
        const copy = this.convert(pinch);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(pinch: Pinch): Observable<Pinch> {
        const copy = this.convert(pinch);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Pinch> {
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

    getAllSeasons(): Observable<Season[]> {
        return this.http.get(`${this.seasonsUrl}/company/`).map((res: Response) => {
            return res.json();
        });
    }

    updateMarketSeasonVarietyProperty(marketSeasonVarietyProperty: MarketSeasonVarietyProperty) {
        const copy = this.convertMarketSeasonVarietyProperty(marketSeasonVarietyProperty);
        return this.http.put(this.marketSeasonVarietyPropertiesUrl, copy);
    }

    findAllTypeOfFlowerByIdCompany(): Observable<TypeOfFlower[]> {
        return this.http.get(`${this.typeOfFlowersUrl}/company/`).map((res: Response) => {
            return res.json();
        });
    }
    getMarketsByCurrentCompany(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.marketUrl}/company/`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getMarketSeasonVarietyProperty(idMarket?: number, idVariety?: number, idSeason?: number): Observable<PinchListDTO> {
        return this.http.get(`${this.marketSeasonVarietyPropertiesUrl}/market/${idMarket}/variety/${idVariety}
        /season/${idSeason}/pinch/`).map((res: Response) => {
            return res.json();
        });
    }

    getMarketSeasonVarietyPropertiesByCompany(): Observable<PinchListDTO[]> {
        return this.http.get(`${this.marketSeasonVarietyPropertiesUrl}/pinch/`).map((res: Response) => {
            return res.json();
        });
    }

    getVarietiesByTypeOfFlowers(typeOfFlowerId: number): Observable<Variety[]> {
        return this.http.get(`${this.varietyUrl}/type-of-flower/${typeOfFlowerId}`).map((res: Response) => {
            return res.json();
        });
    }

    getPinchByCompany(): Observable<Pinch> {
        return this.http.get(this.resourceUrl).map((res: Response) => {
            return ResponseUtil.toJson(res);
        });
    }

    getMarketSeasonVarietyPropertyAll(idMarket?: number, idTypeOfFlowers?: number, idSeason?: number): Observable<PinchListDTO[]> {
        return this.http.get(`${this.marketSeasonVarietyPropertiesUrl}/market/${idMarket}/pinch/type-of-flower/${idTypeOfFlowers}/season/${idSeason}`)
            .map((res: Response) => {
                return res.json();
            });
    }

    createPinch(pinch: Pinch): Observable<Pinch> {
        const copy = this.convert(pinch);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    getColumns() {
        return this.http.get(this.url).map((response) => response.json());
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

    private convert(pinch: Pinch): Pinch {
        const copy: Pinch = Object.assign({}, pinch);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(pinch.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(pinch.endDate);
        copy.notifyStartDate = this.dateUtils
            .convertLocalDateToServer(pinch.notifyStartDate);
        return copy;
    }

    private convertMarketSeasonVarietyProperty(marketSeasonVarietyProperty: MarketSeasonVarietyProperty): MarketSeasonVarietyProperty {
        const copy: MarketSeasonVarietyProperty = Object.assign({}, marketSeasonVarietyProperty);
        return copy;
    }
}
