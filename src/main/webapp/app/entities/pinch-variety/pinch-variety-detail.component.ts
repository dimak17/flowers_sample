import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PinchVariety } from './pinch-variety.model';
import { PinchVarietyService } from './pinch-variety.service';

@Component({
    selector: 'jhi-pinch-variety-detail',
    templateUrl: './pinch-variety-detail.component.html'
})
export class PinchVarietyDetailComponent implements OnInit, OnDestroy {

    pinchVariety: PinchVariety;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private pinchVarietyService: PinchVarietyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPinchVarieties();
    }

    load(id) {
        this.pinchVarietyService.find(id).subscribe((pinchVariety) => {
            this.pinchVariety = pinchVariety;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPinchVarieties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pinchVarietyListModification',
            (response) => this.load(this.pinchVariety.id)
        );
    }
}
