import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, AlertService } from 'ng-jhipster';

import { BlockService } from './block.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { Block } from '../../entities/block/block.model';
import Collections = require('typescript-collections');

@Component( {
    selector: 'jhi-blocks',
    templateUrl: './block.component.html',
    styleUrls: ['block-dialog.scss']

} )
export class BlockComponent implements OnInit, OnDestroy {

    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    blocks: Block[];

    constructor(
        private blockService: BlockService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private parseLinks: ParseLinks,
        private principal: Principal
    ) {
        // this.itemsPerPage = ITEMS_PER_PAGE;
        // this.page = 0;
        // this.links = {
        //     last: 0
        // };
        this.predicate = 'id';
        this.reverse = true;
        this.blocks = [];
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(( account ) => {
            this.currentAccount = account;
        } );
        this.registerChangeInBlocks();
    }

    loadAll() {
        this.blockService.query( {
            // page: this.page,
            // size: this.itemsPerPage,
            sort: this.sort()
        } ).subscribe(
            ( res: ResponseWrapper ) => this.onSuccess( res.json ),
            ( res: ResponseWrapper ) => this.onError( res.json )
            );
    }

    reset() {
        this.page = 0;
        this.blocks = [];
        this.loadAll();
    }

    loadPage( page ) {
        this.page = page;
        this.loadAll();
    }

    ngOnDestroy() {
        this.eventManager.destroy( this.eventSubscriber );
    }

    private onSuccess( data/*, headers */) {
        // this.links = this.parseLinks.parse(headers.get('link'));
        // this.totalItems = headers.get('X-Total-Count');
        for ( let i = 0; i < data.length; i++ ) {
            this.blocks.push( data[i] );
        }
        if ( this.blocks && this.blocks.length ) {
            this.fillColor();
        }
    }

    public fillColor() {
        const collection = new Collections.Bag();
        let i = 0;
        let j = 0;

        while ( collection.size() < this.blocks.length ) {
            if ( j === (Object.keys(Colors).length / 2) ) {
                j = 0;
            }
            const color = Colors[j++].toLowerCase();
            collection.add(color);
            this.blocks[i].colorClass = color;
            localStorage.setItem(this.blocks[i].id.toString(), color);
            i++;
        }
    }

    trackId(index: number, item: Block) {
        return item.id;
    }

    registerChangeInBlocks() {
        this.eventSubscriber = this.eventManager.subscribe( 'blockListModification', ( response ) => this.reset() );
    }

    sort() {
        const result = [this.predicate + ',' + ( this.reverse ? 'asc' : 'desc' )];
        if ( this.predicate !== 'id' ) {
            result.push( 'id' );
        }
        return result;
    }

    private onError( error ) {
        this.alertService.error( error.message, null, null );
    }
}

export enum Colors { RED, GREEN, BLUE, GOLD, BROWN, VIOLET, PINK, AQUA }
