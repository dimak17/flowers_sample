
import {ClaimsPolicy} from '../../entities/claims-policy/claims-policy.model';
export class ClaimsPolicyUi extends ClaimsPolicy {
    constructor(
        public id?: number,
        public shortName?: string,
        public fullName?: string,
        public colorClass?: string
    ) {
        super(id, shortName, fullName);
        this.colorClass = colorClass;
    }

}
