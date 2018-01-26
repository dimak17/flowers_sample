/**
 * Created by platon on 08.06.17.
 */
import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'wordsFirstLetterToUpperCase'})
export class WordsToUpperCasePipe implements PipeTransform {
    transform(words: string[]): string {
        return words.map((w) => this.firstLetterToUpperCase(w)).reduce((a, b) => a + ' ' + b);
    }

    firstLetterToUpperCase(word: string): string {
        return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
    }
}
