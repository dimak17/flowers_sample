import {Sort} from '../sort/sort.model';
export class Block {
    constructor(
        public id?: number,
        public name?: string,
        public sorts?: Sort,
    ) {
    }
}
