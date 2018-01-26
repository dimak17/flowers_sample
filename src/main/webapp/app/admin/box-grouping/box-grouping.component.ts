import {AfterViewInit, ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {EventManager, JhiLanguageService} from 'ng-jhipster';
import {FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BoxGroupingService} from './box-grouping.service';
import {Principal} from '../../shared/auth/principal.service';
import {CompanyUser} from '../../entities/company-user/company-user.model';
import {BoxType} from '../../entities/box-type/box-type.model';
import {BoxGroupDTO} from './box-group-dto.model';
import {ChangeBoxEvent} from './box-grouping-group.component';
import {Subscription} from 'rxjs/Subscription';
import {Router} from '@angular/router';
import * as _ from 'lodash';
import {Message} from 'primeng/primeng';

export const DEFAULT_BOX_GROUPING_COUNT = 7;

@Component({
    selector: 'jhi-flowers-box-grouping',
    templateUrl: './box-grouping.component.html',
    styleUrls: ['./box-grouping.scss'],
    providers: [BoxGroupingService]
})
export class BoxGroupingComponent implements OnInit, AfterViewInit, OnDestroy {
    isViewInited: boolean;
    rootFormGroup: FormGroup;
    companyUser: CompanyUser;
    boxGroups: BoxGroupDTO[];
    allBoxTypes: BoxType[];
    disabledBoxIds: number[][][];
    disabledQuantities: number[][][];
    boxGroupsFromDB: BoxGroupDTO[];
    msgs: Message[];
    removeControlsSubscription: Subscription;

    constructor(
        private principal: Principal,
        private boxGroupingService: BoxGroupingService,
        private fb: FormBuilder,
        private eventManager: EventManager,
        private cdr: ChangeDetectorRef,
        private router: Router
    ) {
        this.isViewInited = false;
        this.rootFormGroup = this.fb.group({
            root: this.fb.array([
                this.createNewGroup()
            ])
        });
    }
    ngAfterViewInit(): void {
        this.isViewInited = true;
    }

    ngOnDestroy(): void {
        this.eventManager.destroy(this.removeControlsSubscription);
    }

    ngOnInit(): void {
        this.boxGroupingService.getBoxTypesByCurrentCompany()
            .subscribe((boxTypes: BoxType[]) => {
                this.allBoxTypes = boxTypes;
                this.boxGroupingService.getBoxGroupsByCurrentCompany()
                    .subscribe((boxGroups: BoxGroupDTO[]) => {
                        this.boxGroups = boxGroups;
                        this.boxGroupsFromDB = _.cloneDeep(this.boxGroups);
                        this.buildGroups();
                        this.addLastGroupControls();
                        this.buildDisabledIndexes();
                        this.buildDisabledQuantities();
                });
        });
        this.removeControlsSubscription = this.eventManager.subscribe(
            'boxGroupingRemoveControls',
            (event) => this.removeGroupControlsListener(event)
        );
    }

    private buildGroups() {
        if (this.boxGroups && this.boxGroups.length) {
            for (let i = 0; i < this.boxGroups.length; i++) {
                const b = this.boxGroups[i];
                const newFormGroup = this.addBoxTypeGroup();
                for (let j = 0; j < b.boxTypes.length; j++) {
                    this.addBoxTypesControls(i, j, true);
                }
            }
        }
    }

    addBoxTypesControls(groupIndex = 0, elementIndex = 0, initial = false) {
        const root = this.getRootFormGroup();
        const currGroup = <FormGroup>root.controls[groupIndex];
        const boxTypesControl = <FormArray>currGroup.controls['boxTypeGroups'];
        if (boxTypesControl.length < 5) {
            if (!initial) {
                if (!this.boxGroups[0]) {
                    this.boxGroups.push(this.createEmptyBoxGroupDTO());
                }
                const boxIndex = this.boxGroups[groupIndex].boxTypes.length;
                this.boxGroups[groupIndex].boxTypes.push(new BoxType());
                this.boxGroups[groupIndex].quantities.push(null);
                if (!this.disabledBoxIds[groupIndex]) {
                    this.disabledBoxIds[groupIndex] = [];
                }
                this.disabledBoxIds[groupIndex][boxIndex] = [];
                for (let disabledIndex = 0; disabledIndex < this.boxGroups[groupIndex].boxTypes.length; disabledIndex++) {
                    this.disabledBoxIds[groupIndex][boxIndex].push(this.boxGroups[groupIndex].boxTypes[disabledIndex].id);
                }
                this.buildDisabledQuantities();
            }
            const addBoxTypeCtrl = this.createBoxTypeControl();
            boxTypesControl.push(addBoxTypeCtrl);
            if (initial) {
                const boxTypeId = this.boxGroups[groupIndex].boxTypes[elementIndex].id;
                const quantity = this.boxGroups[groupIndex].quantities[elementIndex];
                addBoxTypeCtrl.get('id').setValue(boxTypeId);
                addBoxTypeCtrl.get('quantity').setValue(quantity);
            }
            this.cdr.detectChanges();
        }
    }

    private getRootFormGroup(): FormArray {
        return (<FormArray>this.rootFormGroup.controls['root']);
    }

    addBoxTypeGroup(): FormGroup {
        const rootArray = <FormArray>this.rootFormGroup.controls['root'];
        const newGroup = this.createNewGroup();
        rootArray.push(newGroup);
        return newGroup;
    }

    private createBoxTypeControl() {
        return this.fb.group({
            id: [null, Validators.required],
            quantity: [null, Validators.required]
        });
    }

    private createNewGroup(): FormGroup {
        return this.fb.group({
            boxTypeGroups: this.fb.array([])
        });
    }

    removeControls(index: number) {
        const root = (<FormArray>this.rootFormGroup.controls['root']);
        const currGroup = <FormGroup>root.controls[index];
        const boxTypesControl = <FormArray>currGroup.controls['boxTypeGroups'];
        const currGroupData = this.boxGroups[index];
        if (boxTypesControl.length === 1 && currGroupData.id) {
            this.router.navigate(
                [
                    { outlets:
                        {
                            popup: 'box-grouping/'
                                + this.boxGroups[index].id
                                + '/delete/'
                                + index
                                + '/groupName/'
                                + this.getGroupName(index)
                        }
                    }
                ],
                { replaceUrl: true });
        } else if (boxTypesControl.length === 1) {
            if (this.boxGroups.length > 0) {
                this.boxGroups.splice(index, 1);
                while (boxTypesControl.length) {
                     boxTypesControl.removeAt(boxTypesControl.length);
                }
            }
            this.cdr.detectChanges();
            root.removeAt(index);
        } else {
            const removeAtIndex = boxTypesControl.length - 1;
            boxTypesControl.removeAt(removeAtIndex);
            if (currGroupData) {
                if (currGroupData.boxTypes[removeAtIndex]) {
                    currGroupData.boxTypes.pop();
                    currGroupData.quantities.pop();
                }
            }
            this.cdr.detectChanges();
        }
    }

    removeGroup(index: number) {
        const root = (<FormArray>this.rootFormGroup.controls['root']);
        if (DEFAULT_BOX_GROUPING_COUNT == index && this.boxGroups.length == DEFAULT_BOX_GROUPING_COUNT + 1) {
            const currGroup = <FormGroup>root.controls[index];
            const boxTypesControl = <FormArray>currGroup.controls['boxTypeGroups'];
            while (boxTypesControl.length !== 1) {
                boxTypesControl.removeAt(boxTypesControl.length - 1);
            }
            this.boxGroups[index].boxTypes.splice(0, this.boxGroups[index].boxTypes.length);
            this.boxGroups[index].boxTypes.push(new BoxType());
            this.boxGroups[index].quantities.splice(0, this.boxGroups[index].quantities.length);
            this.boxGroups[index].quantities.push(null);
            this.eventManager.broadcast({
                name: 'boxGroupingGroupRemovedEvent' + index,
                groupIndex: index
            });
            this.cdr.detectChanges();
        } else {
            root.removeAt(index);
            this.boxGroups.splice(index, 1);
        }
    }

    private addLastGroupControls(addGroup = false) {
        const root = (<FormArray>this.rootFormGroup.controls['root']);
        const groupsControls = root.controls;
        if (addGroup) {
            const appendGroup: FormGroup = this.createNewGroup();
            groupsControls.push(appendGroup);
        }

        const boxGroupDTO = this.createEmptyBoxGroupDTO();
        boxGroupDTO.boxTypes.push(new BoxType());
        boxGroupDTO.quantities.push(null);
        this.boxGroups.push(boxGroupDTO);

        const lastGroup = <FormArray>groupsControls[groupsControls.length - 1];
        const boxTypesControl = <FormArray>lastGroup.controls['boxTypeGroups'];
        boxTypesControl.push(this.createBoxTypeControl());
        this.cdr.detectChanges();
    }

    save(groupIndex: number) {
        const root = (<FormArray>this.rootFormGroup.controls['root']);
        const groupsControls = root.controls;
        const boxTypesControls = (<FormArray> (<FormArray>groupsControls[groupIndex]).controls['boxTypeGroups']);
        if (boxTypesControls.valid) {
            const currentGroup = this.boxGroups[groupIndex];
            const isSaving = !(currentGroup && currentGroup.id);
            if (isSaving) {
                this.boxGroupingService.createGroup(currentGroup).subscribe((data: BoxGroupDTO) => {
                    this.saveCallback(currentGroup, data, groupIndex);
                    this.addLastGroupControls(true);
                });
            } else {
                this.boxGroupingService.updateGroup(currentGroup).subscribe((data: BoxGroupDTO) => {
                    this.saveCallback(currentGroup, data, groupIndex);
                });

            }
        }
    }

    saveCallback(currentGroup: BoxGroupDTO, data: BoxGroupDTO, groupIndex: number) {
        currentGroup.id = data.id;
        this.sync(currentGroup, groupIndex);
        this.showGrowlMessage(currentGroup);
    }

    onQuantityChange(e: ChangeBoxEvent) {
        this.boxGroups[e.groupIndex].quantities[e.boxIndex] = e.quantity;
        this.buildDisabledQuantities();
        this.cdr.detectChanges();
    }

    onBoxChoose(e: ChangeBoxEvent) {
        if (this.isViewInited) {
            const root = (<FormArray>this.rootFormGroup.controls['root']);
            const groupsControls = root.controls;
            const currGroupData = this.boxGroups[e.groupIndex];
            if (<FormArray>groupsControls[e.groupIndex]) {
                const boxTypesControls = (<FormArray> (<FormArray>groupsControls[e.groupIndex]).controls['boxTypeGroups']);
                if (boxTypesControls.at(e.boxIndex)) {
                    const to = boxTypesControls.length;
                    for (let i = 0; i < to; i++) {
                        if (i > e.boxIndex) {
                            boxTypesControls.removeAt(boxTypesControls.length - 1);
                            this.disabledBoxIds[e.groupIndex][i].splice(0, this.disabledBoxIds[e.groupIndex][i].length);
                            currGroupData.boxTypes.pop();
                            currGroupData.quantities.pop();
                        }
                    }
                }
            }
        }
        this.cdr.detectChanges();
    }

    private buildDisabledIndexes() {
        if (!this.disabledBoxIds) {
            this.disabledBoxIds = [];
        }
        if (this.boxGroups && this.boxGroups.length) {
            for (let groupIndex = 0; groupIndex < this.boxGroups.length; groupIndex++) {
                const boxGroup = this.boxGroups[groupIndex];
                const boxTypes = <BoxType[]>boxGroup.boxTypes;
                if (boxTypes && boxTypes.length) {
                    this.disabledBoxIds[groupIndex] = [];
                    for (let elementIndex = 0; elementIndex < boxTypes.length; elementIndex ++) {
                        if (elementIndex) {
                            this.disabledBoxIds[groupIndex][elementIndex] = boxTypes
                                .filter((b, i) => i < elementIndex)
                                .map((b) => b.id);
                        }
                    }
                }
            }
        }
    }

    private buildDisabledQuantities() {
        if (!this.disabledQuantities) {
            this.disabledQuantities = [];
        }
        if (this.boxGroups && this.boxGroups.length) {
            for (let groupIndex = 0; groupIndex < this.boxGroups.length; groupIndex++) {
                const boxGroup = this.boxGroups[groupIndex];
                const quantities = <number[]>boxGroup.quantities;
                if (quantities && quantities.length) {
                    this.disabledQuantities[groupIndex] = [];
                    this.disabledQuantities[groupIndex][0] = [1];
                    for (let elementIndex = 1; elementIndex < quantities.length + 1; elementIndex ++) {
                        this.disabledQuantities[groupIndex][elementIndex] = [quantities[elementIndex - 1]];
                    }
                }
            }
        }
    }

    isShowAddButton(group: FormGroup, groupIndex: number): boolean {
        return group.valid
            && (<FormArray>group.controls.boxTypeGroups).length < 5
            && this.boxGroups[groupIndex]
            && this.boxGroups[groupIndex].quantities
            && this.boxGroups[groupIndex].quantities[this.boxGroups[groupIndex].quantities.length - 1] != 5;
    }

    isShowSaveButton(group: FormGroup, groupIndex: number): boolean {
        return (<FormArray>group.controls.boxTypeGroups).length > 0 && this.isShowWarnMsg(group, groupIndex);
    }

    isShowMinusButton(groupIndex: number): boolean {
        const boxGroupControlsExists = groupIndex > DEFAULT_BOX_GROUPING_COUNT - 1;
        const isLastGroup = groupIndex === DEFAULT_BOX_GROUPING_COUNT - 1;
        if (!boxGroupControlsExists && isLastGroup) {
            return this.boxGroups[groupIndex] && this.boxGroups[groupIndex].boxTypes.length > 1;
        } else {
            const boxesWithoutId = this.boxGroups.filter((b) => b.id == null);
            if (this.boxGroups[groupIndex] && this.boxGroups[groupIndex].id) {
                return true;
            } else {
                return boxesWithoutId.length === 1 && boxesWithoutId[0].boxTypes.length > 1;
            }
        }
    }

    isShowDeleteAllButton(group: FormGroup, groupIndex: number) {
        return (<FormArray>group.controls.boxTypeGroups).length > 0
            && this.boxGroups[groupIndex] != null
            && this.boxGroups[groupIndex].boxTypes != null
            && this.boxGroups[groupIndex].boxTypes.length !== 0
            && this.boxGroups[groupIndex].boxTypes[0] != null
            && this.boxGroups[groupIndex].boxTypes[0].id != null;
    }

    isShowControls(groupIndex: number): boolean {
        return groupIndex > DEFAULT_BOX_GROUPING_COUNT - 1;
    }

    isShowWarnMsg(formGroup: FormGroup, groupIndex: number): boolean {
        if (!formGroup.valid) {
            return false;
        }
        const groupDB = this.boxGroupsFromDB[groupIndex];
        const group = this.boxGroups[groupIndex];
        let equals = true;
        if (groupDB && group) {
            equals = this.checkEquality(groupDB.quantities, group.quantities);
            if (!equals) {
                return true;
            }
            equals = this.checkEquality(
                groupDB.boxTypes.map((b) => b.id),
                group.boxTypes.map((b) => b.id)
            );
            return !equals;
        } else {
            return true;
        }
    }

    private checkEquality(groupDB: number[], group: number[]): boolean {
        let equals = true;
        if (groupDB && group && groupDB.length === group.length) {
            groupDB.forEach((val, index) => {
                equals = equals && val == group[index];
            });
            return equals;
        } else {
            return false;
        }
    }

    private createEmptyBoxGroupDTO(): BoxGroupDTO {
        const boxGroup: BoxGroupDTO = new BoxGroupDTO();
        boxGroup.boxTypes = [];
        boxGroup.quantities = [];
        return boxGroup;
    }

    private removeGroupControlsListener(event: any) {
        this.removeGroup(parseInt(event.groupIndex, 10));
    }

    trackByFn(index, item) {
        return index;
    }

    getGroupName(groupIndex: number): string {
        const currGroup = this.boxGroups[groupIndex];
        return  currGroup ?
                encodeURIComponent(currGroup.boxTypes.map((b) => b.shortName).reduce((a, b) => a + '\/' + b))
            : '';
    }

    private sync(currentGroup: BoxGroupDTO, groupIndex: number) {
        this.boxGroupsFromDB[groupIndex] = _.cloneDeep(currentGroup);
    }

    showGrowlMessage(currentGroup: BoxGroupDTO) {
        this.msgs = [];
        this.msgs.push({
            severity: 'success',
            summary: 'Box grouping saved',
            detail: currentGroup.boxTypes
                .map((b) => b.shortName)
                .reduce((a, b) => a + '/' + b)
        });
    }

    showMessageHandler(group: FormGroup, groupIndex: number): boolean{
        return this.isShowWarnMsg(group, groupIndex);
    }
}
