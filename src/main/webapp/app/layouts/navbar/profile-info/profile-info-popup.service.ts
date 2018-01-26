import {Injectable, Component, OnDestroy} from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {ProfileInfoService} from './profile-info.service';
import {CompanyUser} from '../../../entities/company-user/company-user.model';
import {Subscription} from 'rxjs/Subscription';

@Injectable()
export class ProfileInfoPopupService implements OnDestroy {
    private isOpen = false;
    private getProfileId: Subscription;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private profileInfoService: ProfileInfoService

    ) {}

    open(component: Component, windowClass: string, id?: number | any): NgbModalRef {

        this.isOpen = true;

        if (id) {
            this.getProfileId = this.profileInfoService.find(id).subscribe((companyUser: CompanyUser) => {
                this.varietyModalRef(component, windowClass, companyUser);
            });
        } else {
            return this.varietyModalRef(component, windowClass);
        }
    }

    varietyModalRef(component: Component, windowClass: string, companyUser?: CompanyUser): NgbModalRef {
        const modalRef = this.modalService.open(component, {windowClass});
        modalRef.componentInstance.companyUser = companyUser;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }

    ngOnDestroy(): void {
        this.getProfileId.unsubscribe();
    }
}
