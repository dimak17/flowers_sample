import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketBoxDetailComponent } from '../../../../../../main/webapp/app/entities/market-box/market-box-detail.component';
import { MarketBoxService } from '../../../../../../main/webapp/app/entities/market-box/market-box.service';
import { MarketBox } from '../../../../../../main/webapp/app/entities/market-box/market-box.model';

describe('Component Tests', () => {

    describe('MarketBox Management Detail Component', () => {
        let comp: MarketBoxDetailComponent;
        let fixture: ComponentFixture<MarketBoxDetailComponent>;
        let service: MarketBoxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [MarketBoxDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketBoxService,
                    EventManager
                ]
            }).overrideTemplate(MarketBoxDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketBoxDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketBoxService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketBox(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketBox).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
