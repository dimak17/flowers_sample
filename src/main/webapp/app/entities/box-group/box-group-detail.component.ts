import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { BoxGroup } from './box-group.model';
import { BoxGroupService } from './box-group.service';

@Component({
    selector: 'jhi-box-group-detail',
    templateUrl: './box-group-detail.component.html'
})
export class BoxGroupDetailComponent implements OnInit, OnDestroy {

    boxGroup: BoxGroup;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private boxGroupService: BoxGroupService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBoxGroups();
    }

    load(id) {
        this.boxGroupService.find(id).subscribe((boxGroup) => {
            this.boxGroup = boxGroup;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBoxGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'boxGroupListModification',
            (response) => this.load(this.boxGroup.id)
        );
    }
}
