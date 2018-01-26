package com.impltech.web.rest.util;

import com.impltech.domain.ClaimsPolicy;
import com.impltech.domain.ShippingPolicy;

/**
 * Created by alex
 */
public class ShippingPolicyUtils {

    public static ClaimsPolicy convertShippingToClaim (ShippingPolicy shippingPolicy) {
        ClaimsPolicy claimsPolicyResult = new ClaimsPolicy();
        claimsPolicyResult.setShortName(shippingPolicy.getShortName());
        claimsPolicyResult.setFullName(shippingPolicy.getFullName());
        claimsPolicyResult.setId(shippingPolicy.getId() != null ? shippingPolicy.getId() : null);

        return claimsPolicyResult;
    }
}
