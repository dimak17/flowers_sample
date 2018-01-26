
import {TranslateService} from '@ngx-translate/core';
import {Observable} from 'rxjs/Observable';

export class PositionsTranslateService {

    public positionEmployeeTranslate(positionsMap: Array<any>, translateService: TranslateService): Array<any> {

        translateService.get('flowersApp.companyUser.salesManager')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 1, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.salesAssistant')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 2, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.companyOwner')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 3, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.agronomEngineer')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 4, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.postHarvestManager')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 5, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.generalManager')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 6, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.accountantMananger')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 7, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.accountantAssistant')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 8, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.claimsMananger')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 9, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.claimsAssistant')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 10, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.coordinationManager')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 11, value: defTitleTranslation});
                }
            });

        translateService.get('flowersApp.companyUser.systemManager')
            .subscribe((defTitleTranslation: string) => {
                if (positionsMap.length < 13) {
                    positionsMap.push({key: 12, value: defTitleTranslation});
                }
            });

        return positionsMap;
    }
}
