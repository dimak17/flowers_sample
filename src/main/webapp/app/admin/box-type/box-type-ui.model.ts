import {BoxType} from '../../entities/box-type/box-type.model';
import {Company} from '../../entities/company/company.model';
/**
 * Created by dima on 08.06.17.
 */
export class BoxTypeUi extends BoxType {

    constructor(
        public id?: number,
        public shortName?: string,
        public fullName?: string,
        public boxSize?: string,
        public company?: Company,
        public colorClass?: string
    ) {
        super(id, shortName, fullName, boxSize, company);
        this.colorClass = colorClass;
    }
}
