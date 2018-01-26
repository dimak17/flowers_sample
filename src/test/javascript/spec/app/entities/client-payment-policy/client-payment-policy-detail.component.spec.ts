import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClientPaymentPolicyDetailComponent } from '../../../../../../main/webapp/app/entities/client-payment-policy/client-payment-policy-detail.component';
import { ClientPaymentPolicyService } from '../../../../../../main/webapp/app/entities/client-payment-policy/client-payment-policy.service';
import { ClientPaymentPolicy } from '../../../../../../main/webapp/app/entities/client-payment-policy/client-payment-policy.model';

describe('Component Tests', () => {

    describe('ClientPaymentPolicy Management Detail Component', () => {
        let comp: ClientPaymentPolicyDetailComponent;
        let fixture: ComponentFixture<ClientPaymentPolicyDetailComponent>;
        let service: ClientPaymentPolicyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [ClientPaymentPolicyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClientPaymentPolicyService,
                    EventManager
                ]
            }).overrideTemplate(ClientPaymentPolicyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientPaymentPolicyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientPaymentPolicyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClientPaymentPolicy(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.clientPaymentPolicy).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
