import {Http, Response, Headers, ResponseContentType, RequestOptionsArgs} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {Company} from '../../entities/company/company.model';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
/**
 * Created by alex on 20.06.17.
 */

@Injectable()
export class FarmDataService {

    private resourceUrl = 'api/company-users';
    private companyUrl = 'api/companies';
    private typeOfFlowersUrl = 'api/type-of-flowers';

    constructor(private http: Http) {
    }

    private convert(company: Company): Company {
        const copy: Company = Object.assign({}, company);
        return copy;
    }

    update(company: Company): Observable<Company> {
        const copy = this.convert(company);
        return this.http.put(this.companyUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Company> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    getCurrentCompany(): Observable<Company> {
        return this.http.get(`${this.companyUrl}/current`).map((res): Response => {
           return res.json();
        });
    }

    findAllTypeOfFlowerByIdCompany(): Observable<TypeOfFlower[]> {
        return this.http.get(`${this.typeOfFlowersUrl}/company/`).map((res: Response) => {
            return res.json();
        });
    }

    createTypeOfFlower(typeOfFlower: TypeOfFlower): Observable<TypeOfFlower> {
        const copy = this.convert(typeOfFlower);
        return this.http.post(this.typeOfFlowersUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    deleteTypeOfFlower(id: number): Observable<Response> {
        return this.http.delete(`${this.typeOfFlowersUrl}/${id}`);
    }

    makeFileRequest(file: File, fileName: string): Observable<any> {
        return this.http.post(this.companyUrl + '/uploadCompanyImage', JSON.stringify({base64file: file, name: fileName}), {
            headers: new Headers({'Content-Type': 'application/json'})
        });
    }

    getBase64File(): Observable<string[]> {
        return this.http.get(`${this.companyUrl}/image`).map((res: Response) => {
            return res.json();
        });
    }

    getDefaultImage(): Observable<string[]> {
        return this.http.get(this.companyUrl + '/default-image').map((res: Response) => {
            return res.json();
        });
    }

    deleteImage(): Observable<Response> {
        return this.http.delete(`${this.companyUrl}/image-delete`);

    }

    downloadPDF(): Observable<Response> {
        const options: RequestOptionsArgs = {
                responseType: ResponseContentType.Blob,
            };

        return this.http.get(this.companyUrl + '/download', options);
    }

}
