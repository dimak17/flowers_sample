
const enum TYPE_OF_FLOWERS {
    'ROSES'

};
export class Company {
    constructor(
        public id?: number,
        public farmName?: string,
        public legalName?: string,
        public generalOfficePhone?: string,
        public country?: any,
        public city?: string,
        public farmSize?: string,
        public generalEmailAddress?: string,
        public address?: string,
        public typeOfFlowers?: TYPE_OF_FLOWERS,
    ) {
    }
}
