package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.MarketSeason;
import com.impltech.service.MarketSeasonService;
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
 * REST controller for managing MarketSeason.
 */
@RestController
@RequestMapping("/api")
public class MarketSeasonResource {

    private final Logger log = LoggerFactory.getLogger(MarketSeasonResource.class);

    private static final String ENTITY_NAME = "marketSeason";

    private final MarketSeasonService marketSeasonService;

    public MarketSeasonResource(MarketSeasonService marketSeasonService) {
        this.marketSeasonService = marketSeasonService;
    }

    /**
     * POST  /market-seasons : Create a new marketSeason.
     *
     * @param marketSeason the marketSeason to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketSeason, or with status 400 (Bad Request) if the marketSeason has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-seasons")
    @Timed
    public ResponseEntity<MarketSeason> createMarketSeason(@RequestBody MarketSeason marketSeason) throws URISyntaxException {
        log.debug("REST request to save MarketSeason : {}", marketSeason);
        if (marketSeason.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketSeason cannot already have an ID")).body(null);
        }
        MarketSeason result = marketSeasonService.save(marketSeason);
        return ResponseEntity.created(new URI("/api/market-seasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-seasons : Updates an existing marketSeason.
     *
     * @param marketSeason the marketSeason to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketSeason,
     * or with status 400 (Bad Request) if the marketSeason is not valid,
     * or with status 500 (Internal Server Error) if the marketSeason couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-seasons")
    @Timed
    public ResponseEntity<MarketSeason> updateMarketSeason(@RequestBody MarketSeason marketSeason) throws URISyntaxException {
        log.debug("REST request to update MarketSeason : {}", marketSeason);
        if (marketSeason.getId() == null) {
            return createMarketSeason(marketSeason);
        }
        MarketSeason result = marketSeasonService.save(marketSeason);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketSeason.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-seasons : get all the marketSeasons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketSeasons in body
     */
    @GetMapping("/market-seasons")
    @Timed
    public List<MarketSeason> getAllMarketSeasons() {
        log.debug("REST request to get all MarketSeasons");
        return marketSeasonService.findAll();
    }

    /**
     * GET  /market-seasons/:id : get the "id" marketSeason.
     *
     * @param id the id of the marketSeason to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketSeason, or with status 404 (Not Found)
     */
    @GetMapping("/market-seasons/{id}")
    @Timed
    public ResponseEntity<MarketSeason> getMarketSeason(@PathVariable Long id) {
        log.debug("REST request to get MarketSeason : {}", id);
        MarketSeason marketSeason = marketSeasonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketSeason));
    }

    /**
     * DELETE  /market-seasons/:id : delete the "id" marketSeason.
     *
     * @param id the id of the marketSeason to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-seasons/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketSeason(@PathVariable Long id) {
        log.debug("REST request to delete MarketSeason : {}", id);
        marketSeasonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * GET  /market-seasons : get all the marketSeasons by current company id.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketSeasons in body
     */
    @GetMapping("/market-seasons/company")
    @Timed
    public List<MarketSeason> getAllMarketSeasonsByCurrentCompany() {
        log.debug("REST request to get all MarketSeasons");
        return marketSeasonService.findAllByCurrentCompany();
    }

}
