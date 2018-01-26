package com.impltech.service;

import com.impltech.domain.AirLines;
import com.impltech.repository.AirLinesRepositorySH;
import com.impltech.repository.CompanyUserRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dima
 * Service Implementation for managing AirLines.
 */
@Service
@Transactional
public class AirLinesServiceSH {

    private final Logger log = LoggerFactory.getLogger(AirLinesServiceSH.class);

    private final AirLinesRepositorySH airLinesRepository;

    private final CompanyUserRepository companyUserRepository;

    public AirLinesServiceSH(AirLinesRepositorySH airLinesRepository, CompanyUserRepository companyUserRepository) {
        this.airLinesRepository = airLinesRepository;
        this.companyUserRepository = companyUserRepository;
    }

    /**
     * Save a airLines.
     *
     * @param airLines the entity to save
     * @return the persisted entity
     */
    public AirLines save(AirLines airLines) {
        log.debug("Request to save AirLines : {}", airLines);
        if(airLines.getId() == null) {
            airLines.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        }
        return airLinesRepository.save(airLines);
    }

    /**
     *  Get all the airLines.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AirLines> findAll(Pageable pageable) {
        log.debug("Request to get all AirLines");
        return airLinesRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public List<AirLines> findAll() {
        log.debug("Request to get all AirLines");
        return airLinesRepository.findAll();
    }

    /**
     *  Get one airLines by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AirLines findOne(Long id) {
        log.debug("Request to get AirLines : {}", id);
        return airLinesRepository.findOne(id);
    }

    /**
     *  Delete the  airLines by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AirLines : {}", id);
        airLinesRepository.delete(id);
    }

    public List<AirLines> findAirLinesByCompanyId(){
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("Request to get AirLinesByCompanyId where id is " + companyId);
        return airLinesRepository.findAirLinesByCompanyId(companyId);
    }

}
