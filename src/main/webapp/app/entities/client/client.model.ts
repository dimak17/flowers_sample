import { Company } from '../company';
import { MarketClient } from '../market-client';
import { CompanyUser } from '../company-user';
import { ClientPaymentPolicy } from '../client-payment-policy';
import {ClaimsPolicy} from '../claims-policy/claims-policy.model';
import {Label} from '../label/label.model';
import {ClientEmployee} from '../client-employee/client-employee.model';
export class Client {
    constructor(
        public id?: number,
        public companyName?: string,
        public address?: string,
        public officePhone?: string,
        public email?: string,
        public skype?: string,
        public webPage?: string,
        public activationStatus?: boolean,
        public blockStatus?: boolean,
        public idNumber?: string,
        public clientEmployees?: ClientEmployee,
        public company?: Company,
        public marketClient?: MarketClient,
        public claimsPolicy?: ClaimsPolicy,
        public label?: Label,
        public companyUser?: CompanyUser,
        public clientPaymentPolicy?: ClientPaymentPolicy,
    ) {
        this.activationStatus = false;
        this.blockStatus = false;
    }
}
