import { MarketBox } from '../market-box';
import {Company} from '../company/company.model';
export class BoxType {
    constructor(
        public id?: number,
        public shortName?: string,
        public fullName?: string,
        public boxSize?: string,
        public marketBoxes?: MarketBox,
        public company?: Company,
    ) {
    }
}
