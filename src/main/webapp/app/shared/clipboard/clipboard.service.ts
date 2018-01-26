import { DOCUMENT } from '@angular/platform-browser';
import {Inject, Injectable} from '@angular/core';

@Injectable()
export class ClipboardService {

    public value: string;
    private dom: Document;

    constructor( @Inject( DOCUMENT ) dom: any ) {
        this.value = '';
        this.dom = dom;
    }

    public copy( value: string ): Promise<string> {
        const promise = new Promise<string>(
            ( resolve, reject ): void => {
                let textarea = null;
                try {
                    textarea = this.dom.createElement( 'textarea' );
                    textarea.style.height = '0px';
                    textarea.style.left = '-100px';
                    textarea.style.opacity = '0';
                    textarea.style.position = 'fixed';
                    textarea.style.top = '-100px';
                    textarea.style.width = '0px';
                    this.dom.body.appendChild( textarea );

                    textarea.value = value;
                    textarea.select();

                    this.dom.execCommand( 'copy' );
                    resolve( value );
                } finally {

                    if ( textarea && textarea.parentNode ) {

                        textarea.parentNode.removeChild( textarea );
                    }
                }
            }
        );
        return( promise );
    }

    copyToClipboard() {
        this.copy( this.value )
            .catch(
                ( error: Error ) : void => {
                    console.error(error.message);
                }
            );
    }
}
