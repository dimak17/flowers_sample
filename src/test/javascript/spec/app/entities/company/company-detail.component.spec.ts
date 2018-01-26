import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanyDetailComponent } from '../../../../../../main/webapp/app/entities/company/company-detail.component';
import { CompanyService } from '../../../../../../main/webapp/app/entities/company/company.service';
import { Company } from '../../../../../../main/webapp/app/entities/company/company.model';

describe('Component Tests', () => {

    describe('Company Management Detail Component', () => {
        let comp: CompanyDetailComponent;
        let fixture: ComponentFixture<CompanyDetailComponent>;
        let service: CompanyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [CompanyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompanyService,
                    EventManager
                ]
            }).overrideTemplate(CompanyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Company(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.company).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
