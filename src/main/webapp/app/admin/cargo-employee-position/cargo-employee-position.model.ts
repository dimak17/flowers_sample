import { Company } from '../../entities/company/company.model';

export class CargoEmployeePosition {
    constructor(public id?: number,
                public name?: string,
                public company?: Company,
                public colorClass?: string) {
    }
}
