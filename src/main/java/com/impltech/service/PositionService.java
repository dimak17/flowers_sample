package com.impltech.service;

import com.impltech.domain.Position;
import com.impltech.repository.PositionRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author platon
 * Service Implementation for managing Position.
 */
@Service
@Transactional
public class PositionService {

    private final Logger log = LoggerFactory.getLogger(PositionService.class);

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * Save a position.
     *
     * @param position the entity to save
     * @return the persisted entity
     */
    public Position save(Position position) {
        log.debug("Request to save Position : {}", position);
        return positionRepository.save(position);
    }

    /**
     *  Get all the positions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Position> findAll(Pageable pageable) {
        log.debug("Request to get all Positions");
        return positionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Position> findAllByCurrentCompany() {
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.info("Request to get all Positions by Current Company Id: " + companyId);
        return positionRepository.findAllByCurrentCompany(companyId);
    }
    /**
     *  Get one position by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Position findOne(Long id) {
        log.debug("Request to get Position : {}", id);
        return positionRepository.findOne(id);
    }

    /**
     *  Delete the  position by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Position : {}", id);
        positionRepository.delete(id);
    }
}
