package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.MarketClient;
import com.impltech.service.MarketClientService;
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
 * REST controller for managing MarketClient.
 */
@RestController
@RequestMapping("/api")
public class MarketClientResource {

    private final Logger log = LoggerFactory.getLogger(MarketClientResource.class);

    private static final String ENTITY_NAME = "marketClient";

    private final MarketClientService marketClientService;

    public MarketClientResource(MarketClientService marketClientService) {
        this.marketClientService = marketClientService;
    }

    /**
     * POST  /market-clients : Create a new marketClient.
     *
     * @param marketClient the marketClient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketClient, or with status 400 (Bad Request) if the marketClient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-clients")
    @Timed
    public ResponseEntity<MarketClient> createMarketClient(@RequestBody MarketClient marketClient) throws URISyntaxException {
        log.debug("REST request to save MarketClient : {}", marketClient);
        if (marketClient.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketClient cannot already have an ID")).body(null);
        }
        MarketClient result = marketClientService.save(marketClient);
        return ResponseEntity.created(new URI("/api/market-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-clients : Updates an existing marketClient.
     *
     * @param marketClient the marketClient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketClient,
     * or with status 400 (Bad Request) if the marketClient is not valid,
     * or with status 500 (Internal Server Error) if the marketClient couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-clients")
    @Timed
    public ResponseEntity<MarketClient> updateMarketClient(@RequestBody MarketClient marketClient) throws URISyntaxException {
        log.debug("REST request to update MarketClient : {}", marketClient);
        if (marketClient.getId() == null) {
            return createMarketClient(marketClient);
        }
        MarketClient result = marketClientService.save(marketClient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketClient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-clients : get all the marketClients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketClients in body
     */
    @GetMapping("/market-clients")
    @Timed
    public List<MarketClient> getAllMarketClients() {
        log.debug("REST request to get all MarketClients");
        return marketClientService.findAll();
    }

    /**
     * GET  /market-clients/:id : get the "id" marketClient.
     *
     * @param id the id of the marketClient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketClient, or with status 404 (Not Found)
     */
    @GetMapping("/market-clients/{id}")
    @Timed
    public ResponseEntity<MarketClient> getMarketClient(@PathVariable Long id) {
        log.debug("REST request to get MarketClient : {}", id);
        MarketClient marketClient = marketClientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketClient));
    }

    /**
     * DELETE  /market-clients/:id : delete the "id" marketClient.
     *
     * @param id the id of the marketClient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-clients/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketClient(@PathVariable Long id) {
        log.debug("REST request to delete MarketClient : {}", id);
        marketClientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
