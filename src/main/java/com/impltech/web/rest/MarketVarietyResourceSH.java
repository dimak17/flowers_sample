package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.MarketVariety;
import com.impltech.service.MarketVarietyServiceSH;
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
 * REST controller for managing MarketVariety.
 */
@RestController
@RequestMapping("/api")
public class MarketVarietyResourceSH {

    private final Logger log = LoggerFactory.getLogger(MarketVarietyResourceSH.class);

    private static final String ENTITY_NAME = "marketVariety";

    private final MarketVarietyServiceSH marketVarietyService;

    public MarketVarietyResourceSH(MarketVarietyServiceSH marketVarietyService) {
        this.marketVarietyService = marketVarietyService;
    }

    /**
     * POST  /market-varieties : Create a new marketVariety.
     *
     * @param marketVariety the marketVariety to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketVariety, or with status 400 (Bad Request) if the marketVariety has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-varieties")
    @Timed
    public ResponseEntity<MarketVariety> createMarketVariety(@RequestBody MarketVariety marketVariety) throws URISyntaxException {
        log.debug("REST request to save MarketVariety : {}", marketVariety);
        if (marketVariety.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketVariety cannot already have an ID")).body(null);
        }
        MarketVariety result = marketVarietyService.save(marketVariety);
        return ResponseEntity.created(new URI("/api/market-varieties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-varieties : Updates an existing marketVariety.
     *
     * @param marketVariety the marketVariety to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketVariety,
     * or with status 400 (Bad Request) if the marketVariety is not valid,
     * or with status 500 (Internal Server Error) if the marketVariety couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-varieties")
    @Timed
    public ResponseEntity<MarketVariety> updateMarketVariety(@RequestBody MarketVariety marketVariety) throws URISyntaxException {
        log.debug("REST request to update MarketVariety : {}", marketVariety);
        if (marketVariety.getId() == null) {
            return createMarketVariety(marketVariety);
        }
        MarketVariety result = marketVarietyService.save(marketVariety);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketVariety.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-varieties : get all the marketVarieties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketVarieties in body
     */
    @GetMapping("/market-varieties")
    @Timed
    public List<MarketVariety> getAllMarketVarieties() {
        log.debug("REST request to get all MarketVarieties");
        return marketVarietyService.findAll();
    }


    /**
     * GET  /market-varieties/:id : get the "id" marketVariety.
     *
     * @param id the id of the marketVariety to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketVariety, or with status 404 (Not Found)
     */
    @GetMapping("/market-varieties/{id}")
    @Timed
    public ResponseEntity<MarketVariety> getMarketVariety(@PathVariable Long id) {
        log.debug("REST request to get MarketVariety : {}", id);
        MarketVariety marketVariety = marketVarietyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketVariety));
    }

    @GetMapping("/market-varieties/by-marketVariety/{id}")
    @Timed
    public List<MarketVariety> getMarketVarieties(@PathVariable Long id) {
        log.debug("REST request to get MarketVariety : {}", id);
        return marketVarietyService.findByMarketId(id);
    }

    /**
     * DELETE  /market-varieties/:id : delete the "id" marketVariety.
     *
     * @param id the id of the marketVariety to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-varieties/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketVariety(@PathVariable Long id) {
        log.debug("REST request to delete MarketVariety : {}", id);
        marketVarietyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/market-varieties/market/{id}")
    @Timed
    public List<MarketVariety> getAllMarketVarieties(@PathVariable Long id) {
        log.debug("REST request to get all MarketVarieties by Market: {}", id);
        return marketVarietyService.findByMarketId(id);
    }
}
