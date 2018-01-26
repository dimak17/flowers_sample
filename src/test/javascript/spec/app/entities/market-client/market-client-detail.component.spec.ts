import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketClientDetailComponent } from '../../../../../../main/webapp/app/entities/market-client/market-client-detail.component';
import { MarketClientService } from '../../../../../../main/webapp/app/entities/market-client/market-client.service';
import { MarketClient } from '../../../../../../main/webapp/app/entities/market-client/market-client.model';

describe('Component Tests', () => {

    describe('MarketClient Management Detail Component', () => {
        let comp: MarketClientDetailComponent;
        let fixture: ComponentFixture<MarketClientDetailComponent>;
        let service: MarketClientService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [MarketClientDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketClientService,
                    EventManager
                ]
            }).overrideTemplate(MarketClientDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketClientDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketClientService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketClient(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketClient).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
