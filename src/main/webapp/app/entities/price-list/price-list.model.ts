
export const enum PriceListType {
    'DEFAULT',
    'HIGH',
    'LOW',
    'SEASON'

}
export class PriceList {
    constructor(
        public id?: number,
        public type?: PriceListType,
    ) {
    }
}
