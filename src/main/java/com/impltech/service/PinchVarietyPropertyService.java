package com.impltech.service;

import com.impltech.domain.PinchVarietyProperty;
import com.impltech.repository.PinchVarietyPropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dima
 * Service Implementation for managing PinchVarietyProperty.
 */
@Service
@Transactional
public class PinchVarietyPropertyService {

    private final Logger log = LoggerFactory.getLogger(PinchVarietyPropertyService.class);

    private final PinchVarietyPropertyRepository pinchVarietyPropertyRepository;

    public PinchVarietyPropertyService(PinchVarietyPropertyRepository pinchVarietyPropertyRepository) {
        this.pinchVarietyPropertyRepository = pinchVarietyPropertyRepository;
    }

    /**
     * Save a pinchVarietyProperty.
     *
     * @param pinchVarietyProperty the entity to save
     * @return the persisted entity
     */
    public PinchVarietyProperty save(PinchVarietyProperty pinchVarietyProperty) {
        log.debug("Request to save PinchVarietyProperty : {}", pinchVarietyProperty);
        return pinchVarietyPropertyRepository.save(pinchVarietyProperty);
    }

    /**
     *  Get all the pinchVarietyProperties.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PinchVarietyProperty> findAll() {
        log.debug("Request to get all PinchVarietyProperties");
        return pinchVarietyPropertyRepository.findAll();
    }

    /**
     *  Get one pinchVarietyProperty by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PinchVarietyProperty findOne(Long id) {
        log.debug("Request to get PinchVarietyProperty : {}", id);
        return pinchVarietyPropertyRepository.findOne(id);
    }

    /**
     *  Delete the  pinchVarietyProperty by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PinchVarietyProperty : {}", id);
        pinchVarietyPropertyRepository.delete(id);
    }
}
