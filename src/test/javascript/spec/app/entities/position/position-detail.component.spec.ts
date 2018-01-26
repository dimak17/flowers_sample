import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PositionDetailComponent } from '../../../../../../main/webapp/app/entities/position/position-detail.component';
import { PositionService } from '../../../../../../main/webapp/app/entities/position/position.service';
import { Position } from '../../../../../../main/webapp/app/entities/position/position.model';

describe('Component Tests', () => {

    describe('Position Management Detail Component', () => {
        let comp: PositionDetailComponent;
        let fixture: ComponentFixture<PositionDetailComponent>;
        let service: PositionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [PositionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PositionService,
                    EventManager
                ]
            }).overrideTemplate(PositionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PositionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PositionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Position(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.position).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
