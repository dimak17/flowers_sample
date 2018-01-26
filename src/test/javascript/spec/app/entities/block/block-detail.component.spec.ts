import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BlockDetailComponent } from '../../../../../../main/webapp/app/entities/block/block-detail.component';
import { BlockService } from '../../../../../../main/webapp/app/entities/block/block.service';
import { Block } from '../../../../../../main/webapp/app/entities/block/block.model';

describe('Component Tests', () => {

    describe('Block Management Detail Component', () => {
        let comp: BlockDetailComponent;
        let fixture: ComponentFixture<BlockDetailComponent>;
        let service: BlockService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [BlockDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BlockService,
                    EventManager
                ]
            }).overrideTemplate(BlockDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BlockDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlockService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Block(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.block).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
