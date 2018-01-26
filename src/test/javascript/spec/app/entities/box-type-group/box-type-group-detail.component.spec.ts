import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BoxTypeGroupDetailComponent } from '../../../../../../main/webapp/app/entities/box-type-group/box-type-group-detail.component';
import { BoxTypeGroupService } from '../../../../../../main/webapp/app/entities/box-type-group/box-type-group.service';
import { BoxTypeGroup } from '../../../../../../main/webapp/app/entities/box-type-group/box-type-group.model';

describe('Component Tests', () => {

    describe('BoxTypeGroup Management Detail Component', () => {
        let comp: BoxTypeGroupDetailComponent;
        let fixture: ComponentFixture<BoxTypeGroupDetailComponent>;
        let service: BoxTypeGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [BoxTypeGroupDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BoxTypeGroupService,
                    EventManager
                ]
            }).overrideTemplate(BoxTypeGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BoxTypeGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BoxTypeGroupService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BoxTypeGroup(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.boxTypeGroup).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
