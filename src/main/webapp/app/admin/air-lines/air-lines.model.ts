import { Company } from '../../entities/company';
export class AirLines {
    constructor(
        public id?: number,
        public name?: string,
        public number?: number,
        public company?: Company,
        public colorClass?: string,
    ) {
    }
}
