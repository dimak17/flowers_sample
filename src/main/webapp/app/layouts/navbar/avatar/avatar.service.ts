import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class AvatarService {
    private resourceUpdateUrl = 'api/company-users';

    constructor(private http: Http) {
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
