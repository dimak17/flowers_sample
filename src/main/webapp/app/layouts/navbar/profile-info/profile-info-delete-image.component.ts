/**
 * Created by dima on 27.06.17.
 */
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import {ProfileInfoService} from './profile-info.service';
import {ProfileInfoPopupService} from './profile-info-popup.service';
import {CompanyUser} from '../../../entities/company-user/company-user.model';

@Component({
    selector: 'jhi-profile-info-delete-image',
    templateUrl: './profile-info-delete-image.component.html',
    styleUrls: ['./profile-info-delete-image.component.scss'],
    providers: [ProfileInfoService]
})
export class ProfileInfoDeleteImageComponent {

    companyUser: CompanyUser;

    constructor(
        private profileInfoService: ProfileInfoService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmImageDelete(id: number) {
        if (id) {
            this.profileInfoService.deleteImage(id).subscribe((response) => {
                this.eventManager.broadcast({
                    name: 'profileInfoImageDeleted',
                    content: 'Deleted an avatar'
                });
                this.activeModal.dismiss(true);
            });
            this.alertService.success('flowersApp.profile-info.deleted', {param: id}, null);
        } else {
            this.eventManager.broadcast({
                name: 'newProfileInfoImageDeleted',
                content: 'Deleted an avatar'
            });
            this.activeModal.dismiss(true);
        }
    }
}

@Component({
    selector: 'jhi-profile-info-delete-popup',
    template: '',
    providers: [ProfileInfoPopupService, ProfileInfoService]
})
export class ImageDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profileInfoPopupService: ProfileInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.profileInfoPopupService
                .open(ProfileInfoDeleteImageComponent, 'delete-avatar', params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
