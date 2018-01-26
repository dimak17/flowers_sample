import {Injectable, Component, OnDestroy} from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SeasonService } from './season.service';
import {Season} from '../../entities/season/season.model';
import {Subscription} from 'rxjs/Subscription';

@Injectable()
export class SeasonPopupService implements OnDestroy{
    private find: Subscription;

    ngOnDestroy(): void {
        this.find.unsubscribe();
    }

    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private seasonService: SeasonService

    ) {}

    open(component: Component, windowClass: string, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.find = this.seasonService.find(id).subscribe((season) => {
                if (season.startDate) {
                    season.startDate = {
                        year: season.startDate.getFullYear(),
                        month: season.startDate.getMonth(),
                        day: season.startDate.getDate()
                    };
                }
                if (season.endDate) {
                    season.endDate = {
                        year: season.endDate.getFullYear(),
                        month: season.endDate.getMonth(),
                        day: season.endDate.getDate()
                    };
                }
                if (season.notifyStartDate) {
                    season.notifyStartDate = {
                        year: season.notifyStartDate.getFullYear(),
                        month: season.notifyStartDate.getMonth(),
                        day: season.notifyStartDate.getDate()
                    };
                }
                this.seasonModalRef(component, windowClass, season);
            });
        } else {
            return this.seasonModalRef(component, windowClass, new Season());
        }
    }

    seasonModalRef(component: Component, windowClass: string, season: Season): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.season = season;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
