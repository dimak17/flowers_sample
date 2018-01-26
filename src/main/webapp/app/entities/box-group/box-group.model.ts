import { BoxTypeGroup } from '../box-type-group';
import { Market } from '../market';
import {Company} from '../company/company.model';
export class BoxGroup {
    constructor(
        public id?: number,
        public boxTypeGroups?: BoxTypeGroup,
        public markets?: Market,
        public company?: Company
    ) {
    }
}
