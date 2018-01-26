import {Component, OnDestroy, OnInit} from '@angular/core';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {Subscription} from 'rxjs/Subscription';
import {CompanyInfoTitle} from './company-info.title';
import {TranslationService} from '../../shared/language/translation-service';

@Component({
    selector: 'jhi-flowers-company-info',
    templateUrl: './company-info.component.html',
    styleUrls: ['company-info.scss'],
    providers: [TranslationService]
})
export class CompanyInfoComponent implements OnInit, OnDestroy {

    languageSubscriber: Subscription;
    titles: CompanyInfoTitle;
    selectedIndex = 0;
    private getTranslation: Subscription;

    onChange($event) {
        this.selectedIndex = $event.index;
    }

    constructor(private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private translationService: TranslationService, ) {
        this.jhiLanguageService.setLocations(['company-info']);
    }

    ngOnInit(): void {
        this.getCurrentTabTitleNames();
        this.registerLangChange();
        this.languageHelper.addListener(this.translationService);
    }

    public getCurrentTabTitleNames() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.translationService.onLangChange(currentLang);
            this.getTranslation = this.translationService.getTranslation(currentLang, 'company-info').subscribe((titles) => {
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
