package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.PriceList;
import com.impltech.service.PriceListServiceSH;
import com.impltech.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author alex
 * REST controller for managing PriceList.
 */
@RestController
@RequestMapping("/api")
public class PriceListResourceSH {

    private final Logger log = LoggerFactory.getLogger(PriceListResourceSH.class);

    private static final String ENTITY_NAME = "priceList";

    private final PriceListServiceSH priceListService;

    public PriceListResourceSH(PriceListServiceSH priceListService) {
        this.priceListService = priceListService;
    }

    /**
     * POST  /price-lists : Create a new priceList.
     *
     * @param priceList the priceList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priceList, or with status 400 (Bad Request) if the priceList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/price-lists")
    @Timed
    public ResponseEntity<PriceList> createPriceList(@RequestBody PriceList priceList) throws URISyntaxException {
        log.debug("REST request to save PriceList : {}", priceList);
        if (priceList.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new priceList cannot already have an ID")).body(null);
        }
        PriceList result = priceListService.save(priceList);
        return ResponseEntity.created(new URI("/api/price-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /price-lists : Updates an existing priceList.
     *
     * @param priceList the priceList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated priceList,
     * or with status 400 (Bad Request) if the priceList is not valid,
     * or with status 500 (Internal Server Error) if the priceList couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/price-lists")
    @Timed
    public ResponseEntity<PriceList> updatePriceList(@RequestBody PriceList priceList) throws URISyntaxException {
        log.debug("REST request to update PriceList : {}", priceList);
        if (priceList.getId() == null) {
            return createPriceList(priceList);
        }
        PriceList result = priceListService.save(priceList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, priceList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /price-lists : get all the priceLists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of priceLists in body
     */
    @GetMapping("/price-lists")
    @Timed
    public List<PriceList> getAllPriceLists() {
        log.debug("REST request to get all PriceLists");
        return priceListService.findAll();
    }

    /**
     * DELETE  /price-lists/:id : delete the "id" priceList.
     *
     * @param id the id of the priceList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/price-lists/{id}")
    @Timed
    public ResponseEntity<Void> deletePriceList(@PathVariable Long id) {
        log.debug("REST request to delete PriceList : {}", id);
        priceListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/price-lists/{type}")
    @Timed
    public PriceList getPriceListByPriceListTyeAndCurrentCompanyId(@PathVariable String type) {
        log.debug("REST request to get PriceList : {}", type);
        return priceListService.findPriceListByPriceListTyeAndCurrentCompanyId(type);
    }

}
