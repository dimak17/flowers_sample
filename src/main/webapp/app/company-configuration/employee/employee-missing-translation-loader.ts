import {MissingTranslationHandler, MissingTranslationHandlerParams} from '@ngx-translate/core';
/**
 * Created by platon on 29.03.17.
 */

export class EmployeeMissingTranslationLoader implements MissingTranslationHandler {
    handle(params: MissingTranslationHandlerParams) {
        throw new Error('translation not found for key=' + params.key);
    }
}
