package com.impltech.service;

import com.impltech.domain.MixType;
import com.impltech.repository.MixTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author alex
 * Service Implementation for managing MixType.
 */
@Service
@Transactional
public class MixTypeService {

    private final Logger log = LoggerFactory.getLogger(MixTypeService.class);

    private final MixTypeRepository mixTypeRepository;

    public MixTypeService(MixTypeRepository mixTypeRepository) {
        this.mixTypeRepository = mixTypeRepository;
    }

    /**
     * Save a mixType.
     *
     * @param mixType the entity to save
     * @return the persisted entity
     */
    public MixType save(MixType mixType) {
        log.debug("Request to save MixType : {}", mixType);
        return mixTypeRepository.save(mixType);
    }

    /**
     *  Get all the mixTypes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MixType> findAll() {
        log.debug("Request to get all MixTypes");
        return mixTypeRepository.findAll();
    }

    /**
     *  Get one mixType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MixType findOne(Long id) {
        log.debug("Request to get MixType : {}", id);
        return mixTypeRepository.findOne(id);
    }

    /**
     *  Delete the  mixType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MixType : {}", id);
        mixTypeRepository.delete(id);
    }
}
