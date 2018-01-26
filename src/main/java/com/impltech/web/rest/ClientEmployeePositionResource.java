package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.ClientEmployeePosition;
import com.impltech.service.ClientEmployeePositionService;
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
 * REST controller for managing ClientEmployeePosition.
 */
@RestController
@RequestMapping("/api")
public class ClientEmployeePositionResource {

    private final Logger log = LoggerFactory.getLogger(ClientEmployeePositionResource.class);

    private static final String ENTITY_NAME = "clientEmployeePosition";

    private final ClientEmployeePositionService clientEmployeePositionService;

    public ClientEmployeePositionResource(ClientEmployeePositionService clientEmployeePositionService) {
        this.clientEmployeePositionService = clientEmployeePositionService;
    }

    /**
     * POST  /client-employee-positions : Create a new clientEmployeePosition.
     *
     * @param clientEmployeePosition the clientEmployeePosition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientEmployeePosition, or with status 400 (Bad Request) if the clientEmployeePosition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-employee-positions")
    @Timed
    public ResponseEntity<ClientEmployeePosition> createClientEmployeePosition(@RequestBody ClientEmployeePosition clientEmployeePosition) throws URISyntaxException {
        log.debug("REST request to save ClientEmployeePosition : {}", clientEmployeePosition);
        if (clientEmployeePosition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientEmployeePosition cannot already have an ID")).body(null);
        }
        ClientEmployeePosition result = clientEmployeePositionService.save(clientEmployeePosition);
        return ResponseEntity.created(new URI("/api/client-employee-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-employee-positions : Updates an existing clientEmployeePosition.
     *
     * @param clientEmployeePosition the clientEmployeePosition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientEmployeePosition,
     * or with status 400 (Bad Request) if the clientEmployeePosition is not valid,
     * or with status 500 (Internal Server Error) if the clientEmployeePosition couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-employee-positions")
    @Timed
    public ResponseEntity<ClientEmployeePosition> updateClientEmployeePosition(@RequestBody ClientEmployeePosition clientEmployeePosition) throws URISyntaxException {
        log.debug("REST request to update ClientEmployeePosition : {}", clientEmployeePosition);
        if (clientEmployeePosition.getId() == null) {
            return createClientEmployeePosition(clientEmployeePosition);
        }
        ClientEmployeePosition result = clientEmployeePositionService.save(clientEmployeePosition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientEmployeePosition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-employee-positions : get all the clientEmployeePositions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clientEmployeePositions in body
     */
    @GetMapping("/client-employee-positions")
    @Timed
    public List<ClientEmployeePosition> getAllClientEmployeePositions() {
        log.debug("REST request to get all ClientEmployeePositions");
        return clientEmployeePositionService.findAll();
    }

    /**
     * GET  /client-employee-positions/:id : get the "id" clientEmployeePosition.
     *
     * @param id the id of the clientEmployeePosition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientEmployeePosition, or with status 404 (Not Found)
     */
    @GetMapping("/client-employee-positions/{id}")
    @Timed
    public ResponseEntity<ClientEmployeePosition> getClientEmployeePosition(@PathVariable Long id) {
        log.debug("REST request to get ClientEmployeePosition : {}", id);
        ClientEmployeePosition clientEmployeePosition = clientEmployeePositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientEmployeePosition));
    }

    /**
     * DELETE  /client-employee-positions/:id : delete the "id" clientEmployeePosition.
     *
     * @param id the id of the clientEmployeePosition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-employee-positions/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientEmployeePosition(@PathVariable Long id) {
        log.debug("REST request to delete ClientEmployeePosition : {}", id);
        clientEmployeePositionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
