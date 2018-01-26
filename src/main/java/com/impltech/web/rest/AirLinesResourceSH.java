package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.AirLines;
import com.impltech.service.AirLinesServiceSH;
import com.impltech.validator.util.AirLinesValidator;
import com.impltech.web.rest.util.HeaderUtil;
import com.impltech.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * @author dima
 * REST controller for managing AirLines.
 */
@RestController
@RequestMapping("/api")
public class AirLinesResourceSH {

    private final Logger log = LoggerFactory.getLogger(AirLinesResourceSH.class);

    private static final String ENTITY_NAME = "airLines";

    private final AirLinesServiceSH airLinesService;

    public AirLinesResourceSH(AirLinesServiceSH airLinesService) {
        this.airLinesService = airLinesService;
    }

    /**
     * POST  /air-lines : Create a new airLines.
     *
     * @param airLines the airLines to create
     * @return the ResponseEntity with status 201 (Created) and with body the new airLines, or with status 400 (Bad Request) if the airLines has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/air-lines")
    @Timed
    public ResponseEntity<AirLines> createAirLines(@RequestBody AirLines airLines) throws URISyntaxException {
        log.debug("REST request to save AirLines : {}", airLines);

        if (AirLinesValidator.AirLinesIdentityCheck(airLines, this.airLinesService))
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();

        if (AirLinesValidator.checkFieldsLength(airLines)) {
            AirLines result = airLinesService.save(airLines);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, airLines.getId().toString()))
                .body(result);
        }
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "400", "request is not valid")).build();
    }

    /**
     * PUT  /air-lines : Updates an existing airLines.
     *
     * @param airLines the airLines to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated airLines,
     * or with status 400 (Bad Request) if the airLines is not valid,
     * or with status 500 (Internal Server Error) if the airLines couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/air-lines")
    @Timed
    public ResponseEntity<AirLines> updateAirLines(@RequestBody AirLines airLines) throws URISyntaxException {
        log.debug("REST request to update AirLines : {}", airLines);

        if (AirLinesValidator.AirLinesIdentityCheck(airLines, this.airLinesService))
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();

        if (AirLinesValidator.checkID(airLines) && AirLinesValidator.checkFieldsLength(airLines)) {
            AirLines result = airLinesService.save(airLines);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, airLines.getId().toString()))
                .body(result);
        }
        return ResponseEntity.badRequest()
            .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "400", "request is not valid")).build();
    }

    /**
     * GET  /air-lines : get all the airLines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of airLines in body
     */
    @GetMapping("/air-lines")
    @Timed
    public ResponseEntity<List<AirLines>> getAllAirLines(@ApiParam Pageable pageable) {
        log.debug("REST request to get all AirLines");
        Page<AirLines> page = airLinesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/air-lines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /air-lines/:id : get the "id" airLines.
     *
     * @param id the id of the airLines to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the airLines, or with status 404 (Not Found)
     */
    @GetMapping("/air-lines/{id}")
    @Timed
    public ResponseEntity<AirLines> getAirLines(@PathVariable Long id) {
        log.debug("REST request to get AirLines by id Company: {}", id);
        AirLines airLines = airLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(airLines));
    }

    /**
     * DELETE  /air-lines/:id : delete the "id" airLines.
     *
     * @param id the id of the airLines to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/air-lines/{id}")
    @Timed
    public ResponseEntity<Void> deleteAirLines(@PathVariable Long id) {
        log.debug("REST request to delete AirLines : {}", id);
        airLinesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
