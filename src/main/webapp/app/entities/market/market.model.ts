import {MarketSeason} from '../market-season/market-season.model';
import {CompanyUser} from '../company-user/company-user.model';
import {MarketBox} from '../market-box/market-box.model';
import {BoxGroup} from '../box-group/box-group.model';
import {MarketVariety} from '../market-variety/market-variety.model';
import {Company} from '../company/company.model';
import {MarketClient} from '../market-client/market-client.model';

export class Market {
    constructor(
        public id?: number,
        public name?: string,
        public company?: Company,
        public marketVarieties?: MarketVariety[],
        public boxGroups?: BoxGroup[],
        public marketBoxes?: MarketBox[],
        public companyUsers?: CompanyUser[],
        public marketSeasons?: MarketSeason[],
        public marketClients?: MarketClient[]
    ) {
    }
}
