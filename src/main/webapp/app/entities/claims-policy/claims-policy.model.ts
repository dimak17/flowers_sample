
import {Company} from '../../entities/company/company.model';

export class ClaimsPolicy  {
    constructor(
        public id?: number,
        public shortName?: string,
        public fullName?: string,
        public company?: Company
    ) {
    }
}
