import {Variety} from '../variety/variety.model';
import {Pinch} from '../pinch/pinch.model';
export class PinchVariety {
    constructor(
        public id?: number,
        public pinch?: Pinch,
        public variety?: Variety,
    ) {
    }
}
