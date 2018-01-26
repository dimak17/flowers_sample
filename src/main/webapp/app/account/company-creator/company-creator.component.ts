import {AfterViewInit, Component, ElementRef, OnInit, Renderer} from '@angular/core';
import {JhiLanguageService} from 'ng-jhipster';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {CompanyCreatorService} from './company-creator.service';
import {ActivatedRoute} from '@angular/router';
/**
 * Created by platon on 06.03.17.
 */
@Component({
    selector: 'jhi-company-creator',
    templateUrl: './company-creator.component.html'
})
export class CompanyCreatorComponent implements OnInit, AfterViewInit {

    doNotMatch: string;
    error: string;
    errorActivationKey: boolean;
    errorEmailExists: string;
    createAccount: any;
    success: boolean;
    modalRef: NgbModalRef;

    constructor(
        private languageService: JhiLanguageService,
        private companyCreatorService: CompanyCreatorService,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private route: ActivatedRoute
    ) {
        this.languageService.setLocations(['companyCreator']);
    }

    ngOnInit() {
        this.success = false;
        this.errorActivationKey = true;
        this.createAccount = {};
        this.route.queryParams.subscribe((params) => {
            this.companyCreatorService.isActiveKey(params['key']).subscribe(
                () => { this.errorActivationKey = false; },
                () => { this.errorActivationKey = true; }
            );
        });

    }

    ngAfterViewInit() {
        if (!this.errorActivationKey) {
            this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#firstName'), 'focus', []);
        }
    }

    activate() {
        this.doNotMatch = null;
        this.error = null;
        this.errorEmailExists = null;
        this.route.queryParams.subscribe(
            (params) => {
                this.createAccount.activationKey = params['key'];
            },
            (error) => console.log('there is some problems!')

        );
        this.languageService.getCurrent().then((key) => {
            this.createAccount.langKey = key;

            this.companyCreatorService.create(this.createAccount).subscribe((resp) => {
                this.success = true;
                // TODO hide form here
            }, (resp) => {
                this.processError(resp);
            });
        });
    }

    private processError(response) {
        this.success = null;
        if (response.status === 500 && response._body === 'e-mail address already in use') {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }
}
