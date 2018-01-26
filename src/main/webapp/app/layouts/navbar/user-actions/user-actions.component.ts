import {AfterViewInit, Component, ElementRef, Input, OnInit, Renderer2} from '@angular/core';
import {UserActionService} from './user-actions.service';
import {CompanyUser} from '../../../entities/company-user/company-user.model';
import {Principal} from '../../../shared/auth/principal.service';
import { Position } from '../../../entities/position/position.model';
import {SelectItem} from 'primeng/primeng';

@Component({
    selector: 'jhi-flowers-user-actions',
    templateUrl: './user-actions.component.html',
    providers: [
        UserActionService
    ]
})
export class UserActionsComponent implements OnInit, AfterViewInit {
    companyUser: CompanyUser;
    positions: Position[];
    selectedPosition: Position;
    positionItems: SelectItem[];
    selectedPositionItems: SelectItem[];
    constructor(
        private principal: Principal,
        private elRef: ElementRef,
        private rd: Renderer2
    ) {
        this.selectedPositionItems = [];
    }

    ngAfterViewInit(): void {
        const el = this.elRef.nativeElement.querySelector('.actions-container .ui-dropdown');
        const arrowElem   = el.querySelector('.fa-caret-down');
        this.rd.removeClass(arrowElem,  'fa-caret-down');
        this.rd.addClass(arrowElem,  'fa-angle-down');
    }

    ngOnInit(): void {
        this.principal.identity().then((account) => {
            this.companyUser = account;
            this.positions = this.companyUser.positions;
            this.positionItems = (<Position[]>this.positions)
                .map((p) => <SelectItem>{label: p.name.split('_')
                    .map((word) => word.toLowerCase())
                    .reduce((a, b) => a[0].toUpperCase() + a.substring(1, a.length)  + ' ' + b ),
                value: p.id.toString(10)});
            if (this.positions) {
                this.selectedPosition = this.positions[0];
            }
        });
    }

    selectRole(position) {
        console.log(position);
    }
}
