import { Company } from '../company';
import {Label} from '../label/label.model';
import {Destination} from '../destination/destination.model';
import {Country} from '../country/country.model';
export class LabelCountry {
    constructor(
        public id?: number,
        public label?: Label,
        public country?: Country,
        public company?: Company,
        public destination?: Destination,
    ) {
    }
}
