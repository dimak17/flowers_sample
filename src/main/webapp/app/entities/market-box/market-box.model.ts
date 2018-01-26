import {Market} from '../market';
import {BoxType} from '../box-type/box-type.model';

export enum BoxSizeUnit {
    'MM',
    'M',
    'CM'

}

export class MarketBox {
    constructor(
        public id?: number,
        public width?: number,
        public height?: number,
        public length?: number,
        public unit?: BoxSizeUnit,
        public market?: Market,
        public boxType?: BoxType,
    ) {
    }
}
