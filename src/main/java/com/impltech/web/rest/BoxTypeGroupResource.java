package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.BoxTypeGroup;
import com.impltech.service.BoxTypeGroupService;
import com.impltech.web.rest.util.HeaderUtil;
import com.impltech.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * @author platon
 * REST controller for managing BoxTypeGroup.
 */
@RestController
@RequestMapping("/api")
public class BoxTypeGroupResource {

    private final Logger log = LoggerFactory.getLogger(BoxTypeGroupResource.class);

    private static final String ENTITY_NAME = "boxTypeGroup";

    private final BoxTypeGroupService boxTypeGroupService;

    public BoxTypeGroupResource(BoxTypeGroupService boxTypeGroupService) {
        this.boxTypeGroupService = boxTypeGroupService;
    }

    /**
     * POST  /box-type-groups : Create a new boxTypeGroup.
     *
     * @param boxTypeGroup the boxTypeGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boxTypeGroup, or with status 400 (Bad Request) if the boxTypeGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/box-type-groups")
    @Timed
    public ResponseEntity<BoxTypeGroup> createBoxTypeGroup(@Valid @RequestBody BoxTypeGroup boxTypeGroup) throws URISyntaxException {
        log.debug("REST request to save BoxTypeGroup : {}", boxTypeGroup);
        if (boxTypeGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new boxTypeGroup cannot already have an ID")).body(null);
        }
        BoxTypeGroup result = boxTypeGroupService.save(boxTypeGroup);
        return ResponseEntity.created(new URI("/api/box-type-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /box-type-groups : Updates an existing boxTypeGroup.
     *
     * @param boxTypeGroup the boxTypeGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated boxTypeGroup,
     * or with status 400 (Bad Request) if the boxTypeGroup is not valid,
     * or with status 500 (Internal Server Error) if the boxTypeGroup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/box-type-groups")
    @Timed
    public ResponseEntity<BoxTypeGroup> updateBoxTypeGroup(@Valid @RequestBody BoxTypeGroup boxTypeGroup) throws URISyntaxException {
        log.debug("REST request to update BoxTypeGroup : {}", boxTypeGroup);
        if (boxTypeGroup.getId() == null) {
            return createBoxTypeGroup(boxTypeGroup);
        }
        BoxTypeGroup result = boxTypeGroupService.save(boxTypeGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, boxTypeGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /box-type-groups : get all the boxTypeGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of boxTypeGroups in body
     */
    @GetMapping("/box-type-groups")
    @Timed
    public ResponseEntity<List<BoxTypeGroup>> getAllBoxTypeGroups(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BoxTypeGroups");
        Page<BoxTypeGroup> page = boxTypeGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/box-type-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /box-type-groups/:id : get the "id" boxTypeGroup.
     *
     * @param id the id of the boxTypeGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boxTypeGroup, or with status 404 (Not Found)
     */
    @GetMapping("/box-type-groups/{id}")
    @Timed
    public ResponseEntity<BoxTypeGroup> getBoxTypeGroup(@PathVariable Long id) {
        log.debug("REST request to get BoxTypeGroup : {}", id);
        BoxTypeGroup boxTypeGroup = boxTypeGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(boxTypeGroup));
    }

    /**
     * DELETE  /box-type-groups/:id : delete the "id" boxTypeGroup.
     *
     * @param id the id of the boxTypeGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/box-type-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteBoxTypeGroup(@PathVariable Long id) {
        log.debug("REST request to delete BoxTypeGroup : {}", id);
        boxTypeGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }



    /**
     * GET  /box-types : get all the boxTypes.
     *
     * @param companyId the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of boxTypes in body
     */
    @GetMapping("/box-type-groups/company/{companyId}")
    @Timed
    public ResponseEntity<List<BoxTypeGroup>> getAllBoxTypes(@PathVariable Long companyId) {
        log.debug("REST request to get a page of BoxTypes");
        List<BoxTypeGroup> boxTypes = boxTypeGroupService.findAll(companyId);
        return new ResponseEntity<>(boxTypes, HttpStatus.OK);
    }
}
