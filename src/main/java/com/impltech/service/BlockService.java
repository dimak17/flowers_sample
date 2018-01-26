package com.impltech.service;

import com.impltech.domain.Block;
import com.impltech.repository.BlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author alex
 * Service Implementation for managing Block.
 */
@Service
@Transactional
public class BlockService {

    private final Logger log = LoggerFactory.getLogger(BlockService.class);

    private final BlockRepository blockRepository;

    public BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    /**
     * Save a block.
     *
     * @param block the entity to save
     * @return the persisted entity
     */
    public Block save(Block block) {
        log.debug("Request to save Block : {}", block);
        return blockRepository.save(block);
    }

    /**
     *  Get all the blocks.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Block> findAll(Pageable pageable) {
        log.debug("Request to get all Blocks");
        return blockRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Block> findAllByCompanyId(Long companyId) {
        log.debug("Request to get all Blocks by Company");
        return blockRepository.findAllByCompanyId(companyId);
    }

    /**
     *  Get one block by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Block findOne(Long id) {
        log.debug("Request to get Block : {}", id);
        return blockRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  block by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Block : {}", id);
        blockRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Long checkBlockIdentityName(String name, Long idCompany) {
        log.debug("Try to find Block with name :", name);
        return blockRepository.checkBlockIdentityName(name, idCompany);
    }
}
