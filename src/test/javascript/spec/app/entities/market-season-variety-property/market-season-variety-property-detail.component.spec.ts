/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketSeasonVarietyPropertyDetailComponent } from '../../../../../../main/webapp/app/entities/market-season-variety-property/market-season-variety-property-detail.component';
import { MarketSeasonVarietyPropertyService } from '../../../../../../main/webapp/app/entities/market-season-variety-property/market-season-variety-property.service';
import { MarketSeasonVarietyProperty } from '../../../../../../main/webapp/app/entities/market-season-variety-property/market-season-variety-property.model';

describe('Component Tests', () => {

    describe('MarketSeasonVarietyProperty Management Detail Component', () => {
        let comp: MarketSeasonVarietyPropertyDetailComponent;
        let fixture: ComponentFixture<MarketSeasonVarietyPropertyDetailComponent>;
        let service: MarketSeasonVarietyPropertyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [MarketSeasonVarietyPropertyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketSeasonVarietyPropertyService,
                    EventManager
                ]
            }).overrideTemplate(MarketSeasonVarietyPropertyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketSeasonVarietyPropertyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketSeasonVarietyPropertyService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketSeasonVarietyProperty(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketSeasonVarietyProperty).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
