package com.impltech.validator.util;

import com.impltech.domain.BoxType;

/**
 * Created by dima
 */
public class BoxTypeValidator {

    public static boolean checkLengthValidation(BoxType boxType) {
        return (boxType.getShortName().length() > 5 || boxType.getFullName().length() > 13);
    }
}
