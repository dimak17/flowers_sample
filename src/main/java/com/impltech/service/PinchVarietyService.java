package com.impltech.service;

import com.impltech.domain.PinchVariety;
import com.impltech.repository.PinchVarietyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dima
 * Service Implementation for managing PinchVariety.
 */
@Service
@Transactional
public class PinchVarietyService {

    private final Logger log = LoggerFactory.getLogger(PinchVarietyService.class);

    private final PinchVarietyRepository pinchVarietyRepository;

    public PinchVarietyService(PinchVarietyRepository pinchVarietyRepository) {
        this.pinchVarietyRepository = pinchVarietyRepository;
    }

    /**
     * Save a pinchVariety.
     *
     * @param pinchVariety the entity to save
     * @return the persisted entity
     */
    public PinchVariety save(PinchVariety pinchVariety) {
        log.debug("Request to save PinchVariety : {}", pinchVariety);
        return pinchVarietyRepository.save(pinchVariety);
    }

    /**
     *  Get all the pinchVarieties.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PinchVariety> findAll() {
        log.debug("Request to get all PinchVarieties");
        return pinchVarietyRepository.findAll();
    }

    /**
     *  Get one pinchVariety by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PinchVariety findOne(Long id) {
        log.debug("Request to get PinchVariety : {}", id);
        return pinchVarietyRepository.findOne(id);
    }

    /**
     *  Delete the  pinchVariety by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PinchVariety : {}", id);
        pinchVarietyRepository.delete(id);
    }
}
