import {Injectable} from '@angular/core';
import {Http, Response, ResponseContentType} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {createRequestOption, ResponseWrapper} from '../../shared';
import {PriceList} from '../../entities/price-list/price-list.model';
import {PriceListDTO} from './price-list-dto';
import {Variety} from '../../entities/variety/variety.model';
import ResponseUtil from '../../util/response-util';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import {ShippingPolicy} from '../../entities/shipping-policy/shipping-policy.model';
import {MarketVarietyProperty} from '../../entities/market-variety-property/market-variety-property.model';
import * as FileSaver from 'file-saver';

@Injectable()
export class PriceListService {

    private resourceUrl = 'api/price-lists';
    private marketUrl = 'api/markets/company';
    private varietyUrl = 'api/varieties';
    private marketVarietyPropertiesUrl = 'api/market-variety-properties';
    private url = 'i18n/resources/price_lists';
    private priceListUrl = 'api/price-lists';
    private typeOfFlowersUrl = 'api/type-of-flowers';
    private shippingPoliciesUrl = 'api/shipping-policies';

    constructor(private http: Http) {
    }

    getAllShippingPolicies(): Observable<ShippingPolicy[]> {
        return this.http.get(`${this.shippingPoliciesUrl}/company/`).map((res: Response) => {
            return res.json();
        });
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
        return this.http.get(`${this.url}/columns.json`).map((response) => response.json());
    }

    getFileExtensions() {
        return this.http.get(`${this.url}/file-extensions.json`).map((response) => response.json());
    }

    updateMarketVarietyProperty(marketVarietyProperty: MarketVarietyProperty) {
        const copy = this.convertMarketVarietyProperty(marketVarietyProperty);
        return this.http.put(this.marketVarietyPropertiesUrl, copy);
    }

    getMarketVarietyProperty(idMarket?: number, idVariety?: number, idShippingPolicy?: number, type?: string): Observable<PriceListDTO> {
        return this.http.get(`${this.marketVarietyPropertiesUrl}/market/${idMarket}/variety/${idVariety}
        /shipping-policy/${idShippingPolicy}/price-list/${type}`).map((res: Response) => {
            return res.json();
        });
    }

    getMarketVarietyPropertyAll(idMarket?: number, idTypeOfFlowers?: number, idShippingPolicy?: number, type?: string): Observable<PriceListDTO[]> {
        return this.http.get(`${this.marketVarietyPropertiesUrl}/market/${idMarket}/price-list/${type}
        /type-of-flower/${idTypeOfFlowers}/shipping-policy/${idShippingPolicy}`)
            .map((res: Response) => {
                return res.json();
            });
    }

    getMarketVarietyPropertiesByType(type?: string): Observable<PriceListDTO[]> {
        return this.http.get(`${this.marketVarietyPropertiesUrl}/price-list/${type}`).map((res: Response) => {
            return res.json();
        });
    }

    getMarketsByCurrentCompany(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.marketUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    getPriceListFile(priceLists: PriceListDTO[], priceListType: string, extension: string) {
            return this.http.post(`${this.priceListUrl}/downloadPriceList`, {
                priceLists,
                priceListType,
                extension
            }, {responseType: ResponseContentType.Blob})
                .map((res) => {
                    FileSaver.saveAs(new Blob([res.blob()], {type: 'application/pdf'}),
                        res.headers.get('Content-disposition').replace('attachment; filename=', '').concat(extension));
                    return false;
            });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertMarketVarietyProperty(marketVarietyProperty: MarketVarietyProperty): MarketVarietyProperty {
        const copy: MarketVarietyProperty = Object.assign({}, marketVarietyProperty);
        return copy;
    }

    private convertPriceList(priceList: PriceList): PriceList {
        const copy: PriceList = Object.assign({}, priceList);
        return copy;
    }
}
