package com.impltech.service;

import com.impltech.domain.MarketBox;
import com.impltech.repository.MarketBoxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author alex
 * Service Implementation for managing MarketBox.
 */
@Service
@Transactional
public class MarketBoxService {

    private final Logger log = LoggerFactory.getLogger(MarketBoxService.class);

    private final MarketBoxRepository marketBoxRepository;

    public MarketBoxService(MarketBoxRepository marketBoxRepository) {
        this.marketBoxRepository = marketBoxRepository;
    }

    /**
     * Save a marketBox.
     *
     * @param marketBox the entity to save
     * @return the persisted entity
     */
    public MarketBox save(MarketBox marketBox) {
        log.debug("Request to save MarketBox : {}", marketBox);
        return marketBoxRepository.save(marketBox);
    }

    /**
     *  Get all the marketBoxes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketBox> findAll() {
        log.debug("Request to get all MarketBoxes");
        return marketBoxRepository.findAll();
    }

    /**
     *  Get one marketBox by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MarketBox findOne(Long id) {
        log.debug("Request to get MarketBox : {}", id);
        return marketBoxRepository.findOne(id);
    }

    /**
     *  Delete the  marketBox by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MarketBox : {}", id);
        marketBoxRepository.delete(id);
    }
}
