import {Market} from '../../entities/market/market.model';
import {Variety} from '../../entities/variety/variety.model';
import {PriceList} from '../../entities/price-list/price-list.model';
import {MarketSeason} from '../../entities/market-season/market-season.model';
import {ShippingPolicy} from '../../entities/shipping-policy/shipping-policy.model';

export class PriceListDTO {
    constructor(public  market?: Market,
                public  variety?: Variety,
                public marketSeason?: MarketSeason,
                public priceList?: PriceList,
                public shippingPolicy?: ShippingPolicy,
                public _40?: number,
                public _50?: number,
                public _60?: number,
                public _70?: number,
                public _80?: number,
                public _90?: number,
                public _100?: number,
                public _110?: number,
                public _120?: number,
                public _130?: number,
                public _140?: number,
                public _150?: number,
                public _160?: number,
                public _170?: number,
                public _180?: number,
                public _190?: number,
                public _200?: number,
                public _210?: number) {
    }
}
