package com.impltech.service;

import com.impltech.domain.MarketVariety;
import com.impltech.repository.MarketVarietyRepositorySH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author alex
 * Service Implementation for managing MarketVariety.
 */
@Service
@Transactional
public class MarketVarietyServiceSH {

    private final Logger log = LoggerFactory.getLogger(MarketVarietyServiceSH.class);

    private final MarketVarietyRepositorySH marketVarietyRepository;

    public MarketVarietyServiceSH(MarketVarietyRepositorySH marketVarietyRepository) {
        this.marketVarietyRepository = marketVarietyRepository;
    }

    /**
     * Save a marketVariety.
     *
     * @param marketVariety the entity to save
     * @return the persisted entity
     */
    public MarketVariety save(MarketVariety marketVariety) {
        log.debug("Request to save MarketVariety : {}", marketVariety);
        return marketVarietyRepository.save(marketVariety);
    }

    /**
     *  Get all the marketVarieties.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketVariety> findAll() {
        log.debug("Request to get all MarketVarieties");
        return marketVarietyRepository.findAll();
    }

    /**
     *  Get all the marketVarieties by market id.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketVariety> findByMarketId(Long id) {
        log.debug("Request to get all MarketVarieties");
        return marketVarietyRepository.findByMarketId(id);
    }




    /**
     *  Get one marketVariety by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MarketVariety findOne(Long id) {
        log.debug("Request to get MarketVariety : {}", id);
        return marketVarietyRepository.findOne(id);
    }

    /**
     *  Delete the  marketVariety by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MarketVariety : {}", id);
        marketVarietyRepository.delete(id);
    }

}
