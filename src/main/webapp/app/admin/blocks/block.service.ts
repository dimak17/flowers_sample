import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ResponseWrapper, createRequestOption } from '../../shared';
import { Variety } from '../../entities/variety/variety.model';
import { Block } from '../../entities/block/block.model';
import { TypeOfFlower } from '../../entities/type-of-flower/type-of-flower.model';

@Injectable()
export class BlockService {

    private resourceUrl = 'api/varieties';
    private blockResourceUrl = 'api/blocks';
    private typeOfFlowerUrl = 'api/type-of-flowers';

    constructor(private http: Http) {}

    create(block: Block): Observable<Block> {
        const copy = this.convert(block);
        return this.http.post(this.blockResourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    getCompanyUser(login: string): Observable<any> {
        return this.http.get('api/company-users/query?login=' + login).map((res: Response) => res.json());
    }

    update(block: Block): Observable<Block> {
        const copy = this.convert(block);
        return this.http.put(this.blockResourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    findVarietiesByIdCompany(): Observable<Variety[]> {
        return this.http.get(`${this.resourceUrl}/company`).map((res: Response) => {
            return res.json();
        });
    }

    findAllBlocks(): Observable<Block[]> {
        return this.http.get(`${this.blockResourceUrl}`).map((res: Response) => {
            return res.json();
        });
    }

    findBlockById(id: number): Observable<Block> {
        return this.http.get(`${this.blockResourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findAllTypeOfFlowerByIdCompany(): Observable<TypeOfFlower[]> {
        return this.http.get(`${this.typeOfFlowerUrl}/company`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.blockResourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.blockResourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(company: Block): Block {
        const copy: Block = Object.assign({}, company);
        return copy;
    }
}
