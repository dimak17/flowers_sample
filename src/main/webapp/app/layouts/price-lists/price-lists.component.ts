import {Component, OnDestroy, OnInit} from '@angular/core';
import {PriceListsTitle} from './price-lists.title';
import {Subscription} from 'rxjs/Subscription';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {TranslationService} from '../../shared/language/translation-service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-flowers-price-lists',
    templateUrl: './price-lists.component.html',
    styleUrls: ['price-lists.scss'],
    providers: [TranslationService]
})
export class PriceListsComponent implements OnInit, OnDestroy {

    languageSubscriber: Subscription;
    titles: PriceListsTitle;
    private getTranslation: Subscription;

    constructor(private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private translationService: TranslationService, ) {
        this.jhiLanguageService.setLocations(['price-lists']);
    }

    ngOnInit(): void {
        this.getCurrentTabTitleNames();
        this.registerLangChange();
        this.languageHelper.addListener(this.translationService);
    }

    public getCurrentTabTitleNames() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.translationService.onLangChange(currentLang);
            this.getTranslation = this.translationService.getTranslation(currentLang, 'price-lists').subscribe((titles) => {
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
