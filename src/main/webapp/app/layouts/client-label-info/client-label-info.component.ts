import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {TranslationService} from '../../shared/language/translation-service';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {ClientLabelInfoTitle} from './client-label-info.title';

@Component({
    selector: 'jhi-client-label-info',
    templateUrl: './client-label.info.component.html',
    styleUrls: ['client-label-info.scss'],
    providers: [TranslationService]
})
export class ClientLabelInfoComponent implements OnInit, OnDestroy {

    languageSubscriber: Subscription;
    titles: ClientLabelInfoTitle;
    selectedIndex = 0;
    private getTranslation: Subscription;

    onChange($event) {
        this.selectedIndex = $event.index;
    }

    constructor(private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private translationService: TranslationService, ) {
        this.jhiLanguageService.setLocations(['client-label-info']);
    }

    ngOnInit(): void {
        this.getCurrentTabTitleNames();
        this.registerLangChange();
        this.languageHelper.addListener(this.translationService);
    }

    public getCurrentTabTitleNames() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.translationService.onLangChange(currentLang);
            this.getTranslation = this.translationService.getTranslation(currentLang, 'client-label-info').subscribe((titles) => {
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
