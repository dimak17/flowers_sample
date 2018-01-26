import {Market} from '../market/market.model';
import {Season} from '../season/season.model';

export class MarketSeason {
    constructor(public id?: number,
                public market?: Market,
                public season?: Season, ) {
    }
}
