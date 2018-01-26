package com.impltech.service.util;

import com.impltech.domain.BoxType;
import com.impltech.domain.Company;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dima
 */
public class BoxTypeUtil {

    /**
     * Get Default List of BoxTypes for Company
     * @param company
     * @return List<BoxType>
     */
    public static List<BoxType> getDefaultBoxTypes4Company(Company company) {
        return Arrays.asList(
            new BoxType().shortName("QB").fullName("Quarter Box").company(company),
            new BoxType().shortName("HB").fullName("Half Box").company(company),
            new BoxType().shortName("FB").fullName("Full Box").company(company)
        );
    }
}
