import {
    Component, ElementRef, EventEmitter, OnDestroy, OnInit, Input, Output, Renderer2,
    ViewChild, HostListener
} from '@angular/core';
import {CompanyUser} from '../../../entities/company-user/company-user.model';
import {ProfileInfoService} from './profile-info.service';
import {Principal} from '../../../shared/auth/principal.service';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {Subscription} from 'rxjs/Subscription';
import {Http} from '@angular/http';
import {LATIN_VALIDATION} from '../../../shared/constants/validation.constants';
/**
 * Created by platon on 12.06.17.
 */
@Component({
    selector: 'jhi-flowers-profile-info',
    templateUrl: './profile-info.component.html',
    styleUrls: ['profile-info.scss'],
    providers: [ProfileInfoService]
})
export class ProfileInfoComponent implements OnInit, OnDestroy {

    @ViewChild('fileInput') inputEl: ElementRef;
    @ViewChild('image') image: ElementRef;
    @Output() uploadEvent: EventEmitter<any>;

    public htmlInputEl: HTMLInputElement;
    files: Array<File> = [];
    imageFileName: string;
    deletedImageEvent: Subscription;
    newDeletedImageEvent: Subscription;
    file: File;
    resized_image: string;
    initialName: string;
    extension: string;
    base64Image: string;

    profileForm: FormGroup;
    leftForm: FormGroup;
    rightForm: FormGroup;
    companyUser: CompanyUser;
    loading = false;
    @Input() settingsComponent: any;
    @Output() updateCompanyUser: EventEmitter<CompanyUser>;
    @Output() hideProfileInfo: EventEmitter<boolean>;
    @Output() onClickOutside: EventEmitter<boolean>;

    oldPasswordIncorrect = false;
    isSavingLeft = false;

    rightFormErrors = {
        'fullName': '',
        'email': '',
        'officePhone': '',
        'mobilePhone': '',
        'skype': '',
    };

    customKeys = {
        'pass-not-match': true,
        'repeat-required': true,
    };

    rightFormValidationMessagesKeys = {
        'fullName': {
            'required': 'flowersApp.validation.fullName.required',
            'maxlength': 'flowersApp.validation.fullName.maxlength'
        },
        'email': {
            'required': 'flowersApp.validation.email.required',
            'maxlength': 'flowersApp.validation.email.maxlength'
        },
        'officePhone': {
            'required': 'flowersApp.validation.officePhone.required',
            'maxlength': 'flowersApp.validation.officePhone.maxlength'
        },
        'mobilePhone': {
            'required': 'flowersApp.validation.mobilePhone.required',
            'maxlength': 'flowersApp.validation.mobilePhone.maxlength'
        },
        'skype': {
            'required': 'flowersApp.validation.skype.required',
            'maxlength': 'flowersApp.validation.skype.maxlength'
        }
    };

    leftFormErrors = {
        'loginEmail': '',
        'oldPassword': '',
        'newPassword': '',
        'newPasswordRepeat': ''
    };

    leftFormValidationMessagesKeys = {
        'loginEmail': {
            'required': 'flowersApp.validation.loginEmail.required',
            'maxlength': 'flowersApp.validation.loginEmail.maxlength',
            'pattern': 'flowersApp.validation.loginEmail.pattern'
        },
        'newPassword': {
            'required': 'flowersApp.validation.newPassword.required',
            'minlength': 'flowersApp.validation.newPassword.minlength',
            'maxlength': 'flowersApp.validation.newPassword.maxlength'
        },
        'newPasswordRepeat': {
            'required': 'flowersApp.validation.newPasswordRepeat.required',
            'minlength': 'flowersApp.validation.newPasswordRepeat.minlength',
            'maxlength': 'flowersApp.validation.newPasswordRepeat.maxlength'
        },
        'oldPassword': {
            'required': 'flowersApp.validation.oldPassword.required',
            'maxlength': 'flowersApp.validation.oldPassword.maxlength'
        }
    };
    private getLeftFormChangesValues: Subscription;
    private getRightFormChangesValues: Subscription;
    private getUpdatedCompanyUser: Subscription;
    private getChangedAccountData: Subscription;
    private getProfileInfo: Subscription;
    private getBase64File: Subscription;
    private getDefaultImage: Subscription;

    constructor(private principal: Principal,
                private profileInfoService: ProfileInfoService,
                private fb: FormBuilder,
                private jhiLanguageService: JhiLanguageService,
                private eRef: ElementRef,
                private eventManager: EventManager,
                private http: Http,
                private rd: Renderer2,
    ) {
        this.updateCompanyUser = new EventEmitter<CompanyUser>();
        this.createForm();
        this.hideProfileInfo = new EventEmitter<boolean>();
        this.jhiLanguageService.setLocations(['profile-info']);
        this.uploadEvent = new EventEmitter<any>();
        this.onClickOutside = new EventEmitter<boolean>();
    }

    ngOnInit(): void {
        this.getPublicInfo();
        this.registerEvents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.deletedImageEvent);
        this.eventManager.destroy(this.newDeletedImageEvent);
        if (this.getProfileInfo) {
            this.getProfileInfo.unsubscribe();
        }
        if (this.getLeftFormChangesValues) {
            this.getLeftFormChangesValues.unsubscribe();
        }
        if (this.getRightFormChangesValues) {
            this.getRightFormChangesValues.unsubscribe();
        }
        if (this.getUpdatedCompanyUser) {
            this.getUpdatedCompanyUser.unsubscribe();
        }
        if (this.getChangedAccountData) {
            this.getChangedAccountData.unsubscribe();
        }
    }

    registerEvents() {
        this.deletedImageEvent = this.eventManager.subscribe('profileInfoImageDeleted', (response) => this.reset());
        this.newDeletedImageEvent = this.eventManager.subscribe('newProfileInfoImageDeleted', (response) => this.reset());
    }

    reset() {
        this.inputEl.nativeElement.value = '';
    }

    createForm() {
        this.loading = true;

        const oldPassCtrl = new FormControl(null);
        const loginCtrl = new FormControl(null);
        const newPassCtrl = new FormControl(null);
        const newPassRepeatCtrl = new FormControl(null);

        const oldPassValidators = [
            Validators.maxLength(50),
            this.oldPassRequiredValidator(newPassCtrl, newPassRepeatCtrl, loginCtrl)
        ];

        const loginValidators = [
            Validators.maxLength(50),
            Validators.email,
            this.oldPassRequiredForChangeLoginValidator(oldPassCtrl),
        ];

        const newPassValidators = [
            Validators.minLength(6),
            Validators.maxLength(50),
            this.passMatchValidator(newPassRepeatCtrl),
            this.passRequiredValidator(newPassRepeatCtrl),
            this.oldPassRequiredForChangePassValidator(oldPassCtrl),
        ];

        const newPassRepeatValidators = [
            Validators.minLength(6),
            Validators.maxLength(50),
            this.passRepeatMatchValidator(newPassCtrl),
            this.passRequiredValidator(newPassCtrl),
            this.oldPassRequiredForChangePassValidator(oldPassCtrl),
        ];

        oldPassCtrl.setValidators(oldPassValidators);
        loginCtrl.setValidators(loginValidators);
        newPassCtrl.setValidators(newPassValidators);
        newPassRepeatCtrl.setValidators(newPassRepeatValidators);

        this.leftForm = this.fb.group({
            loginEmail: loginCtrl,
            newPassword: newPassCtrl,
            newPasswordRepeat: newPassRepeatCtrl,
            oldPassword: oldPassCtrl
        });

        this.rightForm = this.fb.group({
            fullName: new FormControl(null, [Validators.required, Validators.pattern(LATIN_VALIDATION), Validators.maxLength(50)]),
            email: new FormControl(null, [Validators.email, Validators.required, Validators.maxLength(50)]),
            officePhone: new FormControl(null, [Validators.maxLength(24)]),
            mobilePhone: new FormControl(null, [Validators.pattern(/^[0-9()+\s]+$/), Validators.maxLength(24)]),
            skype: new FormControl(null, [Validators.maxLength(50)])
        });

        this.profileForm = this.fb.group({
            leftForm: this.leftForm,
            rightForm: this.rightForm
        });

        this.getLeftFormChangesValues = this.leftForm.valueChanges.subscribe((data) =>
            this.onValueChanged(this.leftFormErrors, this.leftFormValidationMessagesKeys, this.leftForm, true, data)
        );

        this.getRightFormChangesValues = this.rightForm.valueChanges.subscribe((data) =>
            this.onValueChanged(this.rightFormErrors, this.rightFormValidationMessagesKeys, this.rightForm, false, data)
        );
    }

    onValueChanged(errors?: any, messageKeys?: any, form?: FormGroup, isLeft?: boolean, data?: any) {
        if (!form) {
            return;
        }
        for (const field in messageKeys) {
            errors[field] = '';
            const control = form.get(field);
            if (control && control.dirty && !control.valid) {
                const messages = messageKeys[field];
                for (const key in control.errors) {
                    this.handleValidation(control, errors, isLeft, field, key);
                }
            }
        }
    }

    private handleValidation(control: AbstractControl, errors, isLeft: boolean, field: string, key: string) {
        if (isLeft) {
            if (this.customKeys[key]) {
                errors[field] = control.getError(key) ? key : '';
            } else {
                errors[field] = key;
            }
        } else {
            errors[field] = key;
        }
    }

    getPublicInfo() {
        this.loading = true;
        this.principal.identity(true).then((account) => {
            this.companyUser = account;
            this.initialName = this.companyUser.id.toString();
            this.initFormValues();
            if (this.companyUser && this.companyUser.id) {
                this.getBase64File = this.profileInfoService.getBase64File(this.companyUser.id).subscribe((response) => {
                    this.base64Image = response[0];
                    this.loading = false;
                });
            } else {
                this.getDefaultImage = this.profileInfoService.getDefaultImage().subscribe((response) => {
                    this.base64Image = response[0];
                    this.loading = false;
                });
            }
        });
    }

    saveRight() {
        if (this.rightForm.valid) {
            this.fetchRightFormResult();
            this.getUpdatedCompanyUser = this.profileInfoService.updateCompanyUser(this.companyUser).subscribe((res: Response) => {
                res.json();
                console.log('Emitting user profile info from ProfileInfo' + this.companyUser);
                this.updateCompanyUser.emit(this.companyUser);
            });
        }
    }

    saveLeft() {
        if (this.leftForm.valid && this.isLeftFormChanged()) {
            const changeLeftFormData = this.fetchLeftFormResult();
            this.isSavingLeft = false;
            this.getChangedAccountData = this.profileInfoService.changeAccountData(changeLeftFormData).subscribe((res: Response) => {
                this.oldPasswordIncorrect = false;
                this.hideProfileInfo.emit(false);
            }, (error: Response) => {
                if ('Incorrect password' === error.text().toString()) {
                    this.oldPasswordIncorrect = true;
                } else {
                    this.oldPasswordIncorrect = false;
                }
            }, () => {
                this.isSavingLeft = true;
            });
        } else if (!this.isLeftFormChanged()) {
            this.onClickOutside.emit(false);
        }
    }

    upload() {
        this.htmlInputEl = this.inputEl.nativeElement;
        const imageFile: File  = this.htmlInputEl.files.item(0);
        this.readModalFiles(this.image.nativeElement, imageFile);
        this.extension =  imageFile.name.substring(imageFile.name.lastIndexOf('.'),  imageFile.name.length);
        const fileName = this.companyUser.id.toString() + this.extension;
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

            this.resize(img, file.name, 85, 85, (resized_image) => {

                this.file = resized_image;
                this.resized_image = resized_image;
                this.getProfileInfo = this.profileInfoService.makeFileRequest(this.file, fileName).subscribe(() => {
                    this.eventManager.broadcast({
                        name: 'profileImageUploaded',
                    });
                });
                this.uploadEvent.emit(true);
            });
        });
    }

    readModalFiles(image, file: File) {
        const reader = new FileReader();
        this.readFile(file, reader, (result) => {
            const img = document.createElement('img');
            img.src = result;
            this.resize(img, file.name, 85, 85, (resized_image) => {
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
                if (this.companyUser && this.companyUser.id) {
                    this.imageFileName = tempFileName.replace(fileName, this.companyUser.id.toString().concat('.').concat(pngExtension[0]));
                }
                dataUrl = canvas.toDataURL('image/png');
            }

            if (this.isInArray(jpgExtension, imageExtension)) {
                if (this.companyUser && this.companyUser.id) {
                    this.imageFileName = tempFileName.replace(fileName, this.companyUser.id.toString().concat('.').concat(jpgExtension[0]));
                }
                dataUrl = canvas.toDataURL('image/jpg');
            }

            if (this.isInArray(jpegExtension, imageExtension)) {
                if (this.companyUser && this.companyUser.id) {
                    this.imageFileName = tempFileName.replace(fileName, this.companyUser.id.toString().concat('.').concat(jpegExtension[0]));
                }
                dataUrl = canvas.toDataURL('image/jpeg');
            }
            callback(dataUrl, img.src.length, dataUrl.length, dataUrl.name);
        };
    }

    isInArray(array, word) {
        return array.includes(word.toLowerCase());
    }

    private fetchRightFormResult() {
        this.companyUser.user.fullName = this.rightForm.get('fullName').value;
        this.companyUser.user.email = this.rightForm.get('email').value;
        this.companyUser.officePhone = this.rightForm.get('officePhone').value;
        this.companyUser.mobilePhone = this.rightForm.get('mobilePhone').value;
        this.companyUser.skype = this.rightForm.get('skype').value;
    }

    private fetchLeftFormResult() {
        if (this.companyUser.user.login !== this.leftForm.get('loginEmail').value && this.leftForm.get('newPassword').value !== '') {
            return {
                login: this.leftForm.get('loginEmail').value,
                oldPassword: this.leftForm.get('oldPassword').value,
                newPassword: this.leftForm.get('newPassword').value,
            };
        } else if (this.companyUser.user.login !== this.leftForm.get('loginEmail').value) {
            return {
                login: this.leftForm.get('loginEmail').value,
                oldPassword: this.leftForm.get('oldPassword').value,
            };
        } else if (this.leftForm.get('newPassword').value) {
            return {
                oldPassword: this.leftForm.get('oldPassword').value,
                newPassword: this.leftForm.get('newPassword').value,
            };
        }
    }

    private initFormValues() {
        this.leftForm.get('newPassword').setValue('');
        this.leftForm.get('newPasswordRepeat').setValue('');
        this.leftForm.get('oldPassword').setValue('');
        this.leftForm.get('loginEmail').setValue(this.companyUser.user.login);
        this.rightForm.get('fullName').setValue(this.companyUser.fullName);
        this.rightForm.get('email').setValue(this.companyUser.user.email);
        this.rightForm.get('officePhone').setValue(this.companyUser.officePhone);
        this.rightForm.get('mobilePhone').setValue(this.companyUser.mobilePhone);
        this.rightForm.get('skype').setValue(this.companyUser.skype);
    }

    isLeftFormChanged() {
        const isLoginChanged = this.companyUser.user.login !== this.leftForm.get('loginEmail').value;
        const isNewPasswordEmpty = (!this.leftForm.get('newPassword').value)
                                    && (!this.leftForm.get('newPasswordRepeat').value);
        return isLoginChanged || !isNewPasswordEmpty;
    }

    oldPassRequiredForChangeLoginValidator(oldPassCtrl: AbstractControl): ValidatorFn {
        return (loginCtrl: AbstractControl): { [key: string]: any } => {
            const oldPassVal = oldPassCtrl.value;
            const loginVal = loginCtrl.value;
            const notValid = !oldPassVal && loginVal !== this.companyUser.user.login;
            if (notValid) {
                oldPassCtrl.setErrors({'required': true});
            } else {
                oldPassCtrl.updateValueAndValidity({onlySelf: true, emitEvent: true});
            }
            return null;
        };
    }

    oldPassRequiredForChangePassValidator(oldPassCtrl: AbstractControl): ValidatorFn {
        return (newPassCtrl: AbstractControl): { [key: string]: any } => {
            const oldPassVal = oldPassCtrl.value;
            const newPassVal = newPassCtrl.value;
            const notValid = !oldPassVal && newPassVal;
            if (notValid) {
                oldPassCtrl.setErrors({'required': true});
            } else {
                oldPassCtrl.updateValueAndValidity({onlySelf: true, emitEvent: true});
            }
            return null;
        };
    }

    oldPassRequiredValidator(newPassCtrl: AbstractControl, newPassRepeatCtrl: AbstractControl, loginCtrl: AbstractControl): ValidatorFn {
        return (oldPassCtrl: AbstractControl): { [key: string]: any } => {
            const oldPassVal = oldPassCtrl.value;
            const newPassVal = newPassCtrl.value;
            const newPassRepeatVal = newPassRepeatCtrl.value;
            const loginVal = loginCtrl.value;
            const notValid = !oldPassVal && (newPassVal || newPassRepeatVal || loginVal !== this.companyUser.user.login);
            if (notValid) {
                return{'required': true};
            }
            return null;
        };
    }

    passMatchValidator(thatPassCtrl: AbstractControl): ValidatorFn {
        return (thisPassCtrl: AbstractControl): { [key: string]: any } => {
            const thatPassVal = thatPassCtrl.value;
            const thisPassVal = thisPassCtrl.value;
            const notValid = thisPassVal && thatPassVal && thisPassVal !== thatPassVal;
            if (notValid) {
                thatPassCtrl.setErrors({'pass-not-match': true});
            }
            return null;
        };
    }

    passRepeatMatchValidator(thatPassCtrl: AbstractControl): ValidatorFn {
        return (thisPassCtrl: AbstractControl): { [key: string]: any } => {
            const thatPassVal = thatPassCtrl.value;
            const thisPassVal = thisPassCtrl.value;
            const notValid = thisPassVal && thatPassVal && thisPassVal !== thatPassVal;
            if (notValid) {
                return{'pass-not-match': true};
            }
            return null;
        };
    }

    passRequiredValidator(thatPassCtrl: AbstractControl): ValidatorFn {
        return (thisPassCtrl: AbstractControl): { [key: string]: any } => {
            const thisPassVal = thisPassCtrl.value;
            const thatPassVal = thatPassCtrl.value;
            const notThisValid = !thisPassVal && thatPassVal;
            const notThatValid = thisPassVal && !thatPassVal;
            if (notThatValid) {
                thatPassCtrl.setErrors({'required': true});
            }else if (thatPassCtrl.invalid) {
                thatPassCtrl.updateValueAndValidity({onlySelf: true, emitEvent: true});
            }
            if (notThisValid) {
                return ({'required': true});
            }
            return null;
        };
    }

    @HostListener('document:click', ['$event'])
    registerClickOutside(event) {
        if (this.eRef.nativeElement.contains(event.target) || this.settingsComponent === event.target) {
            this.onClickOutside.emit(true);
        } else {
            this.onClickOutside.emit(false);
        }
    }
}
