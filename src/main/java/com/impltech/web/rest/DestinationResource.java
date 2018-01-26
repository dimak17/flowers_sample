package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Destination;
import com.impltech.service.DestinationService;
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
 * REST controller for managing Destination.
 */
@RestController
@RequestMapping("/api")
public class DestinationResource {

    private final Logger log = LoggerFactory.getLogger(DestinationResource.class);

    private static final String ENTITY_NAME = "destination";

    private final DestinationService destinationService;

    public DestinationResource(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    /**
     * POST  /destinations : Create a new destination.
     *
     * @param destination the destination to create
     * @return the ResponseEntity with status 201 (Created) and with body the new destination, or with status 400 (Bad Request) if the destination has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/destinations")
    @Timed
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) throws URISyntaxException {
        log.debug("REST request to save Destination : {}", destination);
        if (destination.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new destination cannot already have an ID")).body(null);
        }
        Destination result = destinationService.save(destination);
        return ResponseEntity.created(new URI("/api/destinations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /destinations : Updates an existing destination.
     *
     * @param destination the destination to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated destination,
     * or with status 400 (Bad Request) if the destination is not valid,
     * or with status 500 (Internal Server Error) if the destination couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/destinations")
    @Timed
    public ResponseEntity<Destination> updateDestination(@RequestBody Destination destination) throws URISyntaxException {
        log.debug("REST request to update Destination : {}", destination);
        if (destination.getId() == null) {
            return createDestination(destination);
        }
        Destination result = destinationService.save(destination);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, destination.getId().toString()))
            .body(result);
    }

    /**
     * GET  /destinations : get all the destinations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of destinations in body
     */
    @GetMapping("/destinations")
    @Timed
    public List<Destination> getAllDestinations() {
        log.debug("REST request to get all Destinations");
        return destinationService.findAll();
    }

    /**
     * GET  /destinations/:id : get the "id" destination.
     *
     * @param id the id of the destination to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the destination, or with status 404 (Not Found)
     */
    @GetMapping("/destinations/{id}")
    @Timed
    public ResponseEntity<Destination> getDestination(@PathVariable Long id) {
        log.debug("REST request to get Destination : {}", id);
        Destination destination = destinationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(destination));
    }

    /**
     * DELETE  /destinations/:id : delete the "id" destination.
     *
     * @param id the id of the destination to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/destinations/{id}")
    @Timed
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        log.debug("REST request to delete Destination : {}", id);
        destinationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
