package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Pinch;
import com.impltech.service.PinchService;
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
 * REST controller for managing Pinch.
 */
@RestController
@RequestMapping("/api")
public class PinchResource {

    private final Logger log = LoggerFactory.getLogger(PinchResource.class);

    private static final String ENTITY_NAME = "pinch";

    private final PinchService pinchService;

    public PinchResource(PinchService pinchService) {
        this.pinchService = pinchService;
    }

    /**
     * POST  /pinches : Create a new pinch.
     *
     * @param pinch the pinch to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pinch, or with status 400 (Bad Request) if the pinch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pinches")
    @Timed
    public ResponseEntity<Pinch> createPinch(@RequestBody Pinch pinch) throws URISyntaxException {
        log.debug("REST request to save Pinch : {}", pinch);
        if (pinch.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pinch cannot already have an ID")).body(null);
        }
        Pinch result = pinchService.save(pinch);
        return ResponseEntity.created(new URI("/api/pinches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pinches : Updates an existing pinch.
     *
     * @param pinch the pinch to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pinch,
     * or with status 400 (Bad Request) if the pinch is not valid,
     * or with status 500 (Internal Server Error) if the pinch couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pinches")
    @Timed
    public ResponseEntity<Pinch> updatePinch(@RequestBody Pinch pinch) throws URISyntaxException {
        log.debug("REST request to update Pinch : {}", pinch);
        if (pinch.getId() == null) {
            return createPinch(pinch);
        }
        Pinch result = pinchService.save(pinch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pinch.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pinches : get all the pinches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pinches in body
     */
    @GetMapping("/pinches")
    @Timed
    public List<Pinch> getAllPinches() {
        log.debug("REST request to get all Pinches");
        return pinchService.findAll();
    }

    /**
     * GET  /pinches/:id : get the "id" pinch.
     *
     * @param id the id of the pinch to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pinch, or with status 404 (Not Found)
     */
    @GetMapping("/pinches/{id}")
    @Timed
    public ResponseEntity<Pinch> getPinch(@PathVariable Long id) {
        log.debug("REST request to get Pinch : {}", id);
        Pinch pinch = pinchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pinch));
    }

    /**
     * DELETE  /pinches/:id : delete the "id" pinch.
     *
     * @param id the id of the pinch to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pinches/{id}")
    @Timed
    public ResponseEntity<Void> deletePinch(@PathVariable Long id) {
        log.debug("REST request to delete Pinch : {}", id);
        pinchService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
