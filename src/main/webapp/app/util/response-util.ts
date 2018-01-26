import {Response} from '@angular/http';
/**
 * Created by platon on 06.03.17.
 */
export default class ResponseUtil {

    public static toJson(res: Response) {
        let body;
        if (res.text()) {
            body = res.json();
        }
        return body || {};
    }

}
