import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketBoxVarietyPropertyDetailComponent } from '../../../../../../main/webapp/app/entities/market-box-variety-property/market-box-variety-property-detail.component';
import { MarketBoxVarietyPropertyService } from '../../../../../../main/webapp/app/entities/market-box-variety-property/market-box-variety-property.service';
import { MarketBoxVarietyProperty } from '../../../../../../main/webapp/app/entities/market-box-variety-property/market-box-variety-property.model';

describe('Component Tests', () => {

    describe('MarketBoxVarietyProperty Management Detail Component', () => {
        let comp: MarketBoxVarietyPropertyDetailComponent;
        let fixture: ComponentFixture<MarketBoxVarietyPropertyDetailComponent>;
        let service: MarketBoxVarietyPropertyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [MarketBoxVarietyPropertyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketBoxVarietyPropertyService,
                    EventManager
                ]
            }).overrideTemplate(MarketBoxVarietyPropertyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketBoxVarietyPropertyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketBoxVarietyPropertyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketBoxVarietyProperty(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketBoxVarietyProperty).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
