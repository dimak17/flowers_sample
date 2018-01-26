import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'replaceWithNbsp'})
export class ReplaceWithNbsp implements PipeTransform {
    transform(value: string): string {
        return value.replace(/\s/g, '\xa0');
    }

}
