/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketVarietyPropertyDetailComponent } from '../../../../../../main/webapp/app/entities/market-variety-property/market-variety-property-detail.component';
import { MarketVarietyPropertyService } from '../../../../../../main/webapp/app/entities/market-variety-property/market-variety-property.service';
import { MarketVarietyProperty } from '../../../../../../main/webapp/app/entities/market-variety-property/market-variety-property.model';

describe('Component Tests', () => {

    describe('MarketVarietyProperty Management Detail Component', () => {
        let comp: MarketVarietyPropertyDetailComponent;
        let fixture: ComponentFixture<MarketVarietyPropertyDetailComponent>;
        let service: MarketVarietyPropertyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [MarketVarietyPropertyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketVarietyPropertyService,
                    EventManager
                ]
            }).overrideTemplate(MarketVarietyPropertyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketVarietyPropertyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketVarietyPropertyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketVarietyProperty(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketVarietyProperty).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
