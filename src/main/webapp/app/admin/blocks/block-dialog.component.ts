import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';
import { AbstractControl, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { BlockPopupService } from './block-popup.service';
import { BlockService } from './block.service';
import { Variety } from '../../entities/variety/variety.model';
import { Block } from '../../entities/block/block.model';
import { LATIN_VALIDATION, DIGITS } from '../../shared';
import { TypeOfFlower } from '../../entities/type-of-flower/type-of-flower.model';
import { Principal } from '../../shared/auth/principal.service';
import { isUndefined } from 'util';
import { Company } from '../../entities/company/company.model';

@Component({
    selector: 'jhi-company-dialog',
    templateUrl: './block-dialog.component.html',
    styleUrls: ['block-dialog.scss']
})
export class BlockDialogComponent implements OnInit {

    isSaving: boolean;
    varieties: Variety[];
    chosenVarieties: Variety[];
    selectedVarieties: Variety[];
    selectedVarietiesTmp: Variety[];
    isAllButton: boolean;
    block: Block;
    formGroup: FormGroup;
    typeOfFlowers: TypeOfFlower[];
    typeOfFlowerNames: Array<string>;
    company: Company;
    isDuplicateName: boolean;
    blockNameDuplicateTmp: string;

    constructor(
        private principal: Principal,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private blockService: BlockService,
        private eventManager: EventManager,
        private fb: FormBuilder
    ) {
        this.createForm();
    }

    createForm() {
        this.formGroup = this.fb.group({
            typeOfFlowers: new FormControl(),
        });
    }

    ngOnInit() {
        this.typeOfFlowers = [];
        this.chosenVarieties = [];
        this.principal.identity().then((companyUser) => {
            this.company = companyUser.company;
            this.fillVarieties();
        });
        this.selectedVarieties = [];
        this.selectedVarietiesTmp = [];
        this.isSaving = false;
        this.isAllButton = false;
        if (!this.block) {
            this.block = new Block;
        }
    }

    private fillVarieties() {
        this.blockService.findVarietiesByIdCompany().subscribe((varieties) => {
            this.varieties = varieties;

            if (this.block && this.block.varieties) {
                for (let i = 0; i < this.block.varieties.length; i++) {
                    for (let y = 0; y < this.varieties.length; y++) {
                        if (this.block.varieties[i].id === this.varieties[y].id) {
                            this.selectedVarieties.push(this.varieties[y]);
                            this.selectedVarietiesTmp.push(this.varieties[y]);
                            break;
                        }
                    }
                }
            }
            this.fillTypeOfFlowers();
        });
    }

        public selectedTypeOfFlower(value: string): void {
            if (this.isAllButton) {
                for (let i = 0; i < this.chosenVarieties.length; i++) {
                    for (let y = 0; y < this.selectedVarieties.length; y++) {
                        if (this.chosenVarieties[i].id === this.selectedVarieties[y].id) {
                            if (this.selectedVarietiesTmp.indexOf(this.selectedVarieties[y]) === -1) {
                                this.selectedVarietiesTmp.push(this.selectedVarieties[y]);
                            }
                            break;
                        }
                    }
                }
            }
            this.fillVarietiesByTypeOfFlower(value);
        }

    private fillTypeOfFlowers() {
        this.blockService.findAllTypeOfFlowerByIdCompany().subscribe((typeOfFlowers) => {
            this.typeOfFlowers = typeOfFlowers;
            this.fillTypeOfFlowerNames();
            this.initFormValues();
        });
    }

    private fillTypeOfFlowerNames() {
        this.typeOfFlowerNames = this.typeOfFlowers.map((t: TypeOfFlower) => t.name);
    }

    private initFormValues() {
        this.setTypeOfFlowersValue(this.formGroup.get('typeOfFlowers'));
    }

    private setTypeOfFlowersValue(control: AbstractControl) {
        const names = this.typeOfFlowers.map((t) => t.name);
        if (names && names.length && names[0]) {
            const text = names[0];
            control.setValue([{id: this.typeOfFlowers[0].name, text}]);
            this.fillVarietiesByTypeOfFlower(text);
        }
    }

    private fillVarietiesByTypeOfFlower(name) {
        if (name) {
            this.chosenVarieties = this.varieties.filter((v) =>
                v.typeOfFlower.name === (isUndefined(name.id) ? name : name.id));

            for (const variety of this.chosenVarieties) {
                if (-1 === this.selectedVarieties.indexOf(variety)) {
                    this.isAllButton = false;
                    break;
                } else {
                    this.isAllButton = true;
                }
            }
        }
    }

    onSelect(variety: Variety) {
        let isRemovedVariety: boolean;
        if (!this.isAllButton) {
            const index: number = this.selectedVarieties.indexOf(variety);
            if (index !== -1) {
                this.selectedVarieties.splice(index, 1);
                this.selectedVarietiesTmp.splice(index, 1);
                isRemovedVariety = true;
            }
            if (!isRemovedVariety) {
                this.selectedVarieties.push(variety);
                this.selectedVarietiesTmp.push(variety);
            }
        }
    }

    useAllButton() {
        this.isAllButton = !this.isAllButton;
        if (this.isAllButton) {
            this.selectAllVarieties(this.selectedVarieties);
        } else {
            this.movedSelectedVarieties();
        }

    }

    selectAllVarieties(varieties: Variety[]) {
        for (let i = 0; i < this.chosenVarieties.length; i++) {
            varieties.push(this.chosenVarieties[i]);
        }
    }

    useVarButton() {
        this.isAllButton = !this.isAllButton;
        if (!this.isAllButton) {
            this.movedSelectedVarieties();
        } else {
            this.selectAllVarieties(this.selectedVarieties);
        }
    }

    movedSelectedVarieties() {
        this.selectedVarieties.splice(0, this.selectedVarieties.length);
        for (let i = 0; i < this.selectedVarietiesTmp.length; i++) {
            this.selectedVarieties[i] = this.selectedVarietiesTmp[i];
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        if (this.saveButtonDeactivation(this.block)) {
        this.isSaving = true;
            if (this.block) {
                this.block.varieties = [];
                for (let i = 0; i < this.selectedVarieties.length; i++) {
                    this.block.varieties[i] = this.selectedVarieties[i];
                }
            }
            if (this.block.id !== undefined) {
                this.subscribeToSaveResponse(
                    this.blockService.update(this.block), false);
            } else {
                this.block.company = this.company;
                this.subscribeToSaveResponse(
                    this.blockService.create(this.block), true);
            }
        }
    }

    private subscribeToSaveResponse(result: Observable<Block>, isCreated: boolean) {
        result.subscribe((res: Block) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Block, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'flowersApp.company.created'
            : 'flowersApp.company.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'blockListModification', content: 'OK'});
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

        if (error.headers.get('X-flowersApp-error') === 'error.DuplicateName') {
            this.isDuplicateName = true;
            this.blockNameDuplicateTmp = this.block.name.toLowerCase().trim();
        } else {
        this.alertService.error(error.message, null, null);
        }
    }

    isDuplicateValidation(blockName: string): boolean {
        if (blockName && this.blockNameDuplicateTmp) {
            return this.blockNameDuplicateTmp === this.block.name.toLowerCase().trim();
        }
    }

    isLatinValidation(blockName: string): boolean {
        if (blockName) {
            return !blockName.match(LATIN_VALIDATION);
        }
    }

    isNumberValidation(beds: number): boolean {
        if (beds) {
            return !beds.toString().match(DIGITS);
        }
    }

    isFillValidation(fieldData: any): boolean {
        return (fieldData && fieldData.toString().trim());
    }

    isFieldLengthValidation(fieldData: any): boolean {
        if (fieldData && fieldData.length) {
            return fieldData.length < 11;
        }
        return true;
    }

    isBedsLengthValidation(beds: any): boolean {
        if (beds) {
            return beds.toString().length < 6;
        }
        return true;
    }

    saveButtonDeactivation(block: Block): boolean {
        return (this.isFillValidation(block.name) && this.isFieldLengthValidation(block.name) &&
                !this.isLatinValidation(block.name) && this.isFillValidation(block.beds) &&
                this.isBedsLengthValidation(block.beds) && !this.isNumberValidation(block.beds));
    }
}

@Component({
    selector: 'jhi-company-popup',
    template: ''
})
export class BlockPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private blockPopupService: BlockPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id']) {
                this.modalRef = this.blockPopupService
                    .open(BlockDialogComponent, 'create_edit-block', params['id']);
            } else {
                this.modalRef = this.blockPopupService
                    .open(BlockDialogComponent, 'create_edit-block');
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
