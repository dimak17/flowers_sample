import {Company} from '../company/company.model';
import {Position} from '../position/position.model';
import {Pinch} from '../pinch/pinch.model';
import {MarketSeason} from '../market-season/market-season.model';

export class Season {
    constructor(
        public id?: number,
        public seasonName?: string,
        public seasonYear?: number,
        public startDate?: any,
        public endDate?: any,
        public notifyStartDate?: any,
        public company?: Company,
        public positions?: Position[],
        public pinches?: Pinch[],
        public marketSeasons?: MarketSeason[],

        public dates?: string,
        public notifyTableStartDate?: string,
        public tableMarkets?: string,
        public notifyTo?: string
    ) {
    }
}
