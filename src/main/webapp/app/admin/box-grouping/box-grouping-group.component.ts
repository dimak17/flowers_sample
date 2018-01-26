import {
    ChangeDetectorRef,
    Component,
    ElementRef,
    EventEmitter,
    Input,
    OnDestroy,
    OnInit,
    Output,
    Renderer2
} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {BoxGroupingService} from './box-grouping.service';
import {BoxType} from '../../entities/box-type/box-type.model';
import {EventManager} from 'ng-jhipster';
import {Subscription} from 'rxjs/Subscription';

/**
 * Created by platon on 28.06.17.
 */
@Component({
    selector: 'jhi-flowers-box-grouping-group',
    templateUrl: './box-grouping-group.component.html',
    styleUrls: ['./box-grouping-group.scss'],
    providers: [BoxGroupingService]
})
export class BoxGroupingGroupComponent implements OnInit, OnDestroy {
    @Output()
    onBoxChoose: EventEmitter<ChangeBoxEvent>;

    @Output()
    onQuantityChange: EventEmitter<ChangeBoxEvent>;

    @Input()
    disabledIds: number[];
    @Input()
    disabledQuantities: number;
    @Input()
    groupIndex: number;
    @Input()
    boxIndex: number;
    @Input()
    public boxTypeFormGroup: FormGroup;
    @Input()
    public boxTypes: BoxType[];
    @Input()
    public selectedBox: BoxType;
    @Input()
    public selectedQuantity: number;
    @Input()
    canCollapse: boolean;
    selectedBoxTypeName: string;
    selectedBoxTypeId: number;
    selectedBoxTypeIdPrev: number;
    collapsed: boolean;
    quantities: number[];
    boxDestroyedSubscription: Subscription;
    constructor(
        private cdr: ChangeDetectorRef,
        private eventManager: EventManager,
        private eRef: ElementRef,
        private rd: Renderer2
    ) {
        this.collapsed = false;
        this.onBoxChoose = new EventEmitter<ChangeBoxEvent>();
        this.onQuantityChange = new EventEmitter<ChangeBoxEvent>();
        this.quantities = [1, 2, 3, 4, 5];
    }

    ngOnInit(): void {
        this.selectedBoxTypeName = this.selectedBox ? this.selectedBox.shortName : '';
        this.selectedBoxTypeId  = this.selectedBox ? this.selectedBox.id : 0;
        this.boxDestroyedSubscription = this.eventManager.subscribe(
            'boxGroupingGroupRemovedEvent' + this.groupIndex,
            (event) => this.boxRemovedEvent(event)
        );
    }

    ngOnDestroy(): void {
        this.eventManager.destroy(this.boxDestroyedSubscription);
    }

    toggleGrouping() {
        if (this.canCollapse) {
            this.collapsed = !this.collapsed;
        }
    }

    boxDisabled(id) {
        return this.disabledIds && this.disabledIds.length && this.disabledIds.indexOf(id) !== -1;
    }

    changeBoxModel(event) {
        this.selectedBoxTypeId = event;
        if (this.boxDisabled(event)) {
            this.selectedBoxTypeId = this.selectedBoxTypeIdPrev;
        } else {
            this.selectedBoxTypeIdPrev = event;
        }
    }

    chooseQuantity(event, quantity) {
        event.preventDefault();
        if (this.quantityDisabled(quantity)) {
            return;
        }
        this.onQuantityChange.emit({
            groupIndex: this.groupIndex,
            boxIndex: this.boxIndex,
            quantity
        });
        this.cdr.detectChanges();
    }

    private quantityDisabled(quantity: any) {
        if (this.boxIndex !== 0 ) {
            return this.disabledQuantities && quantity <= this.disabledQuantities;
        } else {
            return quantity > 1;
        }
    }

    chooseBoxTypeId(event, boxTypeId) {
        event.preventDefault();
        if (!this.boxDisabled(boxTypeId)) {
            this.selectedBoxTypeName = this.boxTypes
                .filter((b) => b.id === boxTypeId)[0].shortName;
            this.selectedBoxTypeId = boxTypeId;
            const boxFromList: BoxType = this.boxTypes.filter((b) => b.id === boxTypeId)[0];
            Object.assign(this.selectedBox, boxFromList);
            this.onBoxChoose.emit({
                groupIndex: this.groupIndex,
                boxIndex: this.boxIndex,
                selectedBox: this.selectedBox
            });
        }
        this.cdr.detectChanges();
    }

    getCollapsedClass(reverse: boolean) {
        return {
            'show-global': reverse ? !this.collapsed && this.canCollapse : this.collapsed,
            'hide-global': reverse ? this.collapsed && this.canCollapse : !this.collapsed
        };
    }

    getDisabledQuantityClass(quantity: number) {
        return {
            'disabled-box': this.quantityDisabled(quantity)
        };
    }

    getDisabledClasses(id: number) {
        return {
            'group-collapsed-list-right': true,
            'disabled-box': this.boxDisabled(id)
        };
    }

    getSelectedRadioBoxClasses(id: number) {
        return {
            'selected-radio' : true,
            'selected': id === this.selectedBoxTypeId
        };
    }

    getSelectedRadioQuantityClasses(quantity: number) {
        return {
            'selected-radio' : true,
            'selected': quantity === this.selectedQuantity
        };
    }

    onClickOutside() {
            this.collapsed = false;
    }

    trackByFn(index, item) {
        return index;
    }

    private boxRemovedEvent(event) {
        if (this.boxIndex === 0 && parseInt(event.groupIndex, 10) === this.groupIndex) {
            this.selectedBox = new BoxType();
            this.selectedBoxTypeName = null;
            this.selectedBoxTypeId = null;
            this.selectedQuantity = null;
        }
    }

    onGroupMouseEnter(group) {
        const arrow = group.querySelector('.group-arrow');
        if (arrow && this.groupIndex > 6) {
            this.rd.addClass(arrow, 'font-hover');
            this.rd.setStyle(group, 'cursor', 'pointer');
        }
    }

    onGroupMouseLeave(group) {
        const arrow = group.querySelector('.group-arrow');
        if (arrow && this.groupIndex > 6) {
            this.rd.removeClass(arrow, 'font-hover');
            this.rd.removeStyle(group, 'cursor');
        }
    }

    showValidationMessage(full: boolean): boolean {
        return full
            ? this.boxIndex == 0 && (!this.selectedQuantity || !this.selectedBoxTypeName)
            : this.boxIndex > 0 && (!this.selectedQuantity || !this.selectedBoxTypeName);
    }
}

export interface ChangeBoxEvent {
    groupIndex: number;
    boxIndex: number;
    selectedBox?: BoxType;
    quantity?: number;
}
