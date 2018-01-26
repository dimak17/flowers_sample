package com.impltech.service.util;

import com.impltech.domain.Company;
import com.impltech.domain.Position;
import com.impltech.repository.AuthorityRepository;
import com.impltech.security.AuthoritiesConstants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by platon
 */
public class PositionsUtil {

    public static final String SALES_MANAGER = "SALES_MANAGER";
    public static final String SALES_ASSISTANT = "SALES_ASSISTANT";
    public static final String COMPANY_OWNER = "COMPANY_OWNER";
    public static final String AGRONOM_ENGINEER = "AGRONOM_ENGINEER";
    public static final String POST_HARVEST_MANAGER = "POST_HARVEST_MANAGER";
    public static final String GENERAL_MANAGER = "GENERAL_MANAGER";
    public static final String ACCOUNTANT_MANAGER = "ACCOUNTANT_MANAGER";
    public static final String ACCOUNTANT_ASSISTANT = "ACCOUNTANT_ASSISTANT";
    public static final String CLAIMS_MANAGER = "CLAIMS_MANAGER";
    public static final String CLAIMS_ASSISTANT = "CLAIMS_ASSISTANT";
    public static final String COORDINATION_MANAGER = "COORDINATION_MANAGER";
    public static final String SYSTEM_MANAGER = "SYSTEM_MANAGER";


    public static List<Position> getDefaultPositions4Company(Company company) {
        AuthorityRepository authorityRepository = StaticContextAccessor.getBean(AuthorityRepository.class);
        return Arrays.asList(
            new Position()
                .name(SALES_MANAGER)
                .authority(authorityRepository.findOne(AuthoritiesConstants.SALES_MANAGER))
                .company(company),
            new Position()
                .name(SALES_ASSISTANT)
                .authority(authorityRepository.findOne(AuthoritiesConstants.SALES_ASSISTANT))
                .company(company),
            new Position()
                .name(COMPANY_OWNER)
                .authority(authorityRepository.findOne(AuthoritiesConstants.COMPANY_OWNER))
                .company(company),
            new Position()
                .name(AGRONOM_ENGINEER)
                .authority(authorityRepository.findOne(AuthoritiesConstants.AGRONOM_ENGINEER))
                .company(company),
            new Position()
                .name(POST_HARVEST_MANAGER)
                .authority(authorityRepository.findOne(AuthoritiesConstants.POST_HARVEST_MANAGER))
                .company(company),
            new Position()
                .name(GENERAL_MANAGER)
                .authority(authorityRepository.findOne(AuthoritiesConstants.GENERAL_MANAGER))
                .company(company),
            new Position()
                .name(ACCOUNTANT_MANAGER)
                .authority(authorityRepository.findOne(AuthoritiesConstants.ACCOUNTANT_MANAGER))
                .company(company),
            new Position()
                .name(ACCOUNTANT_ASSISTANT)
                .authority(authorityRepository.findOne(AuthoritiesConstants.ACCOUNTANT_ASSISTANT))
                .company(company),
            new Position()
                .name(CLAIMS_MANAGER)
                .authority(authorityRepository.findOne(AuthoritiesConstants.CLAIMS_MANAGER))
                .company(company),
            new Position()
                .name(CLAIMS_ASSISTANT)
                .authority(authorityRepository.findOne(AuthoritiesConstants.CLAIMS_ASSISTANT))
                .company(company),
            new Position()
                .name(COORDINATION_MANAGER)
                .authority(authorityRepository.findOne(AuthoritiesConstants.COORDINATION_MANAGER))
                .company(company),
            new Position()
                .name(SYSTEM_MANAGER)
                .authority(authorityRepository.findOne(AuthoritiesConstants.SYSTEM_MANAGER))
                .company(company)
        );
    }

}
