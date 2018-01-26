const enum Length {
    '_40',
    '_50',
    '_60',
    '_70',
    '_80',
    '_90',
    '_100',
    '_110',
    '_120',
    '_130',
    '_140',
    '_150',
    '_160',
    '_170',
    '_180',
    '_190',
    '_200',
    '_210'

};

import { PinchVariety } from '../pinch-variety';
export class PinchVarietyProperty {
    constructor(
        public id?: number,
        public length?: Length,
        public quantity?: number,
        public pinchVariety?: PinchVariety,
    ) {
    }
}
