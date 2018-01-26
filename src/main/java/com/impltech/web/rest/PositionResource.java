package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Position;
import com.impltech.service.PositionService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * @author platon
 * REST controller for managing Position.
 */
@RestController
@RequestMapping("/api")
public class PositionResource {

    private final Logger log = LoggerFactory.getLogger(PositionResource.class);

    private static final String ENTITY_NAME = "position";

    private final PositionService positionService;

    public PositionResource(PositionService positionService) {
        this.positionService = positionService;
    }

    /**
     * POST  /positions : Create a new position.
     *
     * @param position the position to create
     * @return the ResponseEntity with status 201 (Created) and with body the new position, or with status 400 (Bad Request) if the position has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/positions")
    @Timed
    public ResponseEntity<Position> createPosition(@RequestBody Position position) throws URISyntaxException {
        log.debug("REST request to save Position : {}", position);
        if (position.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new position cannot already have an ID")).body(null);
        }
        Position result = positionService.save(position);
        return ResponseEntity.created(new URI("/api/positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /positions : Updates an existing position.
     *
     * @param position the position to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated position,
     * or with status 400 (Bad Request) if the position is not valid,
     * or with status 500 (Internal Server Error) if the position couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/positions")
    @Timed
    public ResponseEntity<Position> updatePosition(@RequestBody Position position) throws URISyntaxException {
        log.debug("REST request to update Position : {}", position);
        if (position.getId() == null) {
            return createPosition(position);
        }
        Position result = positionService.save(position);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, position.getId().toString()))
            .body(result);
    }

    /**
     * GET  /positions : get all the positions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of positions in body
     */
    @GetMapping("/positions")
    @Timed
    public ResponseEntity<List<Position>> getAllPositions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Positions");
        Page<Position> page = positionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/positions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /positions/:id : get the "id" position.
     *
     * @param id the id of the position to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the position, or with status 404 (Not Found)
     */
    @GetMapping("/positions/{id}")
    @Timed
    public ResponseEntity<Position> getPosition(@PathVariable Long id) {
        log.debug("REST request to get Position : {}", id);
        Position position = positionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(position));
    }

    @GetMapping("/positions/company")
    @Timed
    public ResponseEntity<List<Position>> getAllPositionsByCurrentCompany() {
        log.debug("REST request to get All Positions By Current Comapny");
        List<Position> positions = positionService.findAllByCurrentCompany();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(positions));
    }

    /**
     * DELETE  /positions/:id : delete the "id" position.
     *
     * @param id the id of the position to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/positions/{id}")
    @Timed
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        log.debug("REST request to delete Position : {}", id);
        positionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
