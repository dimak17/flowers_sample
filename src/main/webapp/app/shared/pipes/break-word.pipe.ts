/**
 * Created by alex
 */
import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'breakWord'})
export class BreakWordPipe implements PipeTransform {

    transform(value: string, breakNumber: number): string {
        if (value.length > breakNumber) {
            return value.slice(0, breakNumber) + '\n' + value.slice(breakNumber, value.length - 1);
        } else {
            return value;
        }
    }
}
