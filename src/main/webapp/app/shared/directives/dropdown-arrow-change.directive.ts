import {
    AfterViewInit, Directive, ElementRef, EventEmitter, HostListener, Output, Renderer,
    Renderer2
} from '@angular/core';
/**
 * Created by platon on 14.09.17.
 */
@Directive({
    selector: '[dropdownArrowChange]'
})
export class DropdownArrowChangeDirective implements AfterViewInit{
    constructor(
        private eRef: ElementRef,
        private rd: Renderer2
    ) {
    }

    ngAfterViewInit(): void {
        this.eRef.nativeElement.querySelectorAll('.fa-caret-down').forEach((el) => {
            this.rd.removeClass(el, 'fa-caret-down');
            this.rd.addClass(el, 'fa-angle-down');
        });
    }

}
