import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BoxGroupDetailComponent } from '../../../../../../main/webapp/app/entities/box-group/box-group-detail.component';
import { BoxGroupService } from '../../../../../../main/webapp/app/entities/box-group/box-group.service';
import { BoxGroup } from '../../../../../../main/webapp/app/entities/box-group/box-group.model';

describe('Component Tests', () => {

    describe('BoxGroup Management Detail Component', () => {
        let comp: BoxGroupDetailComponent;
        let fixture: ComponentFixture<BoxGroupDetailComponent>;
        let service: BoxGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [BoxGroupDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BoxGroupService,
                    EventManager
                ]
            }).overrideTemplate(BoxGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BoxGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BoxGroupService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BoxGroup(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.boxGroup).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
