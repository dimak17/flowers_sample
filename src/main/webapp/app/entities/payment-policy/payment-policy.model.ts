import { Company } from '../company';
import { ClientPaymentPolicy } from '../client-payment-policy';
export class PaymentPolicy {
    constructor(
        public id?: number,
        public name?: string,
        public company?: Company,
        public clientPaymentPolicy?: ClientPaymentPolicy,
    ) {
    }
}
