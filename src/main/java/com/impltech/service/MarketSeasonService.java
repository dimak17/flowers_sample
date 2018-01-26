package com.impltech.service;

import com.impltech.domain.MarketSeason;
import com.impltech.repository.MarketSeasonRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author alex
 * Service Implementation for managing MarketSeason.
 */
@Service
@Transactional
public class MarketSeasonService {

    private final Logger log = LoggerFactory.getLogger(MarketSeasonService.class);

    private final MarketSeasonRepository marketSeasonRepository;

    public MarketSeasonService(MarketSeasonRepository marketSeasonRepository) {
        this.marketSeasonRepository = marketSeasonRepository;
    }

    /**
     * Save a marketSeason.
     *
     * @param marketSeason the entity to save
     * @return the persisted entity
     */
    public MarketSeason save(MarketSeason marketSeason) {
        log.debug("Request to save MarketSeason : {}", marketSeason);
        return marketSeasonRepository.save(marketSeason);
    }

    /**
     *  Get all the marketSeasons.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketSeason> findAll() {
        log.debug("Request to get all MarketSeasons");
        return marketSeasonRepository.findAll();
    }

    /**
     *  Get one marketSeason by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MarketSeason findOne(Long id) {
        log.debug("Request to get MarketSeason : {}", id);
        return marketSeasonRepository.findOne(id);
    }


    @Transactional(readOnly = true)
    public MarketSeason findOneByCurrentCompanyId(Long id) {
        log.debug("Request to get MarketSeason : {}", id);
        Long currentCompanyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return marketSeasonRepository.findOneByCurrentCompany(currentCompanyId, id);
    }

    /**
     *  Delete the  marketSeason by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MarketSeason : {}", id);
        marketSeasonRepository.delete(id);
    }


    /**
     *  Get all the marketSeasons by current company id.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketSeason> findAllByCurrentCompany() {
        Long id  = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("Request to get all MarketSeasons by current company id ");
        return marketSeasonRepository.findAllByCurrentCompany(id);
    }

}
