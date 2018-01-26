import {Injectable} from '@angular/core';
@Injectable()
export class ProfileSharedService {
    data: ProfileSharedData;

    saveData(data: ProfileSharedData) {
        this.data = data;
    }

    getData(): ProfileSharedData {
        return this.data;
    }
}

export interface ProfileSharedData {
    firstName: string;
    lastName: string;
}
