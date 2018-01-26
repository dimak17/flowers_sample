package com.impltech.service;

import com.impltech.domain.Market;
import com.impltech.repository.MarketRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author platon
 * Service Implementation for managing Market.
 */
@Service
@Transactional
public class MarketService {

    private final Logger log = LoggerFactory.getLogger(MarketService.class);

    private final MarketRepository marketRepository;

    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    /**
     * Save a market.
     *
     * @param market the entity to save
     * @return the persisted entity
     */
    public Market save(Market market) {
        log.debug("Request to save Market : {}", market);
        return marketRepository.save(market);
    }

    /**
     *  Get all the markets.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Market> findAll() {
        log.debug("Request to get all Markets");
        return marketRepository.findAllWithEagerRelationships();
    }

    @Transactional(readOnly = true)
    public List<Market> findAllByCurrentCompany() {
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("Request to get all Markets by Current Company Id");
        return marketRepository.findAllByCurrentCompanyId(companyId);
    }

    /**
     *  Get one market by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Market findOne(Long id) {
        log.debug("Request to get Market : {}", id);
        return marketRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Get one market by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Market findOneByCurrentCompanyId(Long id) {
        log.debug("Request to get Market : {}", id);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return marketRepository.findOneByCurrentCompanyId(companyId, id);
    }


    /**
     *  Delete the  market by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Market : {}", id);
        marketRepository.delete(id);
    }
}
