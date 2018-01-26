import {Block} from '../block/block.model';
export class Sort {
    constructor(
        public id?: number,
        public name?: string,
        public length?: number,
        public blocks?: Block,
    ) {
    }
}
