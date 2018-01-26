import {Directive, ElementRef, EventEmitter, HostListener, Output} from '@angular/core';
/**
 * Created by platon on 14.07.17.
 */
@Directive({
    selector: '[jhi-clickOutside]'
})
export class ClickOutsideDirective {
    @Output() onClickOutside;

    constructor(
        private eRef: ElementRef
    ) {
        this.onClickOutside = new EventEmitter();
    }

    @HostListener('document:click', ['$event.path'])
    onGlobalClick(targetElementPath: Array<any>) {
        const elementRefInPath = targetElementPath.find(
            (e) => e === this.eRef.nativeElement
        );
        if (!elementRefInPath) {
            this.onClickOutside.emit(null);
        }
    }
}
