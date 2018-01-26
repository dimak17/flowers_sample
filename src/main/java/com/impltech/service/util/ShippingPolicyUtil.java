package com.impltech.service.util;

import com.impltech.domain.Company;
import com.impltech.domain.ShippingPolicy;

import java.util.Arrays;
import java.util.List;

/**
 * @author dima
 */
public class ShippingPolicyUtil {
    /**
     *
     * @param company
     * @return List<ShippingPolicy>
     */
    public static List<ShippingPolicy> getDefaultShippingPolicyForCompany(Company company) {
    return Arrays.asList(
        new ShippingPolicy().shortName("FOB").fullName("Free On Board").company(company)

    );
}
}
