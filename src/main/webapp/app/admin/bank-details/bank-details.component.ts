import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {BankDetailsService} from './bank-details.service';
import {BankDetails} from '../../entities/bank-details/bank-details.model';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ClipboardService} from '../../shared/clipboard/clipboard.service';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {LATIN_VALIDATION_WITH_ENTER} from '../../shared/constants/validation.constants';
import * as FileSaver from 'file-saver';
import {Message} from 'primeng/primeng';
import {isUndefined} from 'typescript-collections/dist/lib/util';
import {Company} from '../../entities/company/company.model';
import {Principal} from '../../shared/auth/principal.service';
import {Subscription} from 'rxjs/Subscription';

@Component({
    selector: 'jhi-flowers-bank-details',
    templateUrl: './bank-details.component.html',
    styleUrls: ['/bank-details.scss'],
    providers: [BankDetailsService, ClipboardService]

})
export class BankDetailsComponent implements OnInit, OnDestroy {

    @ViewChild('fileGenUpload') fileGenUpload;
    @ViewChild('fileAltUpload') fileAltUpload;

    eventSubscriber: Subscription;
    farmName: Company;
    bankDetails: BankDetails;
    dataForm: FormGroup;
    isGeneralDetails = false;
    isAlternativeDetails = false;
    generalDetailsStr = '';
    alternativeDetailsStr = '';
    file: File;
    filePathToUploadedFile: any;
    msgs: Message[] = [];
    isButtonDisabled = true;
    isGeneralButtonDisabled = false;
    isAlternativeButtonDisabled = false;
    generalFileName = 'No file uploaded';
    alternativeFileName = 'No file uploaded';
    bankDetailCompanyId: number;
    removeControlsSubscription: Subscription;

    constructor(private principal: Principal,
                private bankDetailsService: BankDetailsService,
                private fb: FormBuilder,
                private clipboardService: ClipboardService,
                private jhiLanguageService: JhiLanguageService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['bank-details']);
        this.createForm();
    }

    ngOnInit() {
        this.principal.identity().then((companyUser) => {
            this.farmName = companyUser.company.farmName;
            this.bankDetailCompanyId = companyUser.company.id;
        });
        this.load();
        this.displayUploadedFileName();
        this.registerChangeInBankDetails();
    }

    ngOnDestroy(): void {
        this.eventManager.destroy(this.removeControlsSubscription);
    }

    load() {
        this.bankDetailsService.findBankDetailsByCurrentCompanyId().subscribe((bankDetails) => {
            this.bankDetails = bankDetails;
            this.initFormValues();
            this.dataForm.valueChanges.subscribe((data) => this.saveForm(data));
        });


    }

    onValueChanged(errors?: any, messageKeys?: any, form?: FormGroup) {
        if (!form) {
            return;
        }
        for (const field in messageKeys) {
            errors[field] = '';
            const control = form.get(field);
            if (control && control.dirty && !control.valid) {
                const messages = messageKeys[field];
                for (const key in control.errors) {
                    this.handleValidation(control, errors, field, key);
                }
            }
        }
    }

    private handleValidation(control: AbstractControl, errors, field: string, key: string) {
        errors[field] = key;
    }

    createForm() {
        this.dataForm = this.fb.group({
            general: new FormControl(null, [Validators.maxLength(500),
                Validators.pattern(LATIN_VALIDATION_WITH_ENTER)]),
            alternative: new FormControl(null, [Validators.maxLength(500),
                Validators.pattern(LATIN_VALIDATION_WITH_ENTER)])
        });
        this.dataForm.valueChanges.subscribe((ers) => this.onValueChanged(this.dataForm));
    }

    private initFormValues() {
        this.dataForm.get('general').setValue(this.bankDetails.general);
        this.dataForm.get('alternative').setValue(this.bankDetails.alternative);
    }

    private saveForm(data: BankDetails) {
        if (this.dataForm.valid) {
            this.prepareData();
            this.bankDetailsService.update(this.bankDetails).subscribe((res) => {
                console.log(res);
            }, (error) => {
                console.log(error);
            });
        }
    }

    private prepareData() {
        this.bankDetails.general = this.dataForm.get('general').value;
        this.bankDetails.alternative = this.dataForm.get('alternative').value;
    }

    public checkGeneralChange() {
        this.isGeneralDetails = !this.isGeneralDetails;
        this.changeDisabledStatus();
    }

    public checkAlternativeChange() {
        this.isAlternativeDetails = !this.isAlternativeDetails;
        this.changeDisabledStatus();
    }

    getSelectedInputs() {
        if (this.isGeneralDetails && this.isAlternativeDetails
            && this.alternativeDetailsStr.length !== 0 && this.generalDetailsStr.length !== 0) {
            this.clipboardService.value = this.generalDetailsStr +
                '\n\n' + this.alternativeDetailsStr;

        } else if (this.isGeneralDetails && this.generalDetailsStr.length !== 0) {
            this.clipboardService.value = this.generalDetailsStr;

        } else if (this.isAlternativeDetails && this.alternativeDetailsStr.length !== 0) {
            this.clipboardService.value = this.alternativeDetailsStr;
        }
        if (this.isGeneralDetails || this.isAlternativeDetails) {
            this.clipboardService.copyToClipboard();
        }
    }

    downloadFile() {
        let data: any;
        if (this.isGeneralDetails && this.isAlternativeDetails) {
            data = 1;
        } else if (this.isGeneralDetails) {
            data = 2;
        } else if (this.isAlternativeDetails) {
            data = 3;
        } else {
            data = 0;
        }
        this.bankDetailsService.downloadPDF(data).subscribe(
            (res) => {
                const file = res.blob();
                const displayDate = new Date().toLocaleDateString();
                FileSaver.saveAs(file, this.farmName + ' Bank Details ' + displayDate + '.pdf');
            },
        );
    }

    initData(): any {
        if (this.isGeneralDetails && this.isAlternativeDetails) {
            return 1;
        } else if (this.isGeneralDetails) {
            return 2;
        } else if (this.isAlternativeDetails) {
            return 3;
        } else {
            return 0;
        }
    }

    onUpload(event, type: string) {
        this.file = event.files[0];
        this.bankDetailsService.uploadFile(this.file, type)
            .subscribe(() => {
                event.files = [];
                this.fileGenUpload.clear();
                this.fileAltUpload.clear();
                if (type === 'alternative') {
                    this.alternativeFileName = this.file.name;
                    this.checkAlternativeDisabled();
                } else {
                    this.generalFileName = this.file.name;
                    this.checkGeneralDisabled();
                }
                this.msgs = [];
                this.msgs.push({severity: 'info', detail: 'File uploaded successfully'});
            });
    }

    downloadUploaded(type: string) {
        this.bankDetailsService.downloadUploadedPDF(type).subscribe(
            (res) => {
                const file = res.blob();
                this.filePathToUploadedFile = res.headers.get('content-disposition');
                FileSaver.saveAs(file, this.getOriginalFileName());
            });
    }

    private getOriginalFileName(): any {
        const delimiterStartIndex: number = this.filePathToUploadedFile.indexOf('filename=');
        const delimiterEndIndex = delimiterStartIndex + 'filename='.length + 1;
        const fileName = this.filePathToUploadedFile.substring(delimiterEndIndex, this.filePathToUploadedFile.length - 1);
        return fileName;
    }

    registerChangeInBankDetails() {
        this.removeControlsSubscription = this.eventManager.subscribe('bankDetailsChanged',
            (res) => {
                this.msgs = [];
                if (res.status === 200) {
                    this.msgs.push({severity: 'info', detail: 'File deleted successfully'});
                    if (res.type === 'alternative') {
                        this.alternativeFileName = 'No file uploaded';
                        this.checkAlternativeDisabled();
                    } else {
                        this.generalFileName = 'No file uploaded';
                        this.checkGeneralDisabled();
                    }
                } else if (res.status === 400) {
                    this.msgs.push({severity: 'info', detail: 'File delete failure or file already deleted'});
                }
            }
        );
    }

    private changeDisabledStatus() {
        this.isButtonDisabled = !this.isAlternativeDetails && !this.isGeneralDetails;
    }

    private checkAlternativeDisabled() {
        this.isAlternativeButtonDisabled = this.alternativeFileName === 'No file uploaded';
    }

    private checkGeneralDisabled() {
        this.isGeneralButtonDisabled = this.generalFileName === 'No file uploaded';
    }

    displayUploadedFileName() {
        this.bankDetailsService.showUploadedFileName().subscribe((res) => {
            if (!isUndefined(res['general'])) {
                this.generalFileName = res['general'];
            }
            if (!isUndefined(res['alternative'])) {
                this.alternativeFileName = res['alternative'];
            }
            this.checkAlternativeDisabled();
            this.checkGeneralDisabled();
        });
    }
}
