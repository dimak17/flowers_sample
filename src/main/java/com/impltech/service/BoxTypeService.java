package com.impltech.service;

import com.impltech.domain.BoxGroup;
import com.impltech.domain.BoxType;
import com.impltech.domain.BoxTypeGroup;
import com.impltech.repository.BoxTypeRepository;
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
 * Service Implementation for managing BoxType.
 */
@Service
@Transactional
public class BoxTypeService {

    private final Logger log = LoggerFactory.getLogger(BoxTypeService.class);

    private final BoxGroupService boxGroupService;

    private final BoxTypeRepository boxTypeRepository;

    public BoxTypeService(BoxTypeRepository boxTypeRepository, BoxGroupService boxGroupService) {
        this.boxTypeRepository = boxTypeRepository;
        this.boxGroupService = boxGroupService;
    }

    /**
     * Save a boxType.
     *
     * @param boxType the entity to save
     * @return the persisted entity
     */
    public BoxType save(BoxType boxType) {
        log.debug("Request to save BoxType : {}", boxType);
        boxType.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        return boxTypeRepository.save(boxType);
    }

    /**
     *  Get all the boxTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BoxType> findAll(Pageable pageable) {
        log.debug("Request to get all BoxTypes");
        return boxTypeRepository.findAll(pageable);
    }

    /**
     *  Get all the boxTypes.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<BoxType> findAll() {
        Long id = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("Request to get all BoxTypes by current Company: {}", id);
        return boxTypeRepository.findByCompanyId(id);
    }

    @Transactional(readOnly = true)
    public List<BoxType> findAllBoxTypesByCurrentCompany() {
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return boxTypeRepository.findByCompanyId(companyId);
    }

    /**
     *  Get one boxType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BoxType findOne(Long id, Long companyId) {
        log.debug("Request to get BoxType : {}", id);
        return boxTypeRepository.findOneByCompanyId(id , companyId);
    }

    /**
     *  Get one boxType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BoxType findOne(Long id) {
        log.debug("Request to get BoxType : {}", id);
        return boxTypeRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public BoxType findOneByCurrentCompany(Long id) {
        log.debug("Request to get BoxtType : {} by current company", id);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return boxTypeRepository.findOneByCompanyId(id, companyId);
    }

    /**
     *  Delete the  boxType by id.
     *
     *  @param id the id of the entity
     */
    public String delete(Long id) {
        log.debug("Request to delete BoxType : {}", id);

        String usages = "";
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        List<BoxGroup> boxGroups = boxGroupService.findAll(companyId);

        BoxType currentBoxType = findOne(id, companyId);

        for (BoxGroup boxGroup: boxGroups) {
            for (BoxTypeGroup boxTypeGroup: boxGroup.getBoxTypeGroups()) {
                if (currentBoxType.getId().equals(boxTypeGroup.getBoxType().getId())) {
                    usages = "BoxTypeGrouping";
                    return usages;
                }
            }
        }
        boxTypeRepository.deleteBoxTypeByCompanyId(companyId, id);
        return usages;
    }

    @Transactional(readOnly = true)
    public Page<BoxType> findAll(Long id, Pageable pageable) {
        return boxTypeRepository.findByCompanyId(id, pageable);
    }

    @Transactional(readOnly = true)
    public List<BoxType> findAll(Long companyId) {
        log.debug("Request to get all BoxTypes by Company");
        return boxTypeRepository.findByCompanyId(companyId);
    }
}
