package com.impltech.web.rest.util;

import com.impltech.domain.ClaimsPolicy;
import com.impltech.domain.ShippingPolicy;

/**
 * Created by alex
 */
public class ClaimsPolicyUtils {

    public static ShippingPolicy convertClaimToShipping (ClaimsPolicy claimsPolicy) {
        ShippingPolicy shippingPolicyResult = new ShippingPolicy();
        shippingPolicyResult.setShortName(claimsPolicy.getShortName());
        shippingPolicyResult.setFullName(claimsPolicy.getFullName());
        shippingPolicyResult.setId(claimsPolicy.getId() != null ? claimsPolicy.getId() : null);

        return shippingPolicyResult;
    }
}
