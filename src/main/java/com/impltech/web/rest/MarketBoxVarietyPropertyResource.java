package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.MarketBoxVarietyProperty;
import com.impltech.service.MarketBoxVarietyPropertyService;
import com.impltech.service.dto.MarketBoxVarietyPropertyDTO;
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
 * REST controller for managing MarketBoxVarietyProperty.
 */
@RestController
@RequestMapping("/api")
public class MarketBoxVarietyPropertyResource {

    private final Logger log = LoggerFactory.getLogger(MarketBoxVarietyPropertyResource.class);

    private static final String ENTITY_NAME = "marketBoxVarietyProperty";

    private final MarketBoxVarietyPropertyService marketBoxVarietyPropertyService;

    public MarketBoxVarietyPropertyResource(MarketBoxVarietyPropertyService marketBoxVarietyPropertyService) {
        this.marketBoxVarietyPropertyService = marketBoxVarietyPropertyService;
    }

    /**
     * POST  /market-box-variety-properties : Create a new marketBoxVarietyProperty.
     *
     * @param marketBoxVarietyProperty the marketBoxVarietyProperty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketBoxVarietyProperty, or with status 400 (Bad Request) if the marketBoxVarietyProperty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-box-variety-properties")
    @Timed
    public ResponseEntity<MarketBoxVarietyProperty> createMarketBoxVarietyProperty(@RequestBody MarketBoxVarietyProperty marketBoxVarietyProperty) throws URISyntaxException {
        log.debug("REST request to save MarketBoxVarietyProperty : {}", marketBoxVarietyProperty);
        if (marketBoxVarietyProperty.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketBoxVarietyProperty cannot already have an ID")).body(null);
        }
        MarketBoxVarietyProperty result = marketBoxVarietyPropertyService.save(marketBoxVarietyProperty);
        return ResponseEntity.created(new URI("/api/market-box-variety-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-box-variety-properties : Updates an existing marketBoxVarietyProperty.
     *
     * @param marketBoxVarietyProperty the marketBoxVarietyProperty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketBoxVarietyProperty,
     * or with status 400 (Bad Request) if the marketBoxVarietyProperty is not valid,
     * or with status 500 (Internal Server Error) if the marketBoxVarietyProperty couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-box-variety-properties")
    @Timed
    public ResponseEntity<MarketBoxVarietyProperty> updateMarketBoxVarietyProperty(@RequestBody MarketBoxVarietyProperty marketBoxVarietyProperty) throws URISyntaxException {
        log.debug("REST request to update MarketBoxVarietyProperty : {}", marketBoxVarietyProperty);
        if (marketBoxVarietyProperty.getId() == null) {
            return createMarketBoxVarietyProperty(marketBoxVarietyProperty);
        }
        MarketBoxVarietyProperty result = marketBoxVarietyPropertyService.save(marketBoxVarietyProperty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketBoxVarietyProperty.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-box-variety-properties : get all the marketBoxVarietyProperties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketBoxVarietyProperties in body
     */
    @GetMapping("/market-box-variety-properties")
    @Timed
    public List<MarketBoxVarietyProperty> getAllMarketBoxVarietyProperties() {
        log.debug("REST request to get all MarketBoxVarietyProperties");
        return marketBoxVarietyPropertyService.findAll();
    }

    /**
     * GET  /market-box-variety-properties/:id : get the "id" marketBoxVarietyProperty.
     *
     * @param id the id of the marketBoxVarietyProperty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketBoxVarietyProperty, or with status 404 (Not Found)
     */
    @GetMapping("/market-box-variety-properties/{id}")
    @Timed
    public ResponseEntity<MarketBoxVarietyProperty> getMarketBoxVarietyProperty(@PathVariable Long id) {
        log.debug("REST request to get MarketBoxVarietyProperty : {}", id);
        MarketBoxVarietyProperty marketBoxVarietyProperty = marketBoxVarietyPropertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketBoxVarietyProperty));
    }

    /**
     * DELETE  /market-box-variety-properties/:id : delete the "id" marketBoxVarietyProperty.
     *
     * @param id the id of the marketBoxVarietyProperty to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-box-variety-properties/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketBoxVarietyProperty(@PathVariable Long id) {
        log.debug("REST request to delete MarketBoxVarietyProperty : {}", id);
        marketBoxVarietyPropertyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /market-box-variety-properties/market-box/{id} : get all the marketBoxVarietyProperties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketBoxVarietyProperties in body
     */
    @GetMapping("/market-box-variety-properties/market-box/{id}")
    @Timed
    public List<MarketBoxVarietyPropertyDTO> getMarketBoxVarietyPropertiesByMarketBox(@PathVariable Long id) {
        log.debug("REST request to get all MarketBoxVarietyProperties");
        return marketBoxVarietyPropertyService.findAllByMarketBox(id);
    }

    /**
     * GET  /market-box-variety-properties/market/{id} : get all the marketBoxVarietyProperties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketBoxVarietyProperties in body
     */
    @GetMapping("/market-box-variety-properties/market/{id}")
    @Timed
    public List<MarketBoxVarietyPropertyDTO> getMarketBoxVarietyPropertiesByMarket(@PathVariable Long id) {
        log.debug("REST request to get all MarketBoxVarietyProperties");
        return marketBoxVarietyPropertyService.findAllByMarket(id);
    }

    /**
     * GET  /market-box-variety-properties : get all the marketBoxVarietyProperties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketBoxVarietyProperties in body
     */
    @PutMapping("/market-box-variety-properties/dtos")
    @Timed
    public ResponseEntity<Void> saveMarketBoxVarietyProperties(
        @RequestBody List<MarketBoxVarietyPropertyDTO> properties
    ) {
        log.debug("REST request to get all MarketBoxVarietyProperties");
        marketBoxVarietyPropertyService.save(properties);
        return ResponseEntity.ok().build();
    }
}
