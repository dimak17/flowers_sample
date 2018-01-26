import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {createRequestOption, ResponseWrapper} from '../../shared';
import {PriceList} from '../../entities/price-list/price-list.model';
import {PriceListDTO} from './price-list-dto';
import {Variety} from '../../entities/variety/variety.model';
import ResponseUtil from '../../util/response-util';
import {ShippingPolicy} from '../../entities/shipping-policy/shipping-policy.model';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import {MarketSeasonVarietyProperty} from '../../entities/market-season-variety-property/market-season-variety-property.model';
import {MarketSeason} from '../../entities/market-season/market-season.model';

@Injectable()
export class SeasonsPriceListService {

    private resourceUrl = 'api/price-lists';
    private marketUrl = 'api/markets/company';
    private varietyUrl = 'api/varieties';
    private marketSeasonVarietyUrl = '/api/market-season-variety-properties';
    private url = 'i18n/resources/price_lists/columns.json';
    private priceListUrl = 'api/price-lists';
    private marketSeasonUrl = 'api/market-seasons';
    private shippingPoliciesUrl = 'api/shipping-policies';
    private typeOfFlowersUrl = 'api/type-of-flowers';

    constructor(private http: Http, ) {
    }

    findAllTypeOfFlowerByIdCompany(): Observable<TypeOfFlower[]> {
        return this.http.get(`${this.typeOfFlowersUrl}/company/`).map((res: Response) => {
            return res.json();
        });
    }

    getVarietiesByTypeOfFlowers(typeOfFlowerId: number): Observable<Variety[]> {
        return this.http.get(`${this.varietyUrl}/type-of-flower/${typeOfFlowerId}`).map((res: Response) => {
            return res.json();
        });
    }

    getAllShippingPolicies(): Observable<ShippingPolicy[]> {
        return this.http.get(`${this.shippingPoliciesUrl}/company/`).map((res: Response) => {
            return res.json();
        });
    }

    createPriceList(priceList: PriceList): Observable<PriceList> {
        const copy = this.convertPriceList(priceList);
        return this.http.post(this.priceListUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    getPriceList(type: string): Observable<PriceList> {
        return this.http.get(`${this.resourceUrl}/${type}`).map((res: Response) => {
            return ResponseUtil.toJson(res);
        });
    }

    getColumns() {
        return this.http.get(this.url).map((response) => response.json());
    }

    queryMarkets(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.marketUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    updateMarketSeasonVarietyProperty(marketSeasonVarietyProperty: MarketSeasonVarietyProperty) {
        const copy = this.convertMarketSeasonVarietyProperty(marketSeasonVarietyProperty);
        return this.http.put(this.marketSeasonVarietyUrl, copy);
    }

    getMarketSeasonVarietyProperty(idMarketSeason?: number, idVariety?: number, idShippingPolicy?: number): Observable<PriceListDTO> {
        return this.http.get(`${this.marketSeasonVarietyUrl}/market-season/${idMarketSeason}
        /variety/${idVariety}/shipping-policy/${idShippingPolicy}`).map((res: Response) => {
            return res.json();
        });
    }

    getMarketSeasonVarietyPropertyAll(idMarketSeason?: number, type?: string, idTypeOfFlower?: number, idShippingPolicy?: number): Observable<PriceListDTO[]> {
        return this.http.get(`${this.marketSeasonVarietyUrl}/market-season/${idMarketSeason}/price-list/${type}
        /type-of-flower/${idTypeOfFlower}/shipping-policy/${idShippingPolicy}`).map((res: Response) => {
            return res.json();
        });
    }

    getMarketSeasonVarietyPropertyByType(type?: string): Observable<PriceListDTO[]> {
        return this.http.get(`${this.marketSeasonVarietyUrl}/price-list/${type}`).map((res: Response) => {
            return res.json();
        });
    }

    queryMarketSeasons(): Observable<MarketSeason[]> {
        return this.http.get(`${this.marketSeasonUrl}/company/`).map((res: Response) => {
            return res.json();
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertMarketSeasonVarietyProperty(MarketSeasonVarietyProperty: MarketSeasonVarietyProperty): MarketSeasonVarietyProperty {
        const copy: MarketSeasonVarietyProperty = Object.assign({}, MarketSeasonVarietyProperty);
        return copy;
    }

    private convertPriceList(priceList: PriceList): PriceList {
        const copy: PriceList = Object.assign({}, priceList);
        return copy;
    }
}
