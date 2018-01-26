import {Component, OnDestroy, OnInit} from '@angular/core';
import {TranslationService} from '../../shared/language/translation-service';
import {PolicyInfoTitle} from './policy-info.title';
import {Subscription} from 'rxjs/Subscription';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-flowers-policy',
    templateUrl: './policy-info.component.html',
    styleUrls: ['policy-info.scss'],
    providers: [TranslationService]
})
export class PolicyInfoComponent implements OnInit, OnDestroy {

    languageSubscriber: Subscription;
    titles: PolicyInfoTitle;
    selectedIndex = 0;
    private getTranslation: Subscription;

    onChange($event) {
        this.selectedIndex = $event.index;
    }

    constructor(private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private translationService: TranslationService, ) {
        this.jhiLanguageService.setLocations(['policy-info']);
    }

    ngOnInit(): void {
        this.getCurrentTabTitleNames();
        this.registerLangChange();
        this.languageHelper.addListener(this.translationService);
    }

    public getCurrentTabTitleNames() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.translationService.onLangChange(currentLang);
            this.getTranslation = this.translationService.getTranslation(currentLang, 'policy-info').subscribe((titles) => {
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
