package com.impltech.service.util;

import com.impltech.domain.ClaimsPolicy;
import com.impltech.domain.Company;

import java.util.Arrays;
import java.util.List;

/**
 * @author dima
 */
public class ClaimsPolicyUtil {
    /**
     *
     * @param company
     * @return List<ClaimsPolicy>
     */
    public static List<ClaimsPolicy> getDefaultClaimsPolicyForCompany(Company company) {
        return Arrays.asList(
            new ClaimsPolicy().shortName("FOB").fullName("Free On Board").company(company)

        );
    }
}
