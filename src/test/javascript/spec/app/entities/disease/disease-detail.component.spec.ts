import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DiseaseDetailComponent } from '../../../../../../main/webapp/app/entities/disease/disease-detail.component';
import { DiseaseService } from '../../../../../../main/webapp/app/entities/disease/disease.service';
import { Disease } from '../../../../../../main/webapp/app/entities/disease/disease.model';

describe('Component Tests', () => {

    describe('Disease Management Detail Component', () => {
        let comp: DiseaseDetailComponent;
        let fixture: ComponentFixture<DiseaseDetailComponent>;
        let service: DiseaseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [DiseaseDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DiseaseService,
                    EventManager
                ]
            }).overrideTemplate(DiseaseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiseaseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiseaseService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Disease(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.disease).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
