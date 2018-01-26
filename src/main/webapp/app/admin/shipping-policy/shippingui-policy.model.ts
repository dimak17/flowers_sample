import {ShippingPolicy} from './shipping-policy.model';

export class ShippingPolicyUi extends ShippingPolicy {
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
