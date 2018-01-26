package com.impltech.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.impltech.domain.Block;
import com.impltech.security.SecurityUtils;
import com.impltech.web.rest.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.impltech.domain.Variety;
import com.impltech.repository.VarietyRepository;

/**
 * @author alex
 * Service Implementation for managing Variety.
 */
@Service
@Transactional
public class VarietyService {

    private final Logger log = LoggerFactory.getLogger(VarietyService.class);

    private final VarietyRepository varietyRepository;
    private final BlockService blockService;

    public VarietyService(VarietyRepository varietyRepository, BlockService blockService) {
        this.varietyRepository = varietyRepository;
        this.blockService = blockService;
    }

    /**
     * Save a variety.
     *
     * @param variety the entity to save
     * @return the persisted entity
     */
    public Variety save(Variety variety) {
        log.debug("Request to save Variety : {}", variety);
        variety.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        return varietyRepository.save(variety);
    }

    /**
     *  Get all the varieties.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Variety> findAll(Pageable pageable) {
        log.debug("Request to get all Varieties");
        return varietyRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Variety> findAllByCompany() {
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("Request to get all Varieties by Company id" + companyId);
        return varietyRepository.findVarietiesByIdCompany(companyId);
    }


    /**
     *  Get all the varieties.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Variety> findAll() {
        log.debug("Request to get all Varieties");
        return varietyRepository.findAll();
    }

    /**
     *  Get one variety by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Variety findOne(Long id) {
        log.debug("Request to get Variety : {}", id);
        return varietyRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public Variety findOneByCurrentCompanyId(Long id) {
        log.debug("Request to get Variety : {}", id);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return varietyRepository.findOneByCompanyId(companyId, id);
    }

    /**
     *  Delete the  variety by id.
     *
     *  @param id the id of the entity
     */
    public List<String> delete(Long id) {
        log.debug("Request to delete Variety : {}", id);

        List<File> list = ImageUtil.listFilteredFiles(id, ImageUtil.getVarietyImageDir());

        List<String> blockNames = new ArrayList<>();
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        List<Block> blocks = blockService.findAllByCompanyId(companyId);
        Variety currentVariety = findOneByCurrentCompanyId(id);

        for (Block block : blocks) {
            for ( Variety variety: block.getVarieties()) {
                if (currentVariety.getId().equals(variety.getId())) {
                    blockNames.add(block.getName());
                }
            }
        }
        if (blockNames.size() > 0) {
            blockNames.add("Blocks");
            return blockNames;
        }
        ImageUtil.deleteImage(id, list);
        varietyRepository.deleteVarietyByCompanyId(companyId, id);
        blockNames.add("");
        return blockNames;
    }

    public List<Variety> findVarietiesByIdCompany(Long id) {
    	log.debug("Request to get VarietiesByIdCompany where id is " + id);
    	return varietyRepository.findVarietiesByIdCompany(id);
    }

    public List<Variety> findVarietiesByIdCompanyAndTypeOfFlower(Long id) {
        log.debug("Request to get VarietiesByIdCompanyAndTypeOfFlower where id is " + id);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return varietyRepository.findVarietiesByIdCompanyAndTypeOfFlower(companyId, id);
    }
}
