package com.impltech.validator.util;

import com.impltech.domain.Variety;

/**
 * Created by dima
 */
public class VarietyValidator {


    public static boolean checkLengthValidation(Variety variety) {
        if(variety.getName().length() > 35 || variety.getColor().length() > 25
        || (variety.getBreeder() == null || variety.getBreeder().length() > 25)
        || (variety.getMinLength() == null || variety.getMinLength().toString().length() > 4)
        || (variety.getMaxLength() == null || variety.getMaxLength().toString().length() > 4)) {
            return false;
        }
        return true;
    }
}
