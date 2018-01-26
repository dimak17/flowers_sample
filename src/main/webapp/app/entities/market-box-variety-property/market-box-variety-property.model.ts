import { MarketBox } from '../market-box';
import {Length} from '../../shared/constants/length.constants';
import {Variety} from '../variety/variety.model';
export class MarketBoxVarietyProperty {
    constructor(
        public id?: number,
        public capacity?: number,
        public length?: Length,
        public marketBox?: MarketBox,
        public variety?: Variety,
    ) {
    }
}
