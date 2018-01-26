package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.MixType;
import com.impltech.security.SecurityUtils;
import com.impltech.service.MixTypeServiceSH;
import com.impltech.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * @author dima
 * REST controller for managing MixType.
 */
@RestController
@RequestMapping("/api")
public class MixTypeResourceSH {

    private final Logger log = LoggerFactory.getLogger(MixTypeResourceSH.class);

    private static final String ENTITY_NAME = "mixType";

    private final MixTypeServiceSH mixTypeService;

    public MixTypeResourceSH(MixTypeServiceSH mixTypeService) {
        this.mixTypeService = mixTypeService;
    }

    /**
     * POST  /mix-types : Create a new mixType.
     *
     * @param mixType the mixType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mixType, or with status 400 (Bad Request) if the mixType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mix-types")
    @Timed
    public ResponseEntity<MixType> createMixType(@RequestBody MixType mixType) throws URISyntaxException {
        log.debug("REST request to save MixType : {}", mixType);

        if (mixType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mixType cannot already have an ID")).body(null);
        }
        Long id = this.mixTypeService.checkMixTypeName(mixType.getName().trim(), SecurityUtils.getCurrentCompanyUser().getCompany().getId());

        if (id != null) {
            return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }
        if (mixType.getName().length() > 24) {
            return ResponseEntity.status(400).build();
        }
        mixType.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        MixType result = mixTypeService.save(mixType);
        return ResponseEntity.created(new URI("/api/mix-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);

    }

    /**
     * PUT  /mix-types : Updates an existing mixType.
     *
     * @param mixType the mixType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mixType,
     * or with status 400 (Bad Request) if the mixType is not valid,
     * or with status 500 (Internal Server Error) if the mixType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mix-types")
    @Timed
    public ResponseEntity<MixType> updateMixType(@RequestBody MixType mixType) throws
        URISyntaxException {
        log.debug("REST request to update MixType : {}", mixType);

        if (mixType.getId() == null) {
            return createMixType(mixType);
        }

        Long id = this.mixTypeService.checkMixTypeName(mixType.getName().trim(), SecurityUtils.getCurrentCompanyUser().getCompany().getId());

        if (id != null && id != mixType.getId()) {
            return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (mixType.getName().length() > 24) {
            return ResponseEntity.status(400).build();
        }
        MixType result = mixTypeService.save(mixType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mixType.getId().toString()))
            .body(result);
    }


    @GetMapping("/mix-types/by-company")
    @Timed
    public ResponseEntity<List<MixType>> getAllMixTypes() {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mixTypeService.findAll(SecurityUtils.getCurrentCompanyUser().getCompany().getId())));
    }

    /**
     * GET  /mix-types/:id : get the "id" mixType.
     *
     * @param id the id of the mixType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mixType, or with status 404 (Not Found)
     */
    @GetMapping("/mix-types/{id}")
    @Timed
    public ResponseEntity<MixType> getMixType(@PathVariable Long id) {
        log.debug("REST request to get MixType : {}", id);
        MixType mixType = mixTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mixType));
    }

    /**
     * DELETE  /mix-types/:id : delete the "id" mixType.
     *
     * @param id the id of the mixType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mix-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteMixType(@PathVariable Long id) {
        log.debug("REST request to delete MixType : {}", id);

        mixTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
