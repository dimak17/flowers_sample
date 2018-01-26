import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Disease } from './disease.model';
import { DiseaseService } from './disease.service';

@Component({
    selector: 'jhi-disease-detail',
    templateUrl: './disease-detail.component.html'
})
export class DiseaseDetailComponent implements OnInit, OnDestroy {

    disease: Disease;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private diseaseService: DiseaseService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['disease']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.diseaseService.find(id).subscribe((disease) => {
            this.disease = disease;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
