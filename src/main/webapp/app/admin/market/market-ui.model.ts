import {Market} from '../../entities/market/market.model';
import {MarketBox} from '../../entities/market-box/market-box.model';
import {BoxGroup} from '../../entities/box-group/box-group.model';
import {MarketVariety} from '../../entities/market-variety/market-variety.model';

export class MarketUi extends Market {
    constructor(
        public id?: number,
        public name?: string,
        public marketVarieties?: MarketVariety[],
        public boxGroups?: BoxGroup[],
        public marketBoxes?: MarketBox[],
        public colorClass?: string
    ) {
        super(id, name, marketVarieties, boxGroups, marketBoxes);
        this.colorClass = colorClass;
    }

}
