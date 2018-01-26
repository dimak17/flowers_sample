import {AfterViewInit, Component, ElementRef, OnInit, Renderer} from '@angular/core';
import {JhiLanguageService} from 'ng-jhipster';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {CompanyCreatorEmailService} from './company-creator-email.service';
/**
 * Created by platon on 06.03.17.
 */
@Component({
    selector: 'jhi-company-creator-email',
    templateUrl: './company-creator-email.component.html'
})
export class CompanyCreatorEmailComponent implements OnInit, AfterViewInit {

    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    success: boolean;
    activateAccount: any;
    modalRef: NgbModalRef;

    constructor(
        private languageService: JhiLanguageService,
        private companyCreatorEmailService: CompanyCreatorEmailService,
        private elementRef: ElementRef,
        private renderer: Renderer
    ) {
        this.languageService.setLocations(['companyCreator']);
    }

    ngOnInit() {
        this.success = false;
        this.activateAccount = {};
    }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#email'), 'focus', []);
    }

    send() {
        this.doNotMatch = null;
        this.error = null;
        this.errorEmailExists = null;
        this.languageService.getCurrent().then((key) => {
            // TODO language selection
            this.companyCreatorEmailService.sendMail(this.activateAccount.email).subscribe(() => {
                this.success = true;
            }, (response) => this.processError(response));
        });
    }

    private processError(response) {
        this.success = null;
        if (response.status === 400 && response._body === 'e-mail address already in use') {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }
}
