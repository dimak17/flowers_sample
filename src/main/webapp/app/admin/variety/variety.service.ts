import { Injectable } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { ResponseWrapper, createRequestOption } from '../../shared';
import {Variety} from '../../entities/variety/variety.model';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import ResponseUtil from '../../util/response-util';

@Injectable()
export class VarietyService {

    private resourceUrl = 'api/varieties';
    private typeOfFlowerUrl = 'api/type-of-flowers';

    constructor(private http: Http) {
    }

    create(variety: Variety): Observable<Variety> {
        const copy = this.convert(variety);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(variety: Variety): Observable<Variety> {
        const copy = this.convert(variety);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Variety> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`)
            .map((res) => {
                return ResponseUtil.toJson(res);
            })
            .catch((error) => {
                return Observable.throw(error);
            });
    }

    deleteImage(id: number): Observable<Response> {
            return this.http.delete(`${this.resourceUrl}/${id}/image`);

    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(variety: Variety): Variety {
        const copy: Variety = Object.assign({}, variety);
        return copy;
    }

    makeFileRequest(file: File, fileName: string): Observable<any> {
            return this.http.post(this.resourceUrl + '/uploadVarietyImage', JSON.stringify({base64file: file, name: fileName}), {
                headers: new Headers({'Content-Type': 'application/json'})
            });
    }

    findAllTypeOfFlowers(): Observable<TypeOfFlower[]> {
        return this.http.get(this.typeOfFlowerUrl + '/company').map((res: Response) => {
            return res.json();
        });
    }

    getVarietiesByTypeOfFlowers(typeOfFlowerId: number): Observable<Variety[]> {
        return this.http.get(`${this.resourceUrl}/type-of-flower/${typeOfFlowerId}`).map((res: Response) => {
            return res.json();
        });
    }

    getAllBase64FilesByCurrentCompanyAndTypeOfFlower(typeOfFlowersId: number): Observable<string[]> {
        return this.http.get(`${this.resourceUrl}/type-of-flower-images/${typeOfFlowersId}`).map((res: Response) => {
            return res.json();
        });
    }

    getBase64FileById(varietyId: number): Observable<string[]> {
        return this.http.get(`${this.resourceUrl}/image/${varietyId}`).map((res: Response) => {
            return res.json();
        });
    }

    getDefaultImage(): Observable<string[]> {
        return this.http.get(this.resourceUrl + '/default-image').map((res: Response) => {
            return res.json();
        });
    }
}
