import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PinchVarietyDetailComponent } from '../../../../../../main/webapp/app/entities/pinch-variety/pinch-variety-detail.component';
import { PinchVarietyService } from '../../../../../../main/webapp/app/entities/pinch-variety/pinch-variety.service';
import { PinchVariety } from '../../../../../../main/webapp/app/entities/pinch-variety/pinch-variety.model';

describe('Component Tests', () => {

    describe('PinchVariety Management Detail Component', () => {
        let comp: PinchVarietyDetailComponent;
        let fixture: ComponentFixture<PinchVarietyDetailComponent>;
        let service: PinchVarietyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [PinchVarietyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PinchVarietyService,
                    EventManager
                ]
            }).overrideTemplate(PinchVarietyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PinchVarietyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinchVarietyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PinchVariety(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pinchVariety).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
