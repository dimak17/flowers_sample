import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {BoxType} from '../../entities/box-type/box-type.model';
import {BoxGroupDTO} from './box-group-dto.model';
/**
 * Created by platon on 26.06.17.
 */
@Injectable()
export class BoxGroupingService {

    private resourceUrl = 'api/box-groups/company/dto';

    constructor(
        private http: Http
    ) {
    }

    getBoxGroupsByCurrentCompany(): Observable<BoxGroupDTO[]> {
        return this.http.get(`${this.resourceUrl}`).map((res: Response) => {
            return res.json();
        });
    }

    getBoxTypesByCurrentCompany(): Observable<BoxType> {
        return this.http.get(`api/box-types/company`).map((res: Response) => {
            return res.json();
        });
    }

    updateGroup(boxGroup: BoxGroupDTO): Observable<BoxGroupDTO> {
        const copy: BoxGroupDTO = Object.assign({}, boxGroup);
        return this.http.put('api/box-groups/dto', copy).map((res: Response) => {
            return res.json();
        });
    }

    createGroup(boxGroup: BoxGroupDTO): Observable<BoxGroupDTO> {
        const copy: BoxGroupDTO = Object.assign({}, boxGroup);
        return this.http.post('api/box-groups/dto', copy).map((res: Response) => {
            return res.json();
        });
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`api/box-groups/${id}`);
    }

    find(id: number): Observable<BoxGroupDTO> {
        return this.http.get(`api/box-groups/dto/${id}`).map((res: Response) => {
            return res.json();
        });
    }

}
