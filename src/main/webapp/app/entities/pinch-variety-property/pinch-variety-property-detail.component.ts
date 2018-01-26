import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { PinchVarietyProperty } from './pinch-variety-property.model';
import { PinchVarietyPropertyService } from './pinch-variety-property.service';

@Component({
    selector: 'jhi-pinch-variety-property-detail',
    templateUrl: './pinch-variety-property-detail.component.html'
})
export class PinchVarietyPropertyDetailComponent implements OnInit, OnDestroy {

    pinchVarietyProperty: PinchVarietyProperty;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private pinchVarietyPropertyService: PinchVarietyPropertyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPinchVarietyProperties();
    }

    load(id) {
        this.pinchVarietyPropertyService.find(id).subscribe((pinchVarietyProperty) => {
            this.pinchVarietyProperty = pinchVarietyProperty;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPinchVarietyProperties() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pinchVarietyPropertyListModification',
            (response) => this.load(this.pinchVarietyProperty.id)
        );
    }
}
