import {Injectable} from '@angular/core';
import {
    Http, RequestOptionsArgs, Headers, Response, ResponseContentType,
    RequestOptions, URLSearchParams
} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {ResponseWrapper} from '../../shared';
import {Company} from '../../entities/company/company.model';
import {BankDetails} from '../../entities/bank-details/bank-details.model';
import ResponseUtil from '../../util/response-util';

@Injectable()
export class BankDetailsService {

    private resourceUrl = 'api/bank-details';

    constructor(private http: Http) {
    }

    create(bankDetails: BankDetails): Observable<BankDetails> {
        const copy = this.convert(bankDetails);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(bankDetails: BankDetails): Observable<BankDetails> {
        const copy = this.convert(bankDetails);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<BankDetails> {
        return this.http.get(`${this.resourceUrl}/company/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(bankDetails: BankDetails): BankDetails {
        const copy: BankDetails = Object.assign({}, bankDetails);
        return copy;
    }

    findBankDetailsByCurrentCompanyId(): Observable<BankDetails> {
        return this.http.get(`${this.resourceUrl}/currentCompanyId`).map((res: Response) => {
            return res.json();
        });
    }

    downloadPDF(data: string): Observable<Response> {
        const options: RequestOptionsArgs = {
            responseType: ResponseContentType.Blob,
        };
        return this.http.get(this.resourceUrl + '/downloadBankDetails/' + data, options);
    }

    downloadUploadedPDF(type: string): Observable<Response> {
        const params = new URLSearchParams();
        params.append('type', type);
        const options: RequestOptionsArgs = {
            responseType: ResponseContentType.Blob, params
        };
        return this.http.get(this.resourceUrl + '/downloadUploadedBankDetails/', options);
    }

    showPDF(data): any {
        return this.http.get(this.resourceUrl + '/showBankDetails/' + data, {
            responseType: ResponseContentType.Blob
        }).map(
            (res) => {
                return new Blob([res.blob()], {type: 'application/pdf'});
        });
    }

    uploadFile(file: File, type: string): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('uploadFile', file);
        const headers = new Headers();
        headers.delete('Content-Type');
        headers.append('Accept', 'application/json');
        const options = new RequestOptions({headers});
        const params = new URLSearchParams();
        params.append('type', type);
        return this.http.request(this.resourceUrl + '/uploadBankDetails', {
            method: 'post',
            body: formData,
            headers: options.headers,
            params
        }).map((res: Response) => {
            return ResponseUtil.toJson(res);
        });
    }

    deleteUploadedFile(type: string, id: number): Observable<any> {
        const params = new URLSearchParams();
        params.append('type', type);
        const options: RequestOptionsArgs = {
            params
        };
        return this.http.delete(`${this.resourceUrl + '/deleteBankDetails'}/${id}`, options);
    }

    showUploadedFileName(): any {
        return this.http.get(this.resourceUrl + '/showUploadedFileName')
            .map((res) => res.json());
    };
}
