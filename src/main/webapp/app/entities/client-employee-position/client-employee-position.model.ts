import { Company } from '../company';
import {ClientEmployee} from '../client-employee/client-employee.model';
export class ClientEmployeePosition {
    constructor(
        public id?: number,
        public name?: string,
        public company?: Company,
        public clientEmployee?: ClientEmployee,
    ) {
    }
}
