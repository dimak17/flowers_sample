package com.impltech.service;

import com.impltech.domain.MarketClient;
import com.impltech.repository.MarketClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author alex
 * Service Implementation for managing MarketClient.
 */
@Service
@Transactional
public class MarketClientService {

    private final Logger log = LoggerFactory.getLogger(MarketClientService.class);

    private final MarketClientRepository marketClientRepository;

    public MarketClientService(MarketClientRepository marketClientRepository) {
        this.marketClientRepository = marketClientRepository;
    }

    /**
     * Save a marketClient.
     *
     * @param marketClient the entity to save
     * @return the persisted entity
     */
    public MarketClient save(MarketClient marketClient) {
        log.debug("Request to save MarketClient : {}", marketClient);
        return marketClientRepository.save(marketClient);
    }

    /**
     *  Get all the marketClients.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketClient> findAll() {
        log.debug("Request to get all MarketClients");
        return marketClientRepository.findAll();
    }

    /**
     *  Get one marketClient by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MarketClient findOne(Long id) {
        log.debug("Request to get MarketClient : {}", id);
        return marketClientRepository.findOne(id);
    }

    /**
     *  Delete the  marketClient by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MarketClient : {}", id);
        marketClientRepository.delete(id);
    }
}
