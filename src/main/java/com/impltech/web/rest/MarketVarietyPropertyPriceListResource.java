package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Market;
import com.impltech.domain.MarketVarietyPropertyPriceList;
import com.impltech.domain.ShippingPolicy;
import com.impltech.domain.Variety;
import com.impltech.service.MarketService;
import com.impltech.service.MarketVarietyPropertyPriceListService;
import com.impltech.service.ShippingPolicyService;
import com.impltech.service.VarietyService;
import com.impltech.service.dto.DefaultPriceListDTO;
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
 * @author alex
 * REST controller for managing MarketVarietyPropertyPriceList.
 */
@RestController
@RequestMapping("/api")
public class MarketVarietyPropertyPriceListResource {

    private final Logger log = LoggerFactory.getLogger(MarketVarietyPropertyPriceListResource.class);

    private static final String ENTITY_NAME = "marketVarietyProperty";

    private final MarketVarietyPropertyPriceListService marketVarietyPropertyPriceListService;
    private final MarketService marketService;
    private final VarietyService varietyService;
    private final ShippingPolicyService shippingPolicyService;

    public MarketVarietyPropertyPriceListResource(MarketVarietyPropertyPriceListService marketVarietyPropertyPriceListService, MarketService marketService, VarietyService varietyService, ShippingPolicyService shippingPolicyService) {
        this.marketVarietyPropertyPriceListService = marketVarietyPropertyPriceListService;
        this.marketService = marketService;
        this.varietyService = varietyService;
        this.shippingPolicyService = shippingPolicyService;
    }

    /**
     * POST  /market-variety-properties : Create a new marketVarietyPropertyPriceList.
     *
     * @param marketVarietyPropertyPriceList the marketVarietyPropertyPriceList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketVarietyPropertyPriceList, or with status 400 (Bad Request) if the marketVarietyPropertyPriceList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-variety-properties")
    @Timed
    public ResponseEntity<MarketVarietyPropertyPriceList> createMarketVarietyPropertyPriceList(@RequestBody MarketVarietyPropertyPriceList marketVarietyPropertyPriceList) throws URISyntaxException {
        log.debug("REST request to save MarketVarietyPropertyPriceList : {}", marketVarietyPropertyPriceList);
        if (marketVarietyPropertyPriceList.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketVarietyPropertyPriceList cannot already have an ID")).body(null);
        }
        MarketVarietyPropertyPriceList result = marketVarietyPropertyPriceListService.save(marketVarietyPropertyPriceList);
        return ResponseEntity.created(new URI("/api/market-variety-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-variety-properties : Updates an existing marketVarietyPropertyPriceList.
     *
     * @param marketVarietyPropertyPriceList the marketVarietyPropertyPriceList to update
     */
    @PutMapping("/market-variety-properties")
    @Timed
    public ResponseEntity<Void> updateMarketVarietyPropertyPriceList(@RequestBody MarketVarietyPropertyPriceList marketVarietyPropertyPriceList) throws URISyntaxException {
        log.debug("REST request to update MarketVarietyPropertyPriceList : {}", marketVarietyPropertyPriceList);
        Market marketByCurrentCompanyId = marketService.findOneByCurrentCompanyId(marketVarietyPropertyPriceList.getMarket().getId());

        if (marketByCurrentCompanyId == null) {
           return ResponseEntity.badRequest().body(null);
        }

        marketVarietyPropertyPriceListService.saveOrDelete(marketVarietyPropertyPriceList);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /market-variety-properties : get all the marketVarietyPropertyPriceLists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marketVarietyPropertyPriceLists in body
     */
    @GetMapping("/market-variety-properties")
    @Timed
    public List<MarketVarietyPropertyPriceList> getAllMarketVarietyPropertyPriceLists() {
        log.debug("REST request to get all MarketVarietyPropertyPriceLIsts");
        return marketVarietyPropertyPriceListService.findAll();
    }

    /**
     * GET  /market-variety-properties/:id : get the "id" marketVarietyPropertyPriceList.
     *
     * @param id the id of the marketVarietyPropertyPriceList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketVarietyPropertyPriceList, or with status 404 (Not Found)
     */
    @GetMapping("/market-variety-properties/{id}")
    @Timed
    public ResponseEntity<MarketVarietyPropertyPriceList> getMarketVarietyPropertyPriceList(@PathVariable Long id) {
        log.debug("REST request to get MarketVarietyPropertyPriceList : {}", id);
        MarketVarietyPropertyPriceList marketVarietyPropertyPriceList = marketVarietyPropertyPriceListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketVarietyPropertyPriceList));
    }

    /**
     * DELETE  /market-variety-properties/:id : delete the "id" marketVarietyPropertyPriceList.
     *
     * @param id the id of the marketVarietyPropertyPriceList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-variety-properties/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarketVarietyPropertyPriceList(@PathVariable Long id) {
        log.debug("REST request to delete MarketVarietyPropertyPriceList : {}", id);
        marketVarietyPropertyPriceListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * GET  /market-variety-properties : get all the marketVarietyPropertyPriceLists by Market id and  Variety id and Shipping policy id.
     *
     * @return the ResponseEntity with status 200 (OK) and PriceListDTO in body
     */
    @GetMapping("/market-variety-properties/market/{idMarket}/variety/{idVariety}/shipping-policy/{idShippingPolicy}/price-list/{type}")
    @Timed
    public ResponseEntity<DefaultPriceListDTO> getPriceListDTO(
        @PathVariable Long idMarket,
        @PathVariable Long idVariety,
        @PathVariable Long idShippingPolicy,
        @PathVariable String type) {
        log.debug("REST request to get PriceListDTO");

        Market market = marketService.findOneByCurrentCompanyId(idMarket);
        Variety variety = varietyService.findOneByCurrentCompanyId(idVariety);
        ShippingPolicy shippingPolicy = shippingPolicyService.findOneByCompanyId(idShippingPolicy);
        if (market == null || variety == null  || shippingPolicy == null) {
            return ResponseEntity.status(400).body(null);
        }
        List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceLists = marketVarietyPropertyPriceListService.findAllBy(idMarket, idVariety, idShippingPolicy, type);

        DefaultPriceListDTO priceListDTO = ConvertUtil.marketVarietyPropertyPriceListToDefaultPriceListDTO(marketVarietyPropertyPriceLists, market, variety, shippingPolicy);
        return ResponseEntity.status(200).body(priceListDTO);
    }

    /**
     * GET  /market-variety-properties : get all the marketVarietyProperties by Market id, price list type, Type of flower id and Shipping policy id.
     *
     * @return the ResponseEntity with status 200 (OK) and PriceListDTO in body
     */
    @GetMapping("/market-variety-properties/market/{idMarket}/price-list/{type}/type-of-flower/{idTypeOfFlowers}/shipping-policy/{idShippingPolicy}")
    @Timed
    public ResponseEntity<List<DefaultPriceListDTO>> getAllBy(
        @PathVariable Long idMarket,
        @PathVariable String type,
        @PathVariable Long idTypeOfFlowers,
        @PathVariable Long idShippingPolicy) {
        log.debug("REST request to get list PriceListDTO");
        Market market = marketService.findOneByCurrentCompanyId(idMarket);
        ShippingPolicy shippingPolicy = shippingPolicyService.findOneByCompanyId(idShippingPolicy);

        if (market == null || shippingPolicy == null) {
            return ResponseEntity.status(400).body(null);
        }

        List<Variety> varieties = varietyService.findVarietiesByIdCompanyAndTypeOfFlower(idTypeOfFlowers);
        List<MarketVarietyPropertyPriceList> marketVarietyProperties = marketVarietyPropertyPriceListService.findAllBy(idMarket, type, idTypeOfFlowers, idShippingPolicy);

        List<DefaultPriceListDTO> priceListDTOs = ConvertUtil.listMarketVarietyPropertyPriceListToListPriceListDTO(marketVarietyProperties, varieties, market, shippingPolicy);
        return ResponseEntity.status(200).body(priceListDTOs);
    }


    /**
     * GET  /market-variety-properties : get all the marketVarietyPropertyPriceList by price list type.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of PriceListDTO in body
     */
    @GetMapping("/market-variety-properties/price-list/{type}")
    @Timed
    public ResponseEntity<List<DefaultPriceListDTO>> getAllByCurrentCompany(@PathVariable String type) {
        log.debug("REST request to get all PriceListDTO by PriceList and CurrentCompany");
        List<MarketVarietyPropertyPriceList> mvp = marketVarietyPropertyPriceListService.findAllBy(type);
        List<DefaultPriceListDTO> priceListDTOList = ConvertUtil.listMarketVarietyPropertyPriceListToListPriceListDTO(mvp);
        return ResponseEntity.status(200).body(priceListDTOList);
    }
}
