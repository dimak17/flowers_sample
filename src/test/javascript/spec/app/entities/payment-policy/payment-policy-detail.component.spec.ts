import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FlowersTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PaymentPolicyDetailComponent } from '../../../../../../main/webapp/app/entities/payment-policy/payment-policy-detail.component';
import { PaymentPolicyService } from '../../../../../../main/webapp/app/entities/payment-policy/payment-policy.service';
import { PaymentPolicy } from '../../../../../../main/webapp/app/entities/payment-policy/payment-policy.model';

describe('Component Tests', () => {

    describe('PaymentPolicy Management Detail Component', () => {
        let comp: PaymentPolicyDetailComponent;
        let fixture: ComponentFixture<PaymentPolicyDetailComponent>;
        let service: PaymentPolicyService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FlowersTestModule],
                declarations: [PaymentPolicyDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PaymentPolicyService,
                    EventManager
                ]
            }).overrideTemplate(PaymentPolicyDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentPolicyDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentPolicyService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PaymentPolicy(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.paymentPolicy).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
