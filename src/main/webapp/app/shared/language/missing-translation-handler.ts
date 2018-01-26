import {MissingTranslationHandler, MissingTranslationHandlerParams} from '@ngx-translate/core';

export class MissingTranslationHandlerImpl implements MissingTranslationHandler {
    handle(params: MissingTranslationHandlerParams) {
        throw new Error('translation not found for key=' + params.key);
    }
}
