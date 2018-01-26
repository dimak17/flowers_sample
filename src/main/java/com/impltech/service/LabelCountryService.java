package com.impltech.service;

import com.impltech.domain.LabelCountry;
import com.impltech.repository.LabelCountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author alex
 * Service Implementation for managing LabelCountry.
 */
@Service
@Transactional
public class LabelCountryService {

    private final Logger log = LoggerFactory.getLogger(LabelCountryService.class);

    private final LabelCountryRepository labelCountryRepository;

    public LabelCountryService(LabelCountryRepository labelCountryRepository) {
        this.labelCountryRepository = labelCountryRepository;
    }

    /**
     * Save a labelCountry.
     *
     * @param labelCountry the entity to save
     * @return the persisted entity
     */
    public LabelCountry save(LabelCountry labelCountry) {
        log.debug("Request to save LabelCountry : {}", labelCountry);
        return labelCountryRepository.save(labelCountry);
    }

    /**
     *  Get all the labelCountries.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<LabelCountry> findAll() {
        log.debug("Request to get all LabelCountries");
        return labelCountryRepository.findAll();
    }

    /**
     *  Get one labelCountry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public LabelCountry findOne(Long id) {
        log.debug("Request to get LabelCountry : {}", id);
        return labelCountryRepository.findOne(id);
    }

    /**
     *  Delete the  labelCountry by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LabelCountry : {}", id);
        labelCountryRepository.delete(id);
    }
}
