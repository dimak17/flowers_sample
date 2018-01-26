package com.impltech.service;

import com.impltech.domain.Pinch;
import com.impltech.repository.PinchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dima
 * Service Implementation for managing Pinch.
 */
@Service
@Transactional
public class PinchService {

    private final Logger log = LoggerFactory.getLogger(PinchService.class);

    private final PinchRepository pinchRepository;

    public PinchService(PinchRepository pinchRepository) {
        this.pinchRepository = pinchRepository;
    }

    /**
     * Save a pinch.
     *
     * @param pinch the entity to save
     * @return the persisted entity
     */
    public Pinch save(Pinch pinch) {
        log.debug("Request to save Pinch : {}", pinch);
        return pinchRepository.save(pinch);
    }

    /**
     *  Get all the pinches.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Pinch> findAll() {
        log.debug("Request to get all Pinches");
        return pinchRepository.findAll();
    }

    /**
     *  Get one pinch by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Pinch findOne(Long id) {
        log.debug("Request to get Pinch : {}", id);
        return pinchRepository.findOne(id);
    }

    /**
     *  Delete the  pinch by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pinch : {}", id);
        pinchRepository.delete(id);
    }
}
