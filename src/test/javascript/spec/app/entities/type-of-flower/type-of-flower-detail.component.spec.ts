import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TypeOfFlowerDetailComponent } from '../../../../../../main/webapp/app/entities/type-of-flower/type-of-flower-detail.component';
import { TypeOfFlowerService } from '../../../../../../main/webapp/app/entities/type-of-flower/type-of-flower.service';
import { TypeOfFlower } from '../../../../../../main/webapp/app/entities/type-of-flower/type-of-flower.model';

describe('Component Tests', () => {

    describe('TypeOfFlower Management Detail Component', () => {
        let comp: TypeOfFlowerDetailComponent;
        let fixture: ComponentFixture<TypeOfFlowerDetailComponent>;
        let service: TypeOfFlowerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [TypeOfFlowerDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TypeOfFlowerService,
                    EventManager
                ]
            }).overrideTemplate(TypeOfFlowerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeOfFlowerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeOfFlowerService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TypeOfFlower(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.typeOfFlower).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
