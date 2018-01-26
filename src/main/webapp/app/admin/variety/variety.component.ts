import {Component, OnInit, OnDestroy, ElementRef, ViewChild} from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, AlertService } from 'ng-jhipster';
import { VarietyService } from './variety.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import {Variety} from '../../entities/variety/variety.model';
import {TypeOfFlower} from '../../entities/type-of-flower/type-of-flower.model';
import {AbstractControl, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';
import {NavbarComponent} from '../../layouts/navbar/navbar.component';

@Component({
    selector: 'jhi-variety',
    templateUrl: './variety.component.html',
    styleUrls: ['./variety.component.scss'],
})
export class VarietyComponent implements OnInit, OnDestroy {

    @ViewChild('image') image: ElementRef;
    varieties: Variety[];
    currentAccount: any;
    deletedImageEvent: Subscription;
    eventSubscriber: Subscription;
    imageEventSubscriber: Subscription;
    typeOfFlowersSubscriber: Subscription;
    page: any;
    predicate: any;
    reverse: any;
    typeOfFlowerNames: Array<string>;
    typeOfFlowers: TypeOfFlower[];
    formGroup: FormGroup;
    typeOfFlowerId: number;
    base64Images: string [];
    innerWidth: number;
    changeBlockWidth: boolean;

    constructor(
        private varietyService: VarietyService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private fb: FormBuilder
    ) {

        this.varieties = [];
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.createForm();
    }

    createForm() {
        this.formGroup = this.fb.group({
            typeOfFlowers: new FormControl()
        });
    }

    loadAll() {
        this.varietyService.getVarietiesByTypeOfFlowers(this.typeOfFlowerId).subscribe(
            (varieties) => this.onSuccess(varieties,  this.typeOfFlowerId),
            (varieties) => this.onError(varieties)
        );
    }

    reset() {
        this.page = 0;
        this.varieties = [];
        this.loadAll();
    }

    ngOnInit() {
        if (!this.typeOfFlowerId) {
            this.fillTypeOfFlowers();
        } else {
            this.loadAll();
        }
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVarieties();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.eventManager.destroy(this.deletedImageEvent);
        this.eventManager.destroy(this.imageEventSubscriber);
        this.eventManager.destroy(this.typeOfFlowersSubscriber);
    }

    trackId(index: number, item: Variety) {
        return item.id;
    }
    registerChangeInVarieties() {
        this.eventSubscriber = this.eventManager.subscribe('varietyListModification', (response) => this.reset());
        this.imageEventSubscriber = this.eventManager.subscribe('imageUploaded', (response) => this.reset());
        this.deletedImageEvent = this.eventManager.subscribe('varietyImageDeleted', (response) => this.reset());
        this.typeOfFlowersSubscriber = this.eventManager.subscribe('changedTypeOfFlowers', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, typeOfFlowersId: number) {
        this.varieties = data;
        this.varietyService.getAllBase64FilesByCurrentCompanyAndTypeOfFlower(typeOfFlowersId).subscribe((response) => {
            this.base64Images = [];
            this.base64Images = response;
        });

    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    onValue(fieldData: any): string {
        if (fieldData) {
            return fieldData.toString().toLowerCase();
        } else {
            return '---';
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
            const text = names[0];
            control.setValue([{id: this.typeOfFlowers[0].name, text}]);
            this.typeOfFlowerId = id[0];
            this.loadAll();
        }
    }

    public selectedTypeOfFlower(value: any): void {
        console.log(value.text);
        this.typeOfFlowers.forEach((t) => {
           if (t.name === value.text) {
               this.typeOfFlowerId = t.id;
               this.eventManager.broadcast({name: 'changedTypeOfFlowers'});
           }
        });
    }

}
