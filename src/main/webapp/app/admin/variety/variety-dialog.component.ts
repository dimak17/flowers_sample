import {
    Component, OnInit, OnDestroy, ElementRef, ViewChild, Renderer2, Output,
    EventEmitter
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {Http, Response} from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';
import { VarietyPopupService } from './variety-popup.service';
import { VarietyService } from './variety.service';
import {Subscription} from 'rxjs/Subscription';
import {LATIN_VALIDATION, DIGITS} from '../../shared';
import {Variety} from '../../entities/variety/variety.model';
import {AbstractControl, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';

@Component({
    selector: 'jhi-variety-dialog',
    templateUrl: './variety-dialog.component.html',
    styleUrls: ['./variety-dialog.component.scss'],
    providers: [ VarietyService ]
})
export class VarietyDialogComponent implements OnInit, OnDestroy {

    @ViewChild('fileInput') inputEl: ElementRef;
    @ViewChild('image') image: ElementRef;
    @Output() uploadEvent: EventEmitter<any>;

    public htmlInputEl: HTMLInputElement;
    files: Array<File> = new Array<File>();
    imageFileName: string;
    deletedImageEvent: Subscription;
    newDeletedImageEvent: Subscription;
    file: File;
    variety: Variety;
    authorities: any[];
    isSaving: boolean;
    resized_image: string;
    extension: string;
    tmpName: string;
    nameMax = 35;
    colorMax = 25;
    breederMax = 25;
    digitMax = 4;
    typeOfFlowerNames: Array<string>;
    typeOfFlowers: TypeOfFlower[];
    typeOfFlower: TypeOfFlower;
    formGroup: FormGroup;
    typeOfFlowerId: number;
    base64Image: string;

    constructor(public activeModal: NgbActiveModal,
                private alertService: AlertService,
                private varietyService: VarietyService,
                private eventManager: EventManager,
                private rd: Renderer2,
                private fb: FormBuilder
    ) {
        this.createForm();
        this.uploadEvent = new EventEmitter<any>();

    }

    createForm() {
        this.formGroup = this.fb.group({
            typeOfFlowers: new FormControl()
        });
    }

    ngOnInit() {
        if (this.variety.id) {
            this.varietyService.getBase64FileById(this.variety.id).subscribe((response) => {
                this.base64Image = response[0];
            });
        } else {
            this.varietyService.getDefaultImage().subscribe((response) => {
                this.base64Image = response[0];
            });
        }
        if (!this.typeOfFlowerId) {
            this.fillTypeOfFlowers();
        }
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.tmpName = '';
        this.registerEvents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.deletedImageEvent);
        this.eventManager.destroy(this.newDeletedImageEvent);
    }

    registerEvents() {
        this.deletedImageEvent = this.eventManager.subscribe('varietyImageDeleted', (response) => this.resetDefaultImage());
        this.newDeletedImageEvent = this.eventManager.subscribe('newVarietyImageDeleted', (response) => this.resetDefaultImage());
    }

    resetDefaultImage() {
        this.varietyService.getDefaultImage().subscribe((response) => {
            this.base64Image = response[0];
            this.image.nativeElement.src  = 'data:image/png;base64,' + this.base64Image;
            this.inputEl.nativeElement.value = '';
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (!this.variety.breeder) {
            this.variety.breeder = null;
        }
        if (!this.variety.minLength) {
            this.variety.minLength = null;
        }
        if (!this.variety.maxLength) {
            this.variety.maxLength = null;
        }
        if (!this.variety.typeOfFlower) {
            this.variety.typeOfFlower = this.typeOfFlowers[0];
        }
        if (!this.variety.id) {
            this.subscribeToSaveResponse(
                this.varietyService.create(this.variety), true);
        } else {
            this.subscribeToSaveResponse(
                this.varietyService.update(this.variety), false);
        }
    }

    private subscribeToSaveResponse(result: Observable<Variety>, isCreated: boolean) {
        result.subscribe((res: Variety) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Variety, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.variety.created'
                : 'flowersApp.variety.updated',
            {param: result.id}, null);
        this.variety.id = result.id;
        this.sendImage(this.variety.id);
        this.eventManager.broadcast({name: 'varietyListModification', content: 'OK'});
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
        if (error.headers.get('x-flowersapp-error') === 'error.DuplicateName') {
            this.tmpName = this.variety.name.toLowerCase().trim();
        }else {
            this.alertService.error(error.message, null, null);
        }
    }

    private fillTypeOfFlowerNames() {
        this.typeOfFlowerNames = this.typeOfFlowers.map((t: TypeOfFlower) => t.name);
    }

    private fillTypeOfFlowers() {
        this.varietyService.findAllTypeOfFlowers().subscribe((typeOfFlowers) => {
            this.typeOfFlowers = typeOfFlowers;
            this.fillTypeOfFlowerNames();
            this.initFormValues();
        });
    }

    private initFormValues() {
        this.setTypeOfFlowersValue(this.formGroup.get('typeOfFlowers'));
    }

    private setTypeOfFlowersValue(control: AbstractControl) {
        const names = this.typeOfFlowers.map((t) => t.name);
        const id = this.typeOfFlowers.map((t) => t.id);
        if (names && names.length && names[0]) {
            if (!this.variety.typeOfFlower) {
                const text = names[0];
                control.setValue([{id: this.typeOfFlowers[0].name, text}]);
                this.typeOfFlowerId = id[0];
            } else {
                const text = this.variety.typeOfFlower.name;
                control.setValue([{id: this.variety.typeOfFlower.name, text}]);
                this.typeOfFlowerId = this.variety.typeOfFlower.id;
            }
        }
    }

    public selectedTypeOfFlower(value: any): void {
        this.typeOfFlowers.forEach((t) => {
            if (t.name === value.text) {
                this.variety.typeOfFlower = t;
            }
        });
    }

    public sendImage(varietyId: number) {
        let fileName;
        if (!this.isSaving) {
            const ext = this.imageFileName.substring(this.imageFileName.lastIndexOf('.'), this.imageFileName.length);
            fileName = varietyId.toString() + ext;
        } else {
            fileName = varietyId.toString() + this.extension;
        }
        if (this.file) {
            this.varietyService.makeFileRequest(this.file, fileName).subscribe( (response) => {
                this.eventManager.broadcast({name: 'imageUploaded'});
            });
        }
    }

    upload() {
        this.htmlInputEl = this.inputEl.nativeElement;
        const imageFile: File  = this.htmlInputEl.files.item(0);
        this.readModalFiles(this.image.nativeElement, imageFile);
        this.readFiles(imageFile);
    }

    readFile(file: File, reader, callback) {
        reader.onload = () => {
            callback(reader.result);
        };
        reader.readAsDataURL(file);
    }

    readFiles(file: File) {
        this.extension =  file.name.substring(file.name.lastIndexOf('.'),  file.name.length);
        const reader = new FileReader();
        this.readFile(file, reader, (result) => {
            const img = document.createElement('img');
            img.src = result;

            this.resize(img, file.name, 189, 120, (resized_image) => {
                this.file = resized_image;
                this.resized_image = resized_image;
                this.uploadEvent.emit(true);
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
                if (this.variety && this.variety.id) {
                    this.imageFileName = tempFileName.replace(fileName, this.variety.id.toString().concat('.').concat(pngExtension[0]));
                }
                dataUrl = canvas.toDataURL('image/png');
            }

            if (this.isInArray(jpgExtension, imageExtension)) {
                if (this.variety && this.variety.id) {
                    this.imageFileName = tempFileName.replace(fileName, this.variety.id.toString().concat('.').concat(jpgExtension[0]));
                }
                dataUrl = canvas.toDataURL('image/jpg');
            }

            if (this.isInArray(jpegExtension, imageExtension)) {
                if (this.variety && this.variety.id) {
                    this.imageFileName = tempFileName.replace(fileName, this.variety.id.toString().concat('.').concat(jpegExtension[0]));
                }
                dataUrl = canvas.toDataURL('image/jpeg');
            }

            callback(dataUrl, img.src.length, dataUrl.length, dataUrl.name);
        };
    }

    /*- checks if extension exists in array -*/
    isInArray(array, word) {
        return array.indexOf(word.toLowerCase()) > -1;
    }

    requiredValidation(data: string) {
        return !data || !this.isFillValidation(data);
    }

    isFillValidation(fieldData: any): boolean {
        return fieldData && fieldData.toString().trim();
    }

    digitValidation(varietyLength: number): boolean {
        if (varietyLength && varietyLength.toString()) {
            return !varietyLength.toString().match('') && !varietyLength.toString().match(DIGITS);
        }
    }

    latinValidation(data: string, maxLength: number): boolean {
        if (data && !this.maxLength(data, maxLength)) {
            return !data.match(LATIN_VALIDATION);
        }
    }

    maxLength(data: string | number, maxLength: number): boolean {
        if (data && data.toString().length) {
            return data.toString().length > maxLength;
        }
    }

    duplicateValidation(fieldData: string): boolean {
        return this.variety.name && this.tmpName && this.tmpName === this.variety.name.toLowerCase().trim() && !this.maxLength(fieldData, this.nameMax);
    }

    saveButtonDeactivation(variety: Variety): boolean {
        return this.requiredValidation(variety.name) || this.requiredValidation(variety.color)
            || this.digitValidation(variety.minLength) || this.digitValidation(variety.maxLength)
            || this.latinValidation(variety.name, this.nameMax) || this.latinValidation(variety.color, this.colorMax)
            || this.latinValidation(variety.breeder, this.breederMax) || this.maxLength(variety.name, this.nameMax)
            || this.maxLength(variety.color, this.colorMax) || this.maxLength(variety.breeder, this.breederMax)
            || this.maxLength(variety.minLength, this.digitMax) || this.maxLength(variety.maxLength, this.digitMax);
    }
}

@Component({
    selector: 'jhi-variety-popup',
    template: ''
})
export class VarietyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private varietyPopupService: VarietyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.varietyPopupService
                    .open(VarietyDialogComponent, 'variety-list-modal-window', params['id']);
            } else {
                this.modalRef = this.varietyPopupService
                    .open(VarietyDialogComponent, 'variety-list-modal-window');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
