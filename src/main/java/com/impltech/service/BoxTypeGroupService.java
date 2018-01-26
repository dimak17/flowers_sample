package com.impltech.service;

import com.impltech.domain.BoxTypeGroup;
import com.impltech.repository.BoxTypeGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author dima
 * Service Implementation for managing BoxTypeGroup.
 */
@Service
@Transactional
public class BoxTypeGroupService {

    private final Logger log = LoggerFactory.getLogger(BoxTypeGroupService.class);

    private final BoxTypeGroupRepository boxTypeGroupRepository;

    public BoxTypeGroupService(BoxTypeGroupRepository boxTypeGroupRepository) {
        this.boxTypeGroupRepository = boxTypeGroupRepository;
    }

    /**
     * Save a boxTypeGroup.
     *
     * @param boxTypeGroup the entity to save
     * @return the persisted entity
     */
    public BoxTypeGroup save(BoxTypeGroup boxTypeGroup) {
        log.debug("Request to save BoxTypeGroup : {}", boxTypeGroup);
        return boxTypeGroupRepository.save(boxTypeGroup);
    }

    /**
     *  Get all the boxTypeGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BoxTypeGroup> findAll(Pageable pageable) {
        log.debug("Request to get all BoxTypeGroups");
        return boxTypeGroupRepository.findAll(pageable);
    }

    /**
     *  Get one boxTypeGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BoxTypeGroup findOne(Long id) {
        log.debug("Request to get BoxTypeGroup : {}", id);
        return boxTypeGroupRepository.findOne(id);
    }

    /**
     *  Delete the  boxTypeGroup by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BoxTypeGroup : {}", id);
        boxTypeGroupRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<BoxTypeGroup> findAll(Long companyId) {
        log.debug("Request to get all BoxTypes by Company");
        // TODO get by company and market
        return boxTypeGroupRepository.findAll();
    }
}
