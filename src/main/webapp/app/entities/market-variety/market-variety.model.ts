
export enum MarketVarietyType {
    'SPECIAL',
    'PROHIBITED'
};
import { Market } from '../market';
import {Variety} from '../variety/variety.model';
export class MarketVariety {
    constructor(
        public id?: number,
        public type?: MarketVarietyType,
        public market?: Market,
        public variety?: Variety,
    ) {
    }
}
