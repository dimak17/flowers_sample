import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Block } from './block.model';
import { BlockService } from './block.service';

@Component({
    selector: 'jhi-block-detail',
    templateUrl: './block-detail.component.html'
})
export class BlockDetailComponent implements OnInit, OnDestroy {

    block: Block;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private blockService: BlockService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBlocks();
    }

    load(id) {
        this.blockService.find(id).subscribe((block) => {
            this.block = block;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBlocks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'blockListModification',
            (response) => this.load(this.block.id)
        );
    }
}
