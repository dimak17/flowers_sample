package com.impltech.validator.util;

import com.impltech.domain.ClaimsPolicy;
import com.impltech.domain.ShippingPolicy;

/**
 * @author alex
 */
public class PolicyValidator {

    public static boolean checkLengthValidationShippingPolicy(ShippingPolicy shippingPolicy) {
        if(shippingPolicy.getShortName().length() > 17 ||shippingPolicy.getFullName().length() > 25)  {
            return false;
        }
        return false;
    }

    public static boolean checkLengthValidationClaimsPolicy(ClaimsPolicy claimsPolicy) {
        if(claimsPolicy.getShortName().length() > 17 ||claimsPolicy.getFullName().length() > 25)  {
            return false;
        }
        return false;
    }
}
