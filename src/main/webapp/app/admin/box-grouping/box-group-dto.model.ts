/**
 * Created by platon on 02.07.17.
 */
import {BoxType} from '../../entities/box-type/box-type.model';

export class BoxGroupDTO {
    constructor(
        public id?: number,
        public boxTypes?: BoxType[],
        public quantities?: number[]
    ) {
    }
}
