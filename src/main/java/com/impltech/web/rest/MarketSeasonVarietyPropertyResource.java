package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.MarketSeason;
import com.impltech.domain.MarketSeasonVarietyProperty;
import com.impltech.domain.ShippingPolicy;
import com.impltech.domain.Variety;
import com.impltech.service.MarketSeasonService;
import com.impltech.service.MarketSeasonVarietyPropertyService;
import com.impltech.service.ShippingPolicyService;
import com.impltech.service.VarietyService;
import com.impltech.service.dto.SeasonPriceListDTO;
import com.impltech.service.util.ConvertUtil;
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
 * REST controller for managing MarketSeasonVarietyProperty.
 */
@RestController
@RequestMapping("/api")
public class MarketSeasonVarietyPropertyResource {

    private final Logger log = LoggerFactory.getLogger(MarketSeasonVarietyPropertyResource.class);

    private static final String ENTITY_NAME = "marketSeasonVarietyProperty";

    private final MarketSeasonVarietyPropertyService marketSeasonVarietyPropertyService;
    private final VarietyService varietyService;
    private final MarketSeasonService marketSeasonService;
    private final ShippingPolicyService shippingPolicyService;

    public MarketSeasonVarietyPropertyResource(MarketSeasonVarietyPropertyService marketSeasonVarietyPropertyService,
                                               VarietyService varietyService,
                                               MarketSeasonService marketSeasonService,
                                               ShippingPolicyService shippingPolicyService) {
        this.marketSeasonVarietyPropertyService = marketSeasonVarietyPropertyService;
        this.varietyService = varietyService;
        this.marketSeasonService = marketSeasonService;
        this.shippingPolicyService = shippingPolicyService;
    }

    /**
     * POST  /market-season-variety-properties : Create a new marketSeasonVarietyProperty.
     *
     * @param marketSeasonVarietyProperty the marketSeasonVarietyProperty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketSeasonVarietyProperty, or with status 400 (Bad Request) if the marketSeasonVarietyProperty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-season-variety-properties")
    @Timed
    public ResponseEntity<MarketSeasonVarietyProperty> createMarketSeasonVarietyProperty(@RequestBody MarketSeasonVarietyProperty marketSeasonVarietyProperty) throws URISyntaxException {
        log.debug("REST request to save MarketSeasonVarietyProperty : {}", marketSeasonVarietyProperty);
        if (marketSeasonVarietyProperty.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketSeasonVarietyProperty cannot already have an ID")).body(null);
        }
        MarketSeasonVarietyProperty result = marketSeasonVarietyPropertyService.save(marketSeasonVarietyProperty);
        return ResponseEntity.created(new URI("/api/market-season-variety-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-season-variety-properties : Updates an existing marketSeasonVarietyProperty.
     *
     * @param marketSeasonVarietyProperty the marketSeasonVarietyProperty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketSeasonVarietyProperty,
     * or with status 400 (Bad Request) if the marketSeasonVarietyProperty is not valid,
     * or with status 500 (Internal Server Error) if the marketSeasonVarietyProperty couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-season-variety-properties")
    @Timed
    public ResponseEntity<Void> updateMarketSeasonVarietyProperty(@RequestBody MarketSeasonVarietyProperty marketSeasonVarietyProperty) throws URISyntaxException {
        log.debug("REST request to update MarketSeasonVarietyProperty : {}", marketSeasonVarietyProperty);


        marketSeasonVarietyPropertyService.saveOrDeleteOne(marketSeasonVarietyProperty);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /market-season-variety-properties : get all the marketSeasonVarietyProperties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketSeasonVarietyProperties in body
     */
    @GetMapping("/market-season-variety-properties")
    @Timed
    public List<MarketSeasonVarietyProperty> getAllMarketSeasonVarietyProperties() {
        log.debug("REST request to get all MarketSeasonVarietyProperties");
        return marketSeasonVarietyPropertyService.findAll();
    }

    /**
     * GET  /market-season-variety-properties/:id : get the "id" marketSeasonVarietyProperty.
     *
     * @param id the id of the marketSeasonVarietyProperty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketSeasonVarietyProperty, or with status 404 (Not Found)
     */
    @GetMapping("/market-season-variety-properties/{id}")
    @Timed
    public ResponseEntity<MarketSeasonVarietyProperty> getMarketSeasonVarietyProperty(@PathVariable Long id) {
        log.debug("REST request to get MarketSeasonVarietyProperty : {}", id);
        MarketSeasonVarietyProperty marketSeasonVarietyProperty = marketSeasonVarietyPropertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketSeasonVarietyProperty));
    }

    /**
     * DELETE  /market-season-variety-properties/:id : delete the "id" marketSeasonVarietyProperty.
     *
     * @param id the id of the marketSeasonVarietyProperty to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-season-variety-properties/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketSeasonVarietyProperty(@PathVariable Long id) {
        log.debug("REST request to delete MarketSeasonVarietyProperty : {}", id);
        marketSeasonVarietyPropertyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/market-season-variety-properties/market-season/{idMarketSeason}/variety/{idVariety}/shipping-policy/{idShippingPolicy}")
    @Timed
    public ResponseEntity<SeasonPriceListDTO> getOneSeasonPriceListDTOBy(
        @PathVariable Long idMarketSeason,
        @PathVariable Long idVariety,
        @PathVariable Long idShippingPolicy) {
        log.debug("REST request to get SeasonPriceListDTO");

        Variety variety = varietyService.findOneByCurrentCompanyId(idVariety);
        MarketSeason marketSeason = marketSeasonService.findOneByCurrentCompanyId(idMarketSeason);
        ShippingPolicy shippingPolicy = shippingPolicyService.findOneByCompanyId(idShippingPolicy);

        if (variety == null || marketSeason == null || shippingPolicy == null) {
           return ResponseEntity.badRequest().body(null);
        }

        List<MarketSeasonVarietyProperty> marketSeasonVarietyProperties =
            marketSeasonVarietyPropertyService.findAllBy(idMarketSeason, idVariety, idShippingPolicy);

        SeasonPriceListDTO seasonPriceListDTO = ConvertUtil.marketSeasonVarietyPropertiesToSeasonPriceListDTO(
            marketSeasonVarietyProperties, marketSeason, variety, shippingPolicy);

        return ResponseEntity.status(200).body(seasonPriceListDTO);
    }

    @GetMapping("/market-season-variety-properties/price-list/{type}")
    @Timed
    public List<SeasonPriceListDTO> getAllSeasonPriceListDTOsBy(@PathVariable String type) {
        log.debug("REST request to get all SeasonPriceListDTO by PriceList and CurrentCompany");
        List<MarketSeasonVarietyProperty> msvp = marketSeasonVarietyPropertyService.findAllBy(type);
        return ConvertUtil.listMarketSeasonVarietyPropertyToListSeasonPriceListDTO(msvp);
    }


    @GetMapping("/market-season-variety-properties/market-season/{idMarketSeason}/price-list/{type}/type-of-flower/{idTypeOfFlowers}/shipping-policy/{idShippingPolicy}")
    @Timed
    public ResponseEntity<List<SeasonPriceListDTO>> getAllMarketVarietyPropertiesBy(
        @PathVariable Long idMarketSeason,
        @PathVariable String type,
        @PathVariable Long idTypeOfFlowers,
        @PathVariable Long idShippingPolicy) {
        log.debug("REST request to get list SeasonPriceListDTO");
        MarketSeason marketSeason = marketSeasonService.findOneByCurrentCompanyId(idMarketSeason);
        ShippingPolicy shippingPolicy = shippingPolicyService.findOneByCompanyId(idShippingPolicy);

        if (marketSeason == null || shippingPolicy == null) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Variety> varieties = varietyService.findVarietiesByIdCompanyAndTypeOfFlower(idTypeOfFlowers);

        List<MarketSeasonVarietyProperty> marketVarietyProperties = marketSeasonVarietyPropertyService
            .findAllBy(idMarketSeason, type, idTypeOfFlowers, idShippingPolicy);

        List<SeasonPriceListDTO> seasonPriceListDTOS = ConvertUtil.listMarketSeasonVarietyPropertyToListSeasonPriceListDTO(
            marketVarietyProperties, varieties, marketSeason, shippingPolicy);

        return ResponseEntity.status(200).body(seasonPriceListDTOS);

    }
}
