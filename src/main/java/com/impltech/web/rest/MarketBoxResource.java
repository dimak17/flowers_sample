package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.MarketBox;
import com.impltech.service.MarketBoxService;
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
 * REST controller for managing MarketBox.
 */
@RestController
@RequestMapping("/api")
public class MarketBoxResource {

    private final Logger log = LoggerFactory.getLogger(MarketBoxResource.class);

    private static final String ENTITY_NAME = "marketBox";

    private final MarketBoxService marketBoxService;

    public MarketBoxResource(MarketBoxService marketBoxService) {
        this.marketBoxService = marketBoxService;
    }

    /**
     * POST  /market-boxes : Create a new marketBox.
     *
     * @param marketBox the marketBox to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketBox, or with status 400 (Bad Request) if the marketBox has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-boxes")
    @Timed
    public ResponseEntity<MarketBox> createMarketBox(@RequestBody MarketBox marketBox) throws URISyntaxException {
        log.debug("REST request to save MarketBox : {}", marketBox);
        if (marketBox.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketBox cannot already have an ID")).body(null);
        }
        MarketBox result = marketBoxService.save(marketBox);
        return ResponseEntity.created(new URI("/api/market-boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-boxes : Updates an existing marketBox.
     *
     * @param marketBox the marketBox to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketBox,
     * or with status 400 (Bad Request) if the marketBox is not valid,
     * or with status 500 (Internal Server Error) if the marketBox couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-boxes")
    @Timed
    public ResponseEntity<MarketBox> updateMarketBox(@RequestBody MarketBox marketBox) throws URISyntaxException {
        log.debug("REST request to update MarketBox : {}", marketBox);
        if (marketBox.getId() == null) {
            return createMarketBox(marketBox);
        }
        MarketBox result = marketBoxService.save(marketBox);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketBox.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-boxes : get all the marketBoxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketBoxes in body
     */
    @GetMapping("/market-boxes")
    @Timed
    public List<MarketBox> getAllMarketBoxes() {
        log.debug("REST request to get all MarketBoxes");
        return marketBoxService.findAll();
    }

    /**
     * GET  /market-boxes/:id : get the "id" marketBox.
     *
     * @param id the id of the marketBox to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketBox, or with status 404 (Not Found)
     */
    @GetMapping("/market-boxes/{id}")
    @Timed
    public ResponseEntity<MarketBox> getMarketBox(@PathVariable Long id) {
        log.debug("REST request to get MarketBox : {}", id);
        MarketBox marketBox = marketBoxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketBox));
    }

    /**
     * DELETE  /market-boxes/:id : delete the "id" marketBox.
     *
     * @param id the id of the marketBox to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-boxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketBox(@PathVariable Long id) {
        log.debug("REST request to delete MarketBox : {}", id);
        marketBoxService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
