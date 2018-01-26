import { BoxGroup } from '../box-group';
import {BoxTypeUi} from '../../admin/box-type/box-type-ui.model';
export class BoxTypeGroup {
    constructor(
        public id?: number,
        public quantity?: number,
        public order?: number,
        public boxGroup?: BoxGroup,
        public boxType?: BoxTypeUi,
    ) {
    }
}
