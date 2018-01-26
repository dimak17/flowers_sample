package com.impltech.service;

import com.impltech.domain.Country;
import com.impltech.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @autor dima
 * Service Implementation for managing Country.
 */
@Service
@Transactional
public class CountryService {

    private final Logger log = LoggerFactory.getLogger(CountryService.class);

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Save a country.
     *
     * @param country the entity to save
     * @return the persisted entity
     */
    public Country save(Country country) {
        log.debug("Request to save Country : {}", country);
        return countryRepository.save(country);
    }

    /**
     *  Get all the countries.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Country> findAll() {
        log.debug("Request to get all Countries");
        return countryRepository.findAll();
    }

    /**
     *  Get one country by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Country findOne(Long id) {
        log.debug("Request to get Country : {}", id);
        return countryRepository.findOne(id);
    }

    /**
     *  Delete the  country by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Country : {}", id);
        countryRepository.delete(id);
    }
}
