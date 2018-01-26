import {CargoEmployee} from '../cargo-employee/cargo-employee.model';
import {Company} from '../company/company.model';
export class CargoAgency {
    constructor(
        public id?: number,
        public name?: string,
        public mainAddress?: string,
        public additionalAddress?: string,
        public officePhone?: string,
        public email?: string,
        public webPage?: string,
        public cargoEmployees?: CargoEmployee[],
        public company?: Company,
    ) {
    }
}
