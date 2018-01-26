import { PaymentPolicy } from '../payment-policy';
import {Client} from '../client/client.model';
export class ClientPaymentPolicy {
    constructor(
        public id?: number,
        public termsInDays?: number,
        public termsInSum?: number,
        public status?: boolean,
        public client?: Client,
        public paymentPolicy?: PaymentPolicy,
    ) {
        this.status = false;
    }
}
