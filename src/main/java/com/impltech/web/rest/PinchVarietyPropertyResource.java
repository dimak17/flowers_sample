package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.PinchVarietyProperty;
import com.impltech.service.PinchVarietyPropertyService;
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
 * @author platon
 * REST controller for managing PinchVarietyProperty.
 */
@RestController
@RequestMapping("/api")
public class PinchVarietyPropertyResource {

    private final Logger log = LoggerFactory.getLogger(PinchVarietyPropertyResource.class);

    private static final String ENTITY_NAME = "pinchVarietyProperty";

    private final PinchVarietyPropertyService pinchVarietyPropertyService;

    public PinchVarietyPropertyResource(PinchVarietyPropertyService pinchVarietyPropertyService) {
        this.pinchVarietyPropertyService = pinchVarietyPropertyService;
    }

    /**
     * POST  /pinch-variety-properties : Create a new pinchVarietyProperty.
     *
     * @param pinchVarietyProperty the pinchVarietyProperty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pinchVarietyProperty, or with status 400 (Bad Request) if the pinchVarietyProperty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pinch-variety-properties")
    @Timed
    public ResponseEntity<PinchVarietyProperty> createPinchVarietyProperty(@RequestBody PinchVarietyProperty pinchVarietyProperty) throws URISyntaxException {
        log.debug("REST request to save PinchVarietyProperty : {}", pinchVarietyProperty);
        if (pinchVarietyProperty.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pinchVarietyProperty cannot already have an ID")).body(null);
        }
        PinchVarietyProperty result = pinchVarietyPropertyService.save(pinchVarietyProperty);
        return ResponseEntity.created(new URI("/api/pinch-variety-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pinch-variety-properties : Updates an existing pinchVarietyProperty.
     *
     * @param pinchVarietyProperty the pinchVarietyProperty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pinchVarietyProperty,
     * or with status 400 (Bad Request) if the pinchVarietyProperty is not valid,
     * or with status 500 (Internal Server Error) if the pinchVarietyProperty couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pinch-variety-properties")
    @Timed
    public ResponseEntity<PinchVarietyProperty> updatePinchVarietyProperty(@RequestBody PinchVarietyProperty pinchVarietyProperty) throws URISyntaxException {
        log.debug("REST request to update PinchVarietyProperty : {}", pinchVarietyProperty);
        if (pinchVarietyProperty.getId() == null) {
            return createPinchVarietyProperty(pinchVarietyProperty);
        }
        PinchVarietyProperty result = pinchVarietyPropertyService.save(pinchVarietyProperty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pinchVarietyProperty.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pinch-variety-properties : get all the pinchVarietyProperties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pinchVarietyProperties in body
     */
    @GetMapping("/pinch-variety-properties")
    @Timed
    public List<PinchVarietyProperty> getAllPinchVarietyProperties() {
        log.debug("REST request to get all PinchVarietyProperties");
        return pinchVarietyPropertyService.findAll();
    }

    /**
     * GET  /pinch-variety-properties/:id : get the "id" pinchVarietyProperty.
     *
     * @param id the id of the pinchVarietyProperty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pinchVarietyProperty, or with status 404 (Not Found)
     */
    @GetMapping("/pinch-variety-properties/{id}")
    @Timed
    public ResponseEntity<PinchVarietyProperty> getPinchVarietyProperty(@PathVariable Long id) {
        log.debug("REST request to get PinchVarietyProperty : {}", id);
        PinchVarietyProperty pinchVarietyProperty = pinchVarietyPropertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pinchVarietyProperty));
    }

    /**
     * DELETE  /pinch-variety-properties/:id : delete the "id" pinchVarietyProperty.
     *
     * @param id the id of the pinchVarietyProperty to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pinch-variety-properties/{id}")
    @Timed
    public ResponseEntity<Void> deletePinchVarietyProperty(@PathVariable Long id) {
        log.debug("REST request to delete PinchVarietyProperty : {}", id);
        pinchVarietyPropertyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
