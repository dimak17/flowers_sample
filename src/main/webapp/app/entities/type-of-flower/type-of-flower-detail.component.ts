import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { TypeOfFlower } from './type-of-flower.model';
import { TypeOfFlowerService } from './type-of-flower.service';

@Component({
    selector: 'jhi-type-of-flower-detail',
    templateUrl: './type-of-flower-detail.component.html'
})
export class TypeOfFlowerDetailComponent implements OnInit, OnDestroy {

    typeOfFlower: TypeOfFlower;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private typeOfFlowerService: TypeOfFlowerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeOfFlowers();
    }

    load(id) {
        this.typeOfFlowerService.find(id).subscribe((typeOfFlower) => {
            this.typeOfFlower = typeOfFlower;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeOfFlowers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeOfFlowerListModification',
            (response) => this.load(this.typeOfFlower.id)
        );
    }
}
