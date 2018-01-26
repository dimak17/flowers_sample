import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
/**
 * Created by platon on 08.03.17.
 */

@Injectable()
export class CompanyCreatorEmailService {
    private sendMailUrl = 'api/company-creator-email';

    constructor(private http: Http) { }

    sendMail(email: string): Observable<string> {
        return this.http.post(this.sendMailUrl, email).map((res: Response) => {
            return 'success';
        });
    }

}
