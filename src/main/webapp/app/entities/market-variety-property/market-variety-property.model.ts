import {PriceList} from '../price-list/price-list.model';
import {Market} from '../market/market.model';
import {Variety} from '../variety/variety.model';
import {ShippingPolicy} from '../../admin/shipping-policy/shipping-policy.model';
import {Company} from '../company/company.model';
import {Length} from '../../shared/constants/length.constants';

export class MarketVarietyProperty {
    constructor(
        public id?: number,
        public length?: Length,
        public price?: number,
        public priceList?: PriceList,
        public market?: Market,
        public variety?: Variety,
        public shippingPolicy?: ShippingPolicy,
        public company?: Company,
    ) {
    }
}
