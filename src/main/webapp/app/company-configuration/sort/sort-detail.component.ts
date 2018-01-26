import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {JhiLanguageService} from 'ng-jhipster';
import {Sort} from './sort.model';
import {SortService} from './sort.service';

@Component({
    selector: 'jhi-sort-detail',
    templateUrl: './sort-detail.component.html'
})
export class SortDetailComponent implements OnInit, OnDestroy {

    sort: Sort;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private sortService: SortService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['sort']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.sortService.find(id).subscribe((sort) => {
            this.sort = sort;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
