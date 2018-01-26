import {Company} from '../company/company.model';

export class MixType {
    constructor(
        public id?: number,
        public name?: string,
        public company?: Company,
    ) {
    }
}
