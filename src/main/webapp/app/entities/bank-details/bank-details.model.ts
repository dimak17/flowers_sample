import {Company} from '../company/company.model';

export class BankDetails {
    constructor(
        public id?: number,
        public general?: string,
        public alternative?: string,
        public company?: Company,
    ) {
    }
}
