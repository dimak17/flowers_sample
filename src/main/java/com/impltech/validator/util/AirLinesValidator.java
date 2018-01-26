package com.impltech.validator.util;

import com.impltech.domain.AirLines;
import com.impltech.service.AirLinesServiceSH;

/**
 * Created by alex
 */
public class AirLinesValidator {

    public static boolean checkID(AirLines airLines) {
        return (airLines.getId() != null);
    }

    public  static boolean checkFieldsLength(AirLines airLines) {
        return ((airLines.getName().length() <= 25) && (airLines.getNumber().toString().length() <=5));
    }

    public static boolean AirLinesIdentityCheck(AirLines airLines, AirLinesServiceSH airLinesServiceSH) {
        for (AirLines airLinesFromList : airLinesServiceSH.findAirLinesByCompanyId()) {
            if (airLinesFromList.getName().equals(airLines.getName())) {
                return true;
            }
        }
        return false;
    }
}
