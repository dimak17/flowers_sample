import {Injectable} from '@angular/core';
import {Http, Response, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {CompanyUser} from '../../../entities/company-user/company-user.model';

@Injectable()
export class ProfileInfoService {
    private resourceGetUrl = 'api/company-users/query';
    private resourceUpdateUrl = 'api/company-users';
    private resourceChangeAccountUrl = 'api/company-users/change_account_data';

    constructor(private http: Http) {
    }

    updateCompanyUser(companyUser: CompanyUser): Observable<any> {
        const copy = this.convert(companyUser);
        return this.http.put(this.resourceUpdateUrl, copy);
    }

    getPublicInfo(login: string): Observable<any> {
        return this.http.get(this.resourceGetUrl + '?login=' + login).map((res: Response) => res.json());
    }

    changeAccountData(data): Observable<any> {
        console.log(JSON.stringify(data));
        return this.http.post(this.resourceChangeAccountUrl, JSON.stringify(data), {
            headers: new Headers({'Content-Type': 'application/json'})
        });
    }

    private convert(companyUser: CompanyUser): CompanyUser {
        const copy: CompanyUser = Object.assign({}, companyUser);
        return copy;
    }

    find(id: number): Observable<CompanyUser> {
        return this.http.get(`${this.resourceUpdateUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    deleteImage(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUpdateUrl}/${id}/image-delete`);

    }

    makeFileRequest(file: File, fileName: string): Observable<any> {
        return this.http.post(this.resourceUpdateUrl + '/uploadCompanyUserImage', JSON.stringify({base64file: file, name: fileName}), {
            headers: new Headers({'Content-Type': 'application/json'})
        });
    }

    getBase64File(id: number): Observable<string[]> {
        return this.http.get(`${this.resourceUpdateUrl}/image/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    getDefaultImage(): Observable<string[]> {
        return this.http.get(this.resourceUpdateUrl + '/default-image').map((res: Response) => {
            return res.json();
        });
    }
}
