import {Season} from '../season/season.model';
import {CompanyUser} from '../company-user/company-user.model';
import {PinchVariety} from '../pinch-variety/pinch-variety.model';
export class Pinch {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public totalStems?: number,
        public notifyStartDate?: any,
        public seasons?: Season [],
        public companyUsers?: CompanyUser [],
        public pinchVariety?: PinchVariety,

        public dates?: string,
        public notifyTableStartDate?: string,
        public notifyTo?: string,
        public seasonsTable?: string
    ) {
    }
}
