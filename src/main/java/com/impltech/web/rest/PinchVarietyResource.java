package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.PinchVariety;
import com.impltech.service.PinchVarietyService;
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
 * REST controller for managing PinchVariety.
 */
@RestController
@RequestMapping("/api")
public class PinchVarietyResource {

    private final Logger log = LoggerFactory.getLogger(PinchVarietyResource.class);

    private static final String ENTITY_NAME = "pinchVariety";

    private final PinchVarietyService pinchVarietyService;

    public PinchVarietyResource(PinchVarietyService pinchVarietyService) {
        this.pinchVarietyService = pinchVarietyService;
    }

    /**
     * POST  /pinch-varieties : Create a new pinchVariety.
     *
     * @param pinchVariety the pinchVariety to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pinchVariety, or with status 400 (Bad Request) if the pinchVariety has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pinch-varieties")
    @Timed
    public ResponseEntity<PinchVariety> createPinchVariety(@RequestBody PinchVariety pinchVariety) throws URISyntaxException {
        log.debug("REST request to save PinchVariety : {}", pinchVariety);
        if (pinchVariety.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pinchVariety cannot already have an ID")).body(null);
        }
        PinchVariety result = pinchVarietyService.save(pinchVariety);
        return ResponseEntity.created(new URI("/api/pinch-varieties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pinch-varieties : Updates an existing pinchVariety.
     *
     * @param pinchVariety the pinchVariety to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pinchVariety,
     * or with status 400 (Bad Request) if the pinchVariety is not valid,
     * or with status 500 (Internal Server Error) if the pinchVariety couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pinch-varieties")
    @Timed
    public ResponseEntity<PinchVariety> updatePinchVariety(@RequestBody PinchVariety pinchVariety) throws URISyntaxException {
        log.debug("REST request to update PinchVariety : {}", pinchVariety);
        if (pinchVariety.getId() == null) {
            return createPinchVariety(pinchVariety);
        }
        PinchVariety result = pinchVarietyService.save(pinchVariety);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pinchVariety.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pinch-varieties : get all the pinchVarieties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pinchVarieties in body
     */
    @GetMapping("/pinch-varieties")
    @Timed
    public List<PinchVariety> getAllPinchVarieties() {
        log.debug("REST request to get all PinchVarieties");
        return pinchVarietyService.findAll();
    }

    /**
     * GET  /pinch-varieties/:id : get the "id" pinchVariety.
     *
     * @param id the id of the pinchVariety to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pinchVariety, or with status 404 (Not Found)
     */
    @GetMapping("/pinch-varieties/{id}")
    @Timed
    public ResponseEntity<PinchVariety> getPinchVariety(@PathVariable Long id) {
        log.debug("REST request to get PinchVariety : {}", id);
        PinchVariety pinchVariety = pinchVarietyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pinchVariety));
    }

    /**
     * DELETE  /pinch-varieties/:id : delete the "id" pinchVariety.
     *
     * @param id the id of the pinchVariety to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pinch-varieties/{id}")
    @Timed
    public ResponseEntity<Void> deletePinchVariety(@PathVariable Long id) {
        log.debug("REST request to delete PinchVariety : {}", id);
        pinchVarietyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
