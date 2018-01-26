import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {EventManager} from 'ng-jhipster';
import {CompanyUser} from '../../../entities/company-user/company-user.model';
import {Principal} from '../../../shared/auth/principal.service';
import {AvatarService} from './avatar.service';
/**
 * Created by platon on 13.06.17.
 */
@Component({
    selector: 'jhi-flowers-avatar',
    templateUrl: './avatar.component.html',
    styleUrls: ['./avatar.scss'],
    providers: [AvatarService]
})
export class AvatarComponent implements AfterViewInit, OnInit, OnDestroy {

    companyUser: CompanyUser;

    private getBase64File: Subscription;
    private getDefaultImage: Subscription;
    base64Image: string;
    initialName: string;
    profileImageUploaded: Subscription;
    profileImageDeleted: Subscription;
    img: any;
    constructor(
        private principal: Principal,
        private avatarService: AvatarService,
        private eventManager: EventManager,
        private elRef: ElementRef
    ) {}

    ngAfterViewInit(): void {
        this.img = this.elRef.nativeElement.querySelector('img');
    }

    ngOnInit(): void {
        this.getPublicInfo();
        this.imageReload();
    }

    imageReload() {
        this.profileImageUploaded = this.eventManager.subscribe('profileImageUploaded', (response) => this.reset());
        this.profileImageDeleted = this.eventManager.subscribe('profileInfoImageDeleted', (response) => this.reset());
    }

    reset() {
        this.loadAvatar();
    }

    getPublicInfo() {
        this.principal.identity().then((companyUser) => {
            this.companyUser = companyUser;
            this.initialName = this.companyUser.id.toString();
            this.loadAvatar();

        });
    }

    loadAvatar() {
        if (this.companyUser && this.companyUser.id) {
            this.getBase64File = this.avatarService.getBase64File(this.companyUser.id).subscribe((response) => {
                this.base64Image = response[0];
            });
        } else {
            this.getDefaultImage = this.avatarService.getDefaultImage().subscribe((response) => {
                this.base64Image = response[0];
            });
        }
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.profileImageUploaded);
        this.eventManager.destroy(this.profileImageDeleted);
    }
}
