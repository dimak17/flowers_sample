import {
    AfterViewChecked, AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild, Output, EventEmitter,
    OnDestroy
} from '@angular/core';
import {FarmDataService} from './farm-data.service';
import {Principal} from '../../shared/auth/principal.service';
import {Company} from '../../entities/company/company.model';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Country} from './country';
import {CompanyUser} from '../../entities/company-user/company-user.model';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {LATIN_VALIDATION} from '../../shared/index';
import {Message, SelectItem} from 'primeng/primeng';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {Subscription} from 'rxjs/Subscription';
import * as FileSaver from 'file-saver';
import {FarmDataTranslationService} from './farm-data-translation-service';

@Component({
    selector: 'jhi-flowers-farm-data',
    templateUrl: './farm-data.component.html',
    styleUrls: ['/farm-data.scss'],
    providers: [FarmDataService, FarmDataTranslationService]
})
export class FarmDataComponent implements OnInit, OnDestroy, AfterViewInit, AfterViewChecked {

    @ViewChild('logoInput') inputEl: ElementRef;
    @ViewChild('logoImage') image: ElementRef;
    @Output() uploadLogoEvent: EventEmitter<any>;

    public htmlInputEl: HTMLInputElement;
    files: Array<File> = new Array<File>();
    imageFileName: string;
    deletedImageEvent: Subscription;
    profileImageUploaded: Subscription;
    file: File;
    extension: string;
    resized_image: string;
    base64Image: string;

    company: Company;
    countries: Array<Country>;
    typeOfFlower: SelectItem[] = [];
    selected: string[];
    typeOfFlowersTmp: TypeOfFlower[];

    usageVarietyError = false;
    msgs: Message[] = [];

    companyUser: CompanyUser;
    dataForm: FormGroup;
    private getBase64File: Subscription;
    private getDefaultImage: Subscription;
    private getCountries: Subscription;
    private makeFileRequest: Subscription;
    languageSubscriber: Subscription;
    tmp: string;

    save = true;

    constructor(private principal: Principal,
                private farmDataService: FarmDataService,
                private fb: FormBuilder,
                private eventManager: EventManager,
                private jhiLanguageService: JhiLanguageService,
                private languageHelper: JhiLanguageHelper,
                private elRef: ElementRef,
                private translationService: FarmDataTranslationService,
                private rd: Renderer2) {

        this.typeOfFlower.push({label: 'Roses', value: 'Roses'});
        this.typeOfFlower.push({label: 'Garden Roses', value: 'Garden Roses'});
        this.typeOfFlower.push({label: 'Spray Roses', value: 'Spray Roses'});

        this.farmDataService.findAllTypeOfFlowerByIdCompany().subscribe((typeOfFlowers) => {
            this.selected = typeOfFlowers.map((m) => m.name);
            this.typeOfFlowersTmp = typeOfFlowers;
        });
        this.languageHelper.addListener(this.translationService);
        this.createForm();
    }

    ngOnInit() {
        this.loadAll();
        this.registerChange();
    }

    loadAll() {
        this.principal.identity().then((companyUser) => {
            this.company = companyUser.company;

            if (this.company && this.company.id) {
                this.getBase64File = this.farmDataService.getBase64File().subscribe((response) => {
                    this.base64Image = response[0];
                });
            } else {
                this.getDefaultImage = this.farmDataService.getDefaultImage().subscribe((response) => {
                    this.base64Image = response[0];
                });
            }

            this.getCurrentLanguageInformation();
        });
        this.registerLangChange();
    }

    public getCurrentLanguageInformation() {
        this.jhiLanguageService.getCurrent().then((currentLang: string) => {
            this.translationService.getTranslation(currentLang, 'farm-data').subscribe((res) => {
                this.tmp = res.massage;
            });
            this.translationService.getTranslationArray(currentLang, 'countries').subscribe((countries) => {
                    this.countries = countries;
                    this.initFormValues();
                    this.save = true;
                },
                (error) => {
                    console.log(error);
                }
            );
        });
    }
    registerLangChange() {
        this.languageSubscriber = this.eventManager.subscribe('languageChangedFarmData', (response) => {
            this.getCurrentLanguageInformation();
        });
    }

    ngAfterViewInit(): void {
        const el = this.elRef.nativeElement.querySelector('.container-type-of-flowers .ui-multiselect');
        const arrowElem = el.querySelector('.fa-caret-down');
        this.rd.removeClass(arrowElem, 'fa-caret-down');
        this.rd.addClass(arrowElem, 'fa-angle-down');
    }

    ngAfterViewChecked() {
        this.changeArrow();
    }

    ngOnDestroy(): void {
        this.eventManager.destroy(this.languageSubscriber);
        this.eventManager.destroy(this.deletedImageEvent);
        if (this.getBase64File) {
            this.getBase64File.unsubscribe();
        }
        if (this.getDefaultImage) {
            this.getDefaultImage.unsubscribe();
        }
        if (this.getCountries) {
            this.getCountries.unsubscribe();
        }
        if (this.makeFileRequest) {
            this.makeFileRequest.unsubscribe();
        }
    }

    registerChange() {
        this.deletedImageEvent = this.eventManager.subscribe('companyImageDeleted', (response) => this.reset());
        this.profileImageUploaded = this.eventManager.subscribe('profileImageUploaded', (response) => this.reset());
    }

    reset() {
        this.inputEl.nativeElement.value = '';
        this.loadAll();
    }

    changeArrow() {
        const el2 = this.elRef.nativeElement.querySelector('.container-country .ui-select-container');
        const arrowElem2 = el2.querySelector('.dropdown-toggle');
        if (arrowElem2) {
            this.rd.removeClass(arrowElem2, 'dropdown-toggle');
            this.rd.addClass(arrowElem2, 'fa');
            this.rd.addClass(arrowElem2, 'fa-angle-down');
        }
    }

    checkOnError(name: string, error: string): any {
        return this.dataForm.get(name).hasError(error);
    }

    createForm() {
        this.dataForm = this.fb.group({
            typeOfFlowers: new FormControl(null, [Validators.required]),
            country: new FormControl(null, [Validators.required]),
            farmSize: new FormControl(null, [Validators.maxLength(50), Validators.pattern(LATIN_VALIDATION)]),
            address: new FormControl(null, [Validators.maxLength(50), Validators.pattern(LATIN_VALIDATION)]),
            city: new FormControl(null, [Validators.maxLength(50), Validators.pattern(LATIN_VALIDATION)]),
            farmName: new FormControl(null, [Validators.required, Validators.maxLength(50),
                Validators.pattern(LATIN_VALIDATION)]),
            legalName: new FormControl(null, [Validators.required, Validators.maxLength(50),
                Validators.pattern(LATIN_VALIDATION)]),
            generalOfficePhone: new FormControl(null, [Validators.required, Validators.maxLength(24),
                Validators.pattern(LATIN_VALIDATION)]),
            generalEmailAddress: new FormControl(null, [Validators.email, Validators.maxLength(50),
                Validators.required, Validators.pattern(LATIN_VALIDATION)])
        });
        this.dataForm.valueChanges.subscribe((data) => this.saveForm(data));
    }

    private initFormValues() {
        this.save = false;
        this.dataForm.get('farmName').setValue(this.company.farmName);
        this.dataForm.get('legalName').setValue(this.company.legalName);
        this.dataForm.get('generalOfficePhone').setValue(this.company.generalOfficePhone);
        this.dataForm.get('city').setValue(this.company.city);
        this.dataForm.get('farmSize').setValue(this.company.farmSize);
        this.dataForm.get('address').setValue(this.company.address);
        this.setCountryValue(this.dataForm.get('country'));
        this.dataForm.get('generalEmailAddress').setValue(this.company.generalEmailAddress);
        this.dataForm.get('typeOfFlowers').setValue(this.selected);
    }

    private setCountryValue(control: AbstractControl) {
        const texts = this.countries
            .filter((c) => c.id === this.company.country)
            .map((c) => c.text);
        if (texts && texts.length) {
            const text = texts[0];
            control.setValue([{id: this.company.country, text}]);
        }
    }

    private saveForm(data: Company) {
        if (this.dataForm.valid && this.save) {
            data.id = this.company.id;
            data.typeOfFlowers = null;

            if (data.country && data.country.length) {
                data.country = data.country[0].id;
            }
            this.farmDataService.update(data).subscribe((res) => {
                this.eventManager.broadcast({name: 'CompanyNameModification', farmName: res.farmName});
            });
            this.usageVarietyError = false;
            this.typeOfFlowersTmp.forEach((m) => {
                const i = this.checkTypeOfFlowers(m);
                if (i === -1) {
                    this.farmDataService.deleteTypeOfFlower(m.id).subscribe((res) => {
                        this.usageVarietyError = true;
                    }, (error) => {
                        if (error.headers.get('x-flowersapp-error') === 'error.Variety') {
                            const tmp = error.headers.get('defaultMessage').substring(1, error.headers.get('defaultMessage').length - 1);
                            this.msgs.push({
                                severity: 'warn',
                                summary: 'Warn Message',
                                detail: m.name + this.tmp
                            });
                            this.farmDataService.findAllTypeOfFlowerByIdCompany().subscribe((typeOfFlowers) => {
                                this.selected = typeOfFlowers.map((t) => t.name);
                                this.usageVarietyError = true;
                            });
                        }
                    });
                    this.msgs = [];
                    this.findTypeOfFlowersByCompanyId();
                }
            });
            this.saveTypeOfFlowers();
        }
    }

    checkTypeOfFlowers(tp: TypeOfFlower) {
        for (let i = 0; i < this.selected.length; i++) {
            if (this.selected[i] === tp.name) {
                return i;
            }
        }
        return -1;
    }

    private saveTypeOfFlowers() {
        if (!this.usageVarietyError) {
            this.selected.forEach((m) => {
                const typeOfFlower = new TypeOfFlower();
                typeOfFlower.name = m;
                this.farmDataService.createTypeOfFlower(typeOfFlower).subscribe((res) => {
                });
            });
            this.findTypeOfFlowersByCompanyId();
        }
    }

    private findTypeOfFlowersByCompanyId() {
        this.farmDataService.findAllTypeOfFlowerByIdCompany().subscribe((typeOfFlowers) => {
            this.typeOfFlowersTmp = typeOfFlowers;
        });

    }

    upload() {
        this.htmlInputEl = this.inputEl.nativeElement;
        const imageFile: File  = this.htmlInputEl.files.item(0);
        this.readModalFiles(this.image.nativeElement, imageFile);
        this.extension =  imageFile.name.substring(imageFile.name.lastIndexOf('.'),  imageFile.name.length);
        const fileName = this.company.id.toString() + this.extension;
        this.readFiles(imageFile, fileName);
    }

    readFile(file: File, reader, callback) {
        reader.onload = () => {
            callback(reader.result);
        };
        reader.readAsDataURL(file);
    }

    readFiles(file: File, fileName: string) {

        const reader = new FileReader();

        this.readFile(file, reader, (result) => {

            const img = document.createElement('img');
            img.src = result;

            this.resize(img, file.name, 189, 120, (resized_image) => {

                this.file = resized_image;
                this.resized_image = resized_image;
                this.makeFileRequest = this.farmDataService.makeFileRequest(this.file, fileName).subscribe(() => {
                    this.eventManager.broadcast({
                        name: 'profileImageUploaded',
                    });
                });
                this.uploadLogoEvent.emit(true);
            });
        });
    }

    readModalFiles(image, file: File) {
        const reader = new FileReader();
        this.readFile(file, reader, (result) => {
            const img = document.createElement('img');
            img.src = result;
            this.resize(img, file.name, 189, 120, (resized_image) => {
                this.rd.setAttribute(image, 'src', resized_image);
            });
        });
    }

    resize(img, fileName: string, MAX_WIDTH: number, MAX_HEIGHT: number, callback) {

        return img.onload = () => {

            let width = img.width;
            let height = img.height;

            if (width > height) {
                if (width > MAX_WIDTH) {
                    height *= MAX_WIDTH / width;
                    width = MAX_WIDTH;
                }
            } else {
                if (height > MAX_HEIGHT) {
                    width *= MAX_HEIGHT / height;
                    height = MAX_HEIGHT;
                }
            }

            const canvas = document.createElement('canvas');

            canvas.width = width;
            canvas.height = height;
            const ctx = canvas.getContext('2d');

            ctx.drawImage(img, 0, 0,  width, height);

            const pngExtension = ['png'];
            const jpgExtension = ['jpg'];
            const jpegExtension = ['jpeg'];
            const tempFileName = fileName;
            const imageExtension = fileName.split('.').pop();
            let dataUrl: any;

            if (this.isInArray(pngExtension, imageExtension)) {
                if (this.company && this.company.id) {
                    this.imageFileName = tempFileName.replace(fileName, this.company.id.toString().concat('.').concat(pngExtension[0]));
                }
                dataUrl = canvas.toDataURL('image/png');
            }

            if (this.isInArray(jpgExtension, imageExtension)) {
                if (this.company && this.company.id) {
                    this.imageFileName = tempFileName.replace(fileName, this.company.id.toString().concat('.').concat(jpgExtension[0]));
                }
                dataUrl = canvas.toDataURL('image/jpg');
            }

            if (this.isInArray(jpegExtension, imageExtension)) {
                if (this.company && this.company.id) {
                    this.imageFileName = tempFileName.replace(fileName, this.company.id.toString().concat('.').concat(jpegExtension[0]));
                }
                dataUrl = canvas.toDataURL('image/jpeg');
            }

            callback(dataUrl, img.src.length, dataUrl.length, dataUrl.name);
        };
    }

    download(){
        this.farmDataService.downloadPDF().subscribe(
            (res) => {
                const file = res.blob();
                const displayDate = new Date().toLocaleDateString();
                FileSaver.saveAs(file, this.company.farmName  + ' Presentation list ' + displayDate  + '.pdf');
            },
        );
    }

    /*- checks if extension exists in array -*/
    isInArray(array, word) {
        return array.includes(word.toLowerCase());
    }
}
