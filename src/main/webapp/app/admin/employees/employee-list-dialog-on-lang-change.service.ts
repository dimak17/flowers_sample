import {Injectable} from '@angular/core';
import {OnLangChange} from '../../shared/language/language.helper';
import {TranslateService} from '@ngx-translate/core';
import {PositionsTranslateService} from './employee-list-positions-translate.service';

@Injectable()
export class EmployeeListDialogOnLangChangeService implements OnLangChange {

    private _defaultLabel = 'Choose positions for employee';
    private _defaultMarketsLabel = 'Choose markets for employee';
    private language = '';
    private _positionsMap: Array<any> = [{key: 0, value: ''}];

    private _actSwitch = 'Activate';
    private _deactSwitch = 'Deactivate';

    constructor(
        private translateService: TranslateService,
        private positionTranslateService: PositionsTranslateService
    ) {}

    get defaultMarketsLabel(): string {
        return this._defaultMarketsLabel;
    }

    set defaultMarketsLabel(value: string) {
        this._defaultMarketsLabel = value;
    }

    get actSwitch(): string {
        return this._actSwitch;
    }

    set actSwitch(value: string) {
        this._actSwitch = value;
    }

    get deactSwitch(): string {
        return this._deactSwitch;
    }

    set deactSwitch(value: string) {
        this._deactSwitch = value;
    }

    get positionsMap(): Array<any> {
        return this._positionsMap;
    }

    set positionsMap(value: Array<any>) {
        this._positionsMap = value;
    }

    get defaultLabel(): string {
        return this._defaultLabel;
    }

    set defaultLabel(value: string) {
        this._defaultLabel = value;
    }

    onLangChange(lang: string) {
        if (this.language !== lang) {

            this._positionsMap = [];

            this.translateService.use(lang);

            this._positionsMap = this.positionTranslateService.positionEmployeeTranslate(this._positionsMap, this.translateService);

            this.translateService.get('flowersApp.companyUser.defaultPositionTitle')
                .subscribe((defTitleTranslation: string) => {
                this._defaultLabel = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.actSwitch')
                .subscribe((defTitleTranslation: string) => {
                this._actSwitch = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.deactSwitch')
                .subscribe((defTitleTranslation: string) => {
                this._deactSwitch = defTitleTranslation;
            });

            this.translateService.get('flowersApp.companyUser.defaultMarketsLabel')
                .subscribe((defTitleTranslation: string) => {
                this._defaultMarketsLabel = defTitleTranslation;
            });
        }
    }

}
