import { Company } from '../company';
import {Variety} from '../variety/variety.model';
export class Block {
    constructor(
        public id?: number,
        public name?: string,
        public beds?: number,
        public varieties?: Variety[],
        public company?: Company,
        public colorClass?: string
    ) {
        this.colorClass = colorClass;
    }
}
