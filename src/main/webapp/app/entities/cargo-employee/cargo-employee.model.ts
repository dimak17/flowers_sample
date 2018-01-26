import {Market} from '../market/market.model';

import { Company } from '../company';
export class CargoEmployee {
    constructor(
        public id?: number,
        public fullName?: string,
        public email?: string,
        public officePhone?: string,
        public mobilePhone?: string,
        public skype?: string,
        public company?: Company,
        public markets?: Market [],
    ) {
    }
}
