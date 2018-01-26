import { Company } from '../company';
import { LabelCountry } from '../label-country';
import {Client} from '../client/client.model';
export class Label {
    constructor(
        public id?: number,
        public labelName?: string,
        public deliveryDays?: number,
        public wanted?: boolean,
        public company?: Company,
        public labelCountry?: LabelCountry,
        public client?: Client,
    ) {
        this.wanted = false;
    }
}
