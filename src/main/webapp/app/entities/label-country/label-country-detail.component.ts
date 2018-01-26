import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { LabelCountry } from './label-country.model';
import { LabelCountryService } from './label-country.service';

@Component({
    selector: 'jhi-label-country-detail',
    templateUrl: './label-country-detail.component.html'
})
export class LabelCountryDetailComponent implements OnInit, OnDestroy {

    labelCountry: LabelCountry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private labelCountryService: LabelCountryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLabelCountries();
    }

    load(id) {
        this.labelCountryService.find(id).subscribe((labelCountry) => {
            this.labelCountry = labelCountry;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLabelCountries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'labelCountryListModification',
            (response) => this.load(this.labelCountry.id)
        );
    }
}
