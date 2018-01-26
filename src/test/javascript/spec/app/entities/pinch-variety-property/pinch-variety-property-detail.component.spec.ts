import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PinchVarietyPropertyDetailComponent } from '../../../../../../main/webapp/app/entities/pinch-variety-property/pinch-variety-property-detail.component';
import { PinchVarietyPropertyService } from '../../../../../../main/webapp/app/entities/pinch-variety-property/pinch-variety-property.service';
import { PinchVarietyProperty } from '../../../../../../main/webapp/app/entities/pinch-variety-property/pinch-variety-property.model';

describe('Component Tests', () => {

    describe('PinchVarietyProperty Management Detail Component', () => {
        let comp: PinchVarietyPropertyDetailComponent;
        let fixture: ComponentFixture<PinchVarietyPropertyDetailComponent>;
        let service: PinchVarietyPropertyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [PinchVarietyPropertyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PinchVarietyPropertyService,
                    EventManager
                ]
            }).overrideTemplate(PinchVarietyPropertyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PinchVarietyPropertyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PinchVarietyPropertyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PinchVarietyProperty(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pinchVarietyProperty).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
