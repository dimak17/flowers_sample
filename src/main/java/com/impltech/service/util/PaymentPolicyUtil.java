package com.impltech.service.util;

import com.impltech.domain.Company;
import com.impltech.domain.PaymentPolicy;

import java.util.Arrays;
import java.util.List;

/**
 * @author platon
 */
public class PaymentPolicyUtil {

    /**
     *
     * @param company
     * @return List<PaymentPolicy>
     */
    public static List<PaymentPolicy> getDefaultPaymentPolicyForCompany(Company company) {
        return Arrays.asList(
            new PaymentPolicy().name("Prepay").company(company),
            new PaymentPolicy().name("Credit").company(company)
        );
    }
}
