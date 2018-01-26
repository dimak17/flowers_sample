import { Company } from '../company';
import {Client} from '../client/client.model';
import {ClientEmployeePosition} from '../client-employee-position/client-employee-position.model';
export class ClientEmployee {
    constructor(
        public id?: number,
        public fullName?: string,
        public mobilePhone?: string,
        public officePhone?: string,
        public email?: string,
        public skype?: string,
        public client?: Client,
        public company?: Company,
        public clientEmployeePosition?: ClientEmployeePosition,
    ) {
    }
}
