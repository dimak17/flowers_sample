package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Block;
import com.impltech.security.SecurityUtils;
import com.impltech.service.BlockService;
import com.impltech.validator.util.BlockValidator;
import com.impltech.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * @author alex
 * REST controller for managing Block.
 */
@RestController
@RequestMapping("/api")
public class BlockResource {

    private final Logger log = LoggerFactory.getLogger(BlockResource.class);

    private static final String ENTITY_NAME = "block";

    private final BlockService blockService;

    public BlockResource(BlockService blockService) {
        this.blockService = blockService;
    }

    /**
     * POST  /blocks : Create a new block.
     *
     * @param block the block to create
     * @return the ResponseEntity with status 201 (Created) and with body the new block, or with status 400 (Bad Request) if the block has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blocks")
    @Timed
    public ResponseEntity<Block> createBlock(@RequestBody Block block) throws URISyntaxException {
        log.debug("REST request to save Block : {}", block);
        if(BlockValidator.checkName(block) && BlockValidator.checkBeds(block)) {
            Long isIdentityName = blockService.checkBlockIdentityName(block.getName().trim(), block.getCompany().getId());
            if(isIdentityName != null) {
                return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "that name is already exists")).build();
            }
        	if (block.getId() != null) {
        		return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new block cannot already have an ID")).body(null);
        	}
        	Block result = blockService.save(block);
        	return ResponseEntity.created(new URI("/api/blocks/" + result.getId()))
        			.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
        			.body(result);
        }
        return ResponseEntity.badRequest()
        		.headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "400", "request is not valid")).build();
    }

    /**
     * PUT  /blocks : Updates an existing block.
     *
     * @param block the block to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated block,
     * or with status 400 (Bad Request) if the block is not valid,
     * or with status 500 (Internal Server Error) if the block couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blocks")
    @Timed
    public ResponseEntity<Block> updateBlock(@RequestBody Block block) throws URISyntaxException {
        log.debug("REST request to update Block : {}", block);
        if(BlockValidator.checkName(block) && BlockValidator.checkBeds(block)) {
            Long isIdentityName = blockService.checkBlockIdentityName(block.getName().trim(), block.getCompany().getId());
            if(isIdentityName != null && isIdentityName != block.getId()){
                return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "that name is already exists")).build();
            }
            if (block.getId() == null) {
        		return createBlock(block);
            }
            Block result = blockService.save(block);
        	return ResponseEntity.ok()
        		.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, block.getId().toString()))
        		.body(result);
        }
        return ResponseEntity.badRequest()
        		.headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "400", "request is not valid")).build();
    }
    /**
     * GET  /blocks : get all the blocks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of blocks in body
     */

    @GetMapping("/blocks")
    @Timed
    public ResponseEntity<List<Block>> getAllBoxTypeGroups() {
        log.debug("REST request to get a page of Block");
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        if(idCompany != null) {
            List<Block> block = blockService.findAllByCompanyId(idCompany);
            return new ResponseEntity<>(block, HttpStatus.OK);
        }
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "400", "request is not valid")).build();
    }



    /**
     * GET  /blocks/:id : get the "id" block.
     *
     * @param id the id of the block to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the block, or with status 404 (Not Found)
     */
    @GetMapping("/blocks/{id}")
    @Timed
    public ResponseEntity<Block> getBlock(@PathVariable Long id) {
        log.debug("REST request to get Block : {}", id);
        Block block = blockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(block));
    }

    /**
     * DELETE  /blocks/:id : delete the "id" block.
     *
     * @param id the id of the block to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blocks/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlock(@PathVariable Long id) {
        log.debug("REST request to delete Block : {}", id);
        blockService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
