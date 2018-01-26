import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanyUserDetailComponent } from '../../../../../../main/webapp/app/entities/company-user/company-user-detail.component';
import { CompanyUserService } from '../../../../../../main/webapp/app/entities/company-user/company-user.service';
import { CompanyUser } from '../../../../../../main/webapp/app/entities/company-user/company-user.model';

describe('Component Tests', () => {

    describe('CompanyUser Management Detail Component', () => {
        let comp: CompanyUserDetailComponent;
        let fixture: ComponentFixture<CompanyUserDetailComponent>;
        let service: CompanyUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [CompanyUserDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CompanyUserService,
                    EventManager
                ]
            }).overrideTemplate(CompanyUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyUserService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompanyUser(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.companyUser).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
