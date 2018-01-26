import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';
import { CargoEmployeePosition } from './cargo-employee-position.model';
import { CargoEmployeePositionService } from './cargo-employee-position.service';
import { Principal, ResponseWrapper } from '../../shared';
import { Colors } from '../../shared/constants/colors.constants';
import { CargoEmployeePositionTranslationService } from './cargo-employee-position-translation-service';
import { CargoEmployeePositionTranslation } from './cargo-employee-position-translation';
import { JhiLanguageHelper} from '../../shared/language/language.helper';
import Collections = require('typescript-collections');

@Component({
    selector: 'jhi-flower-cargo-employee-position',
    templateUrl: './cargo-employee-position.component.html',
    styleUrls: ['/cargo-employee-position.component.scss'],
    providers: [CargoEmployeePositionTranslationService]
})
export class CargoEmployeePositionComponent implements OnInit, OnDestroy {

    cargoEmployeePositions: CargoEmployeePosition[];
    currentAccount: any;
    cargoEmployeePositionTranslation: CargoEmployeePositionTranslation;
    loadComponent = false;
    languageSubscriber: Subscription;
    links: any;
    predicate: any;
    reverse: any;

    constructor(private cargoEmployeePositionService: CargoEmployeePositionService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private cargoEmployeePositionTranslationService: CargoEmployeePositionTranslationService,
                private principal: Principal) {

        this.languageHelper.addListener(this.cargoEmployeePositionTranslationService);
        this.getCurrentLanguageInformation();

        this.cargoEmployeePositions = [];
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.cargoEmployeePositionService.getAllCargoEmployeePositionsByCurrentCompany({
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    reset() {
        this.cargoEmployeePositions = [];
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCargoEmployeePosition();
        this.registerLangChange();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.languageSubscriber);
    }

    public getCurrentLanguageInformation() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.cargoEmployeePositionTranslationService.getTranslation(currentLang, 'cargoEmployeePosition').subscribe((res) => {
                this.cargoEmployeePositionTranslation = res;
                this.loadComponent = true;
            });
        });
    }

    registerLangChange() {
        this.languageSubscriber = this.eventManager.subscribe('languageChangedCargoEmployeePosition', (response) => {
            this.getCurrentLanguageInformation();
        });
    }

    trackId(index: number, item: CargoEmployeePosition) {
        return item.id;
    }

    registerChangeInCargoEmployeePosition() {
        this.languageSubscriber = this.eventManager.subscribe('cargoEmployeePositionListModification', (response) => this.reset());
    }

    private onSuccess(data, headers) {
        for (let i = 0; i < data.length; i++) {
            this.cargoEmployeePositions.push(data[i]);
        }
        if (this.cargoEmployeePositions && this.cargoEmployeePositions.length) {
            this.fillColor();
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    public fillColor() {
        const collection = new Collections.Bag();
        let i = 0;
        let j = 0;
        while (collection.size() < this.cargoEmployeePositions.length) {
            if (j === (Object.keys(Colors).length / 2)) {
                j = 0;
            }
            const color = Colors[j++].toLowerCase();
            collection.add(color);
            this.cargoEmployeePositions[i].colorClass = color;
            localStorage.setItem(this.cargoEmployeePositions[i].id.toString(), color);
            i++;
        }
    }
}
