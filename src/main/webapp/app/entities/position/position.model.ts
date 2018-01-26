import { Company } from '../company';
import { CompanyUser } from '../company-user';
export class Position {

    constructor(
        public _id?: number,
        public _name?: string,
        public company?: Company,
        public companyUsers?: CompanyUser,
        public authority?: any,
    ) {
    }

    public get id(): number {
        return this._id;
    }

    public set id(value: number) {
        this._id = value;
    }

    public get name(): string {
        return this._name;
    }

    public set name(value: string) {
        this._name = value;
    }
}
