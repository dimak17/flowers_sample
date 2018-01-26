package com.impltech.validator.util;

import com.impltech.domain.Season;
import com.impltech.service.SeasonService;

import java.util.List;

/**
 * Created by dima
 */
public class SeasonValidator {

    public static boolean checkForExistingSeason(Season season, SeasonService seasonService) {

        List<Season> seasonList = seasonService.findSeasonsByCurrentCompanyId();
        if(season.getId() == null) {
            for (Season seasonFromDB: seasonList) {
                if (season.getSeasonName().trim().equalsIgnoreCase(seasonFromDB.getSeasonName())
                    && season.getSeasonYear().toString().trim().equalsIgnoreCase(seasonFromDB.getSeasonYear().toString())) {
                    return true;
                }
            }
        } else {
            for (Season seasonFromDB: seasonList) {
                if (season.getSeasonName().trim().equalsIgnoreCase(seasonFromDB.getSeasonName())
                    && season.getSeasonYear().toString().trim().equalsIgnoreCase(seasonFromDB.getSeasonYear().toString())
                    && !season.getId().equals(seasonFromDB.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkLengthValidation(Season season) {
        return (season.getSeasonName().length() > 50 || season.getSeasonYear().toString().length() > 4);
    }
}
