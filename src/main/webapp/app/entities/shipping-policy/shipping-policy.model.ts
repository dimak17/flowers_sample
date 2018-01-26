import {MarketVarietyProperty} from '../market-variety-property/market-variety-property.model';
import {MarketSeasonVarietyProperty} from '../market-season-variety-property/market-season-variety-property.model';
import {Company} from '../company/company.model';

export class ShippingPolicy {
    constructor(
        public id?: number,
        public shortName?: string,
        public fullName?: string,
        public marketVarietyProperties?: MarketVarietyProperty[],
        public marketSeasonVarietyProperties?: MarketSeasonVarietyProperty[],
        public company?: Company,
    ) {
    }
}
