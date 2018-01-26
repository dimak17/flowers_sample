import { LabelCountry } from '../label-country';
import { Company } from '../company';
export class Destination {
    constructor(
        public id?: number,
        public city?: string,
        public info?: string,
        public labelCountry?: LabelCountry,
        public company?: Company,
    ) {
    }
}
