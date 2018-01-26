import {Variety} from '../variety/variety.model';
import {PriceList} from '../price-list/price-list.model';
import {MarketSeason} from '../market-season/market-season.model';
import {ShippingPolicy} from '../shipping-policy/shipping-policy.model';
import {Company} from '../company/company.model';
import {Pinch} from '../pinch/pinch.model';

export enum Length {
    '_40',
    ' _50',
    ' _60',
    ' _70',
    ' _80',
    ' _90',
    ' _100',
    ' _110',
    ' _120',
    ' _130',
    ' _140',
    ' _150',
    ' _160',
    ' _170',
    ' _180',
    ' _190',
    ' _200',
    ' _210'
}

export class MarketSeasonVarietyProperty {
    constructor(
        public id?: number,
        public length?: Length,
        public price?: number,
        public variety?: Variety,
        public priceList?: PriceList,
        public marketSeason?: MarketSeason,
        public shippingPolicy?: ShippingPolicy,
        public pinch?: Pinch,
        public company?: Company,
    ) {
    }
}
