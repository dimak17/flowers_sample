import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PriceListDetailComponent } from '../../../../../../main/webapp/app/entities/price-list/price-list-detail.component';
import { PriceListService } from '../../../../../../main/webapp/app/entities/price-list/price-list.service';
import { PriceList } from '../../../../../../main/webapp/app/entities/price-list/price-list.model';

describe('Component Tests', () => {

    describe('PriceList Management Detail Component', () => {
        let comp: PriceListDetailComponent;
        let fixture: ComponentFixture<PriceListDetailComponent>;
        let service: PriceListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [PriceListDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PriceListService,
                    EventManager
                ]
            }).overrideTemplate(PriceListDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PriceListDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriceListService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PriceList(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.priceList).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
