package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.LabelCountry;
import com.impltech.service.LabelCountryService;
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
 * @author alex
 * REST controller for managing LabelCountry.
 */
@RestController
@RequestMapping("/api")
public class LabelCountryResource {

    private final Logger log = LoggerFactory.getLogger(LabelCountryResource.class);

    private static final String ENTITY_NAME = "labelCountry";

    private final LabelCountryService labelCountryService;

    public LabelCountryResource(LabelCountryService labelCountryService) {
        this.labelCountryService = labelCountryService;
    }

    /**
     * POST  /label-countries : Create a new labelCountry.
     *
     * @param labelCountry the labelCountry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new labelCountry, or with status 400 (Bad Request) if the labelCountry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/label-countries")
    @Timed
    public ResponseEntity<LabelCountry> createLabelCountry(@RequestBody LabelCountry labelCountry) throws URISyntaxException {
        log.debug("REST request to save LabelCountry : {}", labelCountry);
        if (labelCountry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new labelCountry cannot already have an ID")).body(null);
        }
        LabelCountry result = labelCountryService.save(labelCountry);
        return ResponseEntity.created(new URI("/api/label-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /label-countries : Updates an existing labelCountry.
     *
     * @param labelCountry the labelCountry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated labelCountry,
     * or with status 400 (Bad Request) if the labelCountry is not valid,
     * or with status 500 (Internal Server Error) if the labelCountry couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/label-countries")
    @Timed
    public ResponseEntity<LabelCountry> updateLabelCountry(@RequestBody LabelCountry labelCountry) throws URISyntaxException {
        log.debug("REST request to update LabelCountry : {}", labelCountry);
        if (labelCountry.getId() == null) {
            return createLabelCountry(labelCountry);
        }
        LabelCountry result = labelCountryService.save(labelCountry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, labelCountry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /label-countries : get all the labelCountries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of labelCountries in body
     */
    @GetMapping("/label-countries")
    @Timed
    public List<LabelCountry> getAllLabelCountries() {
        log.debug("REST request to get all LabelCountries");
        return labelCountryService.findAll();
    }

    /**
     * GET  /label-countries/:id : get the "id" labelCountry.
     *
     * @param id the id of the labelCountry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the labelCountry, or with status 404 (Not Found)
     */
    @GetMapping("/label-countries/{id}")
    @Timed
    public ResponseEntity<LabelCountry> getLabelCountry(@PathVariable Long id) {
        log.debug("REST request to get LabelCountry : {}", id);
        LabelCountry labelCountry = labelCountryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(labelCountry));
    }

    /**
     * DELETE  /label-countries/:id : delete the "id" labelCountry.
     *
     * @param id the id of the labelCountry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/label-countries/{id}")
    @Timed
    public ResponseEntity<Void> deleteLabelCountry(@PathVariable Long id) {
        log.debug("REST request to delete LabelCountry : {}", id);
        labelCountryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
