import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketVarietyDetailComponent } from '../../../../../../main/webapp/app/entities/market-variety/market-variety-detail.component';
import { MarketVarietyService } from '../../../../../../main/webapp/app/entities/market-variety/market-variety.service';
import { MarketVariety } from '../../../../../../main/webapp/app/entities/market-variety/market-variety.model';

describe('Component Tests', () => {

    describe('MarketVariety Management Detail Component', () => {
        let comp: MarketVarietyDetailComponent;
        let fixture: ComponentFixture<MarketVarietyDetailComponent>;
        let service: MarketVarietyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [MarketVarietyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketVarietyService,
                    EventManager
                ]
            }).overrideTemplate(MarketVarietyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketVarietyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketVarietyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketVariety(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketVariety).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
