import { LabelCountry } from '../label-country';
export class Country {
    constructor(
        public id?: number,
        public name?: string,
        public labelCountry?: LabelCountry,
    ) {
    }
}
