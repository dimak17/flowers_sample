package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Season;
import com.impltech.service.SeasonService;
import com.impltech.validator.util.SeasonValidator;
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
 * REST controller for managing Season.
 */
@RestController
@RequestMapping("/api")
public class SeasonResource {

    private final Logger log = LoggerFactory.getLogger(SeasonResource.class);

    private static final String ENTITY_NAME = "season";

    private final SeasonService seasonService;

    public SeasonResource(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    /**
     * POST  /seasons : Create a new season.
     *
     * @param season the season to create
     * @return the ResponseEntity with status 201 (Created) and with body the new season, or with status 400 (Bad Request) if the season has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/seasons")
    @Timed
    public ResponseEntity<Season> createSeason(@RequestBody Season season) throws URISyntaxException {
        log.debug("REST request to save Season : {}", season);
        if (season.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new season cannot already have an ID")).body(null);
        }

        if (SeasonValidator.checkForExistingSeason(season, seasonService)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (SeasonValidator.checkLengthValidation(season)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }
        Season result = seasonService.save(season);
        return ResponseEntity.created(new URI("/api/seasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /seasons : Updates an existing season.
     *
     * @param season the season to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated season,
     * or with status 400 (Bad Request) if the season is not valid,
     * or with status 500 (Internal Server Error) if the season couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/seasons")
    @Timed
    public ResponseEntity<Season> updateSeason(@RequestBody Season season) throws URISyntaxException {
        log.debug("REST request to update Season : {}", season);
        if (season.getId() == null) {
            return createSeason(season);
        }

        if (SeasonValidator.checkForExistingSeason(season, seasonService)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (SeasonValidator.checkLengthValidation(season)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }
        Season result = seasonService.save(season);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, season.getId().toString()))
            .body(result);
    }

    /**
     * GET  /seasons : get all the seasons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of seasons in body
     */
    @GetMapping("/seasons")
    @Timed
    public List<Season> getAllSeasons() {
        log.debug("REST request to get all Seasons");
        List<Season> seasonList = seasonService.findSeasonsByCurrentCompanyId();
        return seasonList;
    }

    /**
     * GET  /seasons/:id : get the "id" season.
     *
     * @param id the id of the season to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the season, or with status 404 (Not Found)
     */
    @GetMapping("/seasons/{id}")
    @Timed
    public ResponseEntity<Season> getSeason(@PathVariable Long id) {
        log.debug("REST request to get Season : {}", id);
        Season season = seasonService.findOneByCompanyId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(season));
    }

    /**
     * DELETE  /seasons/:id : delete the "id" season.
     *
     * @param id the id of the season to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/seasons/{id}")
    @Timed
    public ResponseEntity<Void> deleteSeason(@PathVariable Long id) {
        log.debug("REST request to delete Season : {}", id);
        seasonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
