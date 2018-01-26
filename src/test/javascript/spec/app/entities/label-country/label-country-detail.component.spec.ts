import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LabelCountryDetailComponent } from '../../../../../../main/webapp/app/entities/label-country/label-country-detail.component';
import { LabelCountryService } from '../../../../../../main/webapp/app/entities/label-country/label-country.service';
import { LabelCountry } from '../../../../../../main/webapp/app/entities/label-country/label-country.model';

describe('Component Tests', () => {

    describe('LabelCountry Management Detail Component', () => {
        let comp: LabelCountryDetailComponent;
        let fixture: ComponentFixture<LabelCountryDetailComponent>;
        let service: LabelCountryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [LabelCountryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LabelCountryService,
                    EventManager
                ]
            }).overrideTemplate(LabelCountryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LabelCountryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LabelCountryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LabelCountry(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.labelCountry).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
