import { CargoEmployeePosition } from '../cargo-employee-position/cargo-employee-position.model';
import { CargoAgency } from '../cargo-agency/cargo-agency.model';
import { Market } from '../../entities/market/market.model';

export class CargoEmployee {
    constructor(
        public id?: number,
        public fullName?: string,
        public email?: string,
        public officePhone?: string,
        public mobilePhone?: string,
        public skype?: string,
        public cargoEmployeePositions?: CargoEmployeePosition [],
        public markets?: Market [],
        public cargoAgency?: CargoAgency,

        public tableMarkets?: string,
        public tableCargoEmployeePositions?: string,
    ) {
    }
}
