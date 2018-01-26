import { Injectable } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { TranslateService, TranslationChangeEvent, LangChangeEvent } from 'ng2-translate/ng2-translate';

import { LANGUAGES } from './language.constants';

@Injectable()
export class JhiLanguageHelper {

    private langListeners: OnLangChange[] = [];

    constructor(private translateService: TranslateService, private titleService: Title, private router: Router) {
        this.init();
    }

    getAll(): Promise<any> {
        return Promise.resolve(LANGUAGES);
    }

    private init() {
        this.translateService.onTranslationChange.subscribe((event: TranslationChangeEvent) => {
            if (this.langListeners.length > 0) {
                console.log('onLangChange triggered');
                this.langListeners.forEach((listener) => {

                    listener.onLangChange(event.lang);
                });
            }
        });

        this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            if (this.langListeners.length > 0) {
                console.log('onLangChange triggered');
                this.langListeners.forEach((listener) => listener.onLangChange(event.lang));
            }
        });
    }

    addListener(langListener: OnLangChange) {
        this.langListeners.push(langListener);
    }

    removeListener(langListener: OnLangChange) {
        for (let i = 0; i < this.langListeners.length; i++) {
            if (this.langListeners[i] === langListener) {
                this.langListeners.splice(i, 1);
            }
        }
    }
}

export interface OnLangChange {
    onLangChange(lang: string);
}
