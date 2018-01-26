import {Component, OnDestroy, OnInit} from '@angular/core';
import {TranslationService} from '../../shared/language/translation-service';
import {Subscription} from 'rxjs/Subscription';
import {CargoAgenciesTitle} from './cargo-agencies.title';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-cargo-agencies',
    templateUrl: './cargo-agencies.component.html',
    styleUrls: ['cargo-agencies.scss'],
    providers: [TranslationService]
})
export class CargoAgenciesComponent implements OnInit, OnDestroy {

    languageSubscriber: Subscription;
    titles: CargoAgenciesTitle;
    selectedIndex = 0;
    private getTranslation: Subscription;

    onChange($event) {
        this.selectedIndex = $event.index;
    }

    constructor(private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private translationService: TranslationService, ) {
        this.jhiLanguageService.setLocations(['cargo-agencies']);
    }

    ngOnInit(): void {
        this.getCurrentTabTitleNames();
        this.registerLangChange();
        this.languageHelper.addListener(this.translationService);
    }

    public getCurrentTabTitleNames() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.translationService.onLangChange(currentLang);
            this.getTranslation = this.translationService.getTranslation(currentLang, 'cargo-agencies').subscribe((titles) => {
                    this.titles = titles;
                },
                (error) => {
                    console.error(error);
                }
            );
        });
    }

    registerLangChange() {
        this.languageSubscriber = this.eventManager.subscribe('languageChanged', (response) => {
            this.getCurrentTabTitleNames();
        });
    }

    ngOnDestroy(): void {
        this.eventManager.destroy(this.languageSubscriber);
        this.getTranslation.unsubscribe();
    }
}
