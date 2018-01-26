
import {TypeOfFlower} from '../type-of-flower/type-of-flower.model';

import { MarketVariety } from '../market-variety';
import {PinchVariety} from '../pinch-variety/pinch-variety.model';
export class Variety {
    constructor(
        public id?: number,
        public name?: string,
        public color?: string,
        public breeder?: string,
        public minLength?: number,
        public maxLength?: number,
        public marketVarieties?: MarketVariety,
        public typeOfFlower?: TypeOfFlower,
        public pinchVariety?: PinchVariety
    ) {
    }
}
