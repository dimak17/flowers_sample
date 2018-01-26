package com.impltech.service;

import com.impltech.domain.BoxGroup;
import com.impltech.domain.BoxType;
import com.impltech.domain.BoxTypeGroup;
import com.impltech.repository.BoxGroupRepository;
import com.impltech.repository.BoxTypeGroupRepository;
import com.impltech.security.SecurityUtils;
import com.impltech.service.dto.BoxGroupDTO;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author alex
 * Service Implementation for managing BoxGroup.
 */
@Service
@Transactional
public class BoxGroupService {

    private final Logger log = LoggerFactory.getLogger(BoxGroupService.class);

    private final BoxGroupRepository boxGroupRepository;
    private final BoxTypeGroupRepository boxTypeGroupRepository;

    public BoxGroupService(BoxGroupRepository boxGroupRepository, BoxTypeGroupRepository boxTypeGroupRepository) {
        this.boxGroupRepository = boxGroupRepository;
        this.boxTypeGroupRepository = boxTypeGroupRepository;
    }

    /**
     * Save a boxGroup.
     *
     * @param boxGroup the entity to save
     * @return the persisted entity
     */
    public BoxGroup save(BoxGroup boxGroup) {
        log.debug("Request to save BoxGroup : {}", boxGroup);
        return boxGroupRepository.save(boxGroup);
    }

    /**
     *  Get all the boxGroups.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BoxGroup> findAll(Pageable pageable) {
        log.debug("Request to get all BoxGroups");
        return boxGroupRepository.findAll(pageable);
    }

    /**
     *  Get one boxGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BoxGroup findOne(Long id) {
        log.debug("Request to get BoxGroup : {}", id);
        return boxGroupRepository.findOne(id);
    }

    /**
     *  Delete the  boxGroup by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BoxGroup : {}", id);
        BoxGroup boxGroup = boxGroupRepository.findOne(id);
        if(boxGroup != null) {
            Set<BoxTypeGroup> boxTypeGroups = boxGroup.getBoxTypeGroups();
            if (boxTypeGroups != null && boxTypeGroups.size() > 0) {
                boxTypeGroupRepository.delete(boxTypeGroups);
            }
            boxGroupRepository.delete(id);
        }
    }


    @Transactional(readOnly = true)
    public List<BoxGroup> findAllByCurrentCompany() {
        Long id = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("Request to get all BoxGroups by current Company: {}", id);
        // TODO get and market
        List<BoxGroup> groups = boxGroupRepository.findByCompanyId(id);
        groups
            .stream()
            .map(BoxGroup::getBoxTypeGroups)
            .forEach(g -> g.forEach(boxTypeGroup -> Hibernate.initialize(boxTypeGroup.getBoxType())));
        return groups;
    }

    @Transactional(readOnly = true)
    public List<BoxGroup> findAll(Long companyId) {
        log.debug("Request to get all BoxGroups by Company");
        // TODO get and market
        List<BoxGroup> groups = boxGroupRepository.findByCompanyId(companyId);
        groups
            .stream()
            .map(BoxGroup::getBoxTypeGroups)
            .forEach(g -> g.forEach(boxTypeGroup -> Hibernate.initialize(boxTypeGroup.getBoxType())));
        return groups;
    }

    public BoxGroup update(BoxGroupDTO boxGroupDTO) {
        BoxGroup boxGroup = boxGroupRepository.findOne(boxGroupDTO.getId());
        boxTypeGroupRepository.delete(boxGroup.getBoxTypeGroups());
        HashSet<BoxTypeGroup> boxTypeGroups = new HashSet<>();
        for (int i = 0; i < boxGroupDTO.getBoxTypes().size(); i++) {
            BoxType boxType = boxGroupDTO.getBoxTypes().get(i);
            BoxTypeGroup boxTypeGroup = new BoxTypeGroup();
            boxTypeGroup.setBoxType(boxType);
            boxTypeGroup.setOrder(i);
            boxTypeGroup.setBoxGroup(boxGroup);
            boxTypeGroup.setQuantity(boxGroupDTO.getQuantities().get(i));
            BoxTypeGroup save = boxTypeGroupRepository.save(boxTypeGroup);
            boxTypeGroups.add(save);
        }
        boxGroup.setBoxTypeGroups(boxTypeGroups);
        boxGroup.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        return boxGroupRepository.findOne(boxGroupDTO.getId());
    }

    public BoxGroup save(BoxGroupDTO boxGroupDTO) {
        BoxGroup boxGroup = new BoxGroup();
        boxGroup = boxGroupRepository.save(boxGroup);
        for (int i = 0; i < boxGroupDTO.getBoxTypes().size(); i++) {
            BoxType boxType = boxGroupDTO.getBoxTypes().get(i);
            BoxTypeGroup boxTypeGroup = new BoxTypeGroup();
            boxTypeGroup.setBoxType(boxType);
            boxTypeGroup.setOrder(i);
            boxTypeGroup.setBoxGroup(boxGroup);
            boxTypeGroup.setQuantity(boxGroupDTO.getQuantities().get(i));
            BoxTypeGroup save = boxTypeGroupRepository.save(boxTypeGroup);
        }
        boxGroup.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        return boxGroupRepository.findOne(boxGroup.getId());
    }

    public List<BoxGroup> findByMarket(Long id) {
        return boxGroupRepository.findBoxGroupByMarket(id);
    }


    public List<BoxGroup> findAllMarket(Long id) {
        return boxGroupRepository.findBoxGroupByMarket(id);
    }
}
