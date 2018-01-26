package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Disease;

import com.impltech.repository.DiseaseRepository;
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
 * @author dima
 * REST controller for managing Disease.
 */
@RestController
@RequestMapping("/api")
public class DiseaseResource {

    private final Logger log = LoggerFactory.getLogger(DiseaseResource.class);

    private static final String ENTITY_NAME = "disease";

    private final DiseaseRepository diseaseRepository;

    public DiseaseResource(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    /**
     * POST  /diseases : Create a new disease.
     *
     * @param disease the disease to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disease, or with status 400 (Bad Request) if the disease has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diseases")
    @Timed
    public ResponseEntity<Disease> createDisease(@Valid @RequestBody Disease disease) throws URISyntaxException {
        log.debug("REST request to save Disease : {}", disease);
        if (disease.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new disease cannot already have an ID")).body(null);
        }
        Disease result = diseaseRepository.save(disease);
        return ResponseEntity.created(new URI("/api/diseases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diseases : Updates an existing disease.
     *
     * @param disease the disease to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disease,
     * or with status 400 (Bad Request) if the disease is not valid,
     * or with status 500 (Internal Server Error) if the disease couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diseases")
    @Timed
    public ResponseEntity<Disease> updateDisease(@Valid @RequestBody Disease disease) throws URISyntaxException {
        log.debug("REST request to update Disease : {}", disease);
        if (disease.getId() == null) {
            return createDisease(disease);
        }
        Disease result = diseaseRepository.save(disease);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disease.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diseases : get all the diseases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of diseases in body
     */
    @GetMapping("/diseases")
    @Timed
    public ResponseEntity<List<Disease>> getAllDiseases(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Diseases");
        Page<Disease> page = diseaseRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/diseases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /diseases/:id : get the "id" disease.
     *
     * @param id the id of the disease to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disease, or with status 404 (Not Found)
     */
    @GetMapping("/diseases/{id}")
    @Timed
    public ResponseEntity<Disease> getDisease(@PathVariable Long id) {
        log.debug("REST request to get Disease : {}", id);
        Disease disease = diseaseRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(disease));
    }

    /**
     * DELETE  /diseases/:id : delete the "id" disease.
     *
     * @param id the id of the disease to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diseases/{id}")
    @Timed
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        log.debug("REST request to delete Disease : {}", id);
        diseaseRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
