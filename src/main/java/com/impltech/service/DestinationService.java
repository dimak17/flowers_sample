package com.impltech.service;

import com.impltech.domain.Destination;
import com.impltech.repository.DestinationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dima
 * Service Implementation for managing Destination.
 */
@Service
@Transactional
public class DestinationService {

    private final Logger log = LoggerFactory.getLogger(DestinationService.class);

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    /**
     * Save a destination.
     *
     * @param destination the entity to save
     * @return the persisted entity
     */
    public Destination save(Destination destination) {
        log.debug("Request to save Destination : {}", destination);
        return destinationRepository.save(destination);
    }

    /**
     *  Get all the destinations.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Destination> findAll() {
        log.debug("Request to get all Destinations");
        return destinationRepository.findAll();
    }

    /**
     *  Get one destination by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Destination findOne(Long id) {
        log.debug("Request to get Destination : {}", id);
        return destinationRepository.findOne(id);
    }

    /**
     *  Delete the  destination by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Destination : {}", id);
        destinationRepository.delete(id);
    }
}
