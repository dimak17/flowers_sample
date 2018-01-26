package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.BoxType;
import com.impltech.service.BoxTypeService;
import com.impltech.validator.util.BoxTypeValidator;
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
 * REST controller for managing BoxType.
 */
@RestController
@RequestMapping("/api")
public class BoxTypeResource {

    private final Logger log = LoggerFactory.getLogger(BoxTypeResource.class);

    private static final String ENTITY_NAME = "boxType";

    private final BoxTypeService boxTypeService;

    public BoxTypeResource(BoxTypeService boxTypeService) {
        this.boxTypeService = boxTypeService;
    }

    /**
     * POST  /box-types : Create a new boxType.
     *
     * @param boxType the boxType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boxType, or with status 400 (Bad Request) if the boxType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/box-types")
    @Timed
    public ResponseEntity<BoxType> createBoxType(@RequestBody BoxType boxType) throws URISyntaxException {
        log.debug("REST request to save BoxType : {}", boxType);

        for (BoxType boxTypeFromList : this.boxTypeService.findAllBoxTypesByCurrentCompany()) {
            if(boxTypeFromList.getShortName().equalsIgnoreCase((boxType.getShortName().trim()))){
                return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
            }
        }
        if (boxType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new boxType cannot already have an ID")).body(null);
        }
        if (BoxTypeValidator.checkLengthValidation(boxType)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }
        BoxType result = boxTypeService.save(boxType);
        return ResponseEntity.created(new URI("/api/box-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /box-types : Updates an existing boxType.
     *
     * @param boxType the boxType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated boxType,
     * or with status 400 (Bad Request) if the boxType is not valid,
     * or with status 500 (Internal Server Error) if the boxType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/box-types")
    @Timed
    public ResponseEntity<BoxType> updateBoxType(@RequestBody BoxType boxType) throws URISyntaxException {
        log.debug("REST request to update BoxType : {}", boxType);
        if (boxType.getId() == null) {
            return createBoxType(boxType);
        }

        for (BoxType boxTypeFromList : this.boxTypeService.findAllBoxTypesByCurrentCompany()) {
            if(boxTypeFromList.getShortName().equalsIgnoreCase((boxType.getShortName().trim())) && !boxTypeFromList.getId().equals(boxType.getId())){
                return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
            }
        }

        if (BoxTypeValidator.checkLengthValidation(boxType)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }
        BoxType result = boxTypeService.save(boxType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, boxType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /box-types : get all the boxTypes by Company id.
     * @return the ResponseEntity with status 200 (OK) and the list of boxTypes in body
     */
    @GetMapping("/box-types/company")
    @Timed
    public ResponseEntity<List<BoxType>> getAllBoxTypes() {
        log.debug("REST request to get a page of BoxTypes");
        List<BoxType> boxTypes = boxTypeService.findAllBoxTypesByCurrentCompany();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(boxTypes));
    }

    /**
     * GET  /box-types/:id : get the "id" boxType.
     *
     * @param id the id of the boxType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boxType, or with status 404 (Not Found)
     */
    @GetMapping("/box-types/{id}")
    @Timed
    public ResponseEntity<BoxType> getBoxType(@PathVariable Long id) {
        log.debug("REST request to get BoxType : {}", id);
        BoxType boxType = boxTypeService.findOneByCurrentCompany(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(boxType));
    }

    /**
     * DELETE  /box-types/:id : delete the "id" boxType.
     *
     * @param id the id of the boxType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/box-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteBoxType(@PathVariable Long id) {
        log.debug("REST request to delete BoxType : {}", id);

        if (id == null) {
            return ResponseEntity.notFound().build();
        }

            String boxTypeUsages = boxTypeService.delete(id);
            if (boxTypeUsages.equals("BoxTypeGrouping")) {
                return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "BoxTypeGrouping", "BoxTypeGrouping")).build();
            }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
