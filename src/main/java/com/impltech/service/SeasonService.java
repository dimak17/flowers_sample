package com.impltech.service;

import com.impltech.domain.MarketSeason;
import com.impltech.domain.Season;
import com.impltech.repository.SeasonRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * @author dima
 * Service Implementation for managing Season.
 */
@Service
@Transactional
public class SeasonService {

    private final Logger log = LoggerFactory.getLogger(SeasonService.class);

    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;

    }

    /**
     * Save a season.
     *
     * @param season the entity to save
     * @return the persisted entity
     */
    public Season save(Season season) {
        log.debug("Request to save Season : {}", season);
        season.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        Set<MarketSeason> marketSeasons = season.getMarketSeasons();
        season.setMarketSeasons(new HashSet<>());
        Season season1 = seasonRepository.save(season);
        season.setMarketSeasons(
            marketSeasons.stream().peek(marketSeason -> marketSeason.setSeason(season1)).collect(toSet())
        );
        this.seasonRepository.save(season);

         return season;
    }

    @Transactional(readOnly = true)
    public List<Season> findSeasonsByCurrentCompanyId() {
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return new ArrayList<>(seasonRepository.findAllByCurrentCompanyId(companyId));
    }

    /**
     *  Get one season by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Season findOneByCompanyId(Long id) {
        log.debug("Request to get Season : {}", id);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return seasonRepository.findOneByCurrentCompanyId(id, companyId);
    }

    /**
     *  Delete the  season by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Season : {}", id);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        seasonRepository.deleteOneByCurrentCompanyId(id, companyId);
    }
}
