/**
 * Created by platon on 08.06.17.
 */
import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'firstLetterToUpperCase'})
export class FirstUpperCasePipe implements PipeTransform {
    transform(word: string): string {
        return this.firstLetterToUpperCase(word);
    }

    firstLetterToUpperCase(word: string): string {
        return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
    }
}
