import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { BoxTypeGroup } from './box-type-group.model';
import { BoxTypeGroupService } from './box-type-group.service';

@Component({
    selector: 'jhi-box-type-group-detail',
    templateUrl: './box-type-group-detail.component.html'
})
export class BoxTypeGroupDetailComponent implements OnInit, OnDestroy {

    boxTypeGroup: BoxTypeGroup;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private boxTypeGroupService: BoxTypeGroupService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBoxTypeGroups();
    }

    load(id) {
        this.boxTypeGroupService.find(id).subscribe((boxTypeGroup) => {
            this.boxTypeGroup = boxTypeGroup;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBoxTypeGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'boxTypeGroupListModification',
            (response) => this.load(this.boxTypeGroup.id)
        );
    }
}
