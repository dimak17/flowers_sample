import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager} from 'ng-jhipster';

import {Season} from './season.model';
import {SeasonPopupService} from './season-popup.service';
import {SeasonService} from './season.service';
import {Company, CompanyService} from '../company';
import {Position, PositionService} from '../position';
import {ResponseWrapper} from '../../shared';
import {Pinch} from '../pinch/pinch.model';
import {PinchService} from '../../admin/pinch/pinch.service';

@Component({
    selector: 'jhi-season-dialog',
    templateUrl: './season-dialog.component.html'
})
export class SeasonDialogComponent implements OnInit {

    season: Season;
    isSaving: boolean;

    companies: Company[];

    positions: Position[];

    pinches: Pinch[];
    startDateDp: any;
    endDateDp: any;
    notifyStartDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private seasonService: SeasonService,
        private companyService: CompanyService,
        private positionService: PositionService,
        private pinchService: PinchService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.positionService.query()
            .subscribe((res: ResponseWrapper) => { this.positions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.pinchService.query()
            .subscribe((res: ResponseWrapper) => { this.pinches = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.season.id !== undefined) {
            this.subscribeToSaveResponse(
                this.seasonService.update(this.season));
        } else {
            this.subscribeToSaveResponse(
                this.seasonService.create(this.season));
        }
    }

    private subscribeToSaveResponse(result: Observable<Season>) {
        result.subscribe((res: Season) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Season) {
        this.eventManager.broadcast({ name: 'seasonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCompanyById(index: number, item: Company) {
        return item.id;
    }

    trackPositionById(index: number, item: Position) {
        return item.id;
    }

    trackPinchById(index: number, item: Pinch) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-season-popup',
    template: ''
})
export class SeasonPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seasonPopupService: SeasonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.seasonPopupService
                    .open(SeasonDialogComponent as Component, params['id']);
            } else {
                this.seasonPopupService
                    .open(SeasonDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
