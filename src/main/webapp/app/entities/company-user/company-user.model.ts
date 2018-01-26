import { User } from '../../shared';
import { Company } from '../company';
import { Position } from '../position';
import {Market} from '../market/market.model';
import {Client} from '../client/client.model';
export class CompanyUser {
    constructor(
        public id?: number,
        public skype?: string,
        public fullName?: string,
        public accountEmail?: string,
        public workEmail?: string,
        public mobilePhone?: string,
        public whatsUp?: string,
        public officePhone?: string,
        public user?: User,
        public company?: Company,
        public positions?: Position [],
        public markets?: Market [],
        public client?: Client
    ) {
    }
}
