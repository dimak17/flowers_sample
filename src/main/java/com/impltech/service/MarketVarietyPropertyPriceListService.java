package com.impltech.service;

import com.impltech.domain.Company;
import com.impltech.domain.MarketVarietyPropertyPriceList;
import com.impltech.domain.enumeration.Length;
import com.impltech.domain.enumeration.PriceListType;
import com.impltech.repository.MarketVarietyPropertyPriceListRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author alex
 * Service Implementation for managing MarketVarietyPropertyPriceList.
 */
@Service
@Transactional
public class MarketVarietyPropertyPriceListService {

    private final Logger log = LoggerFactory.getLogger(MarketVarietyPropertyPriceListService.class);

    private final MarketVarietyPropertyPriceListRepository marketVarietyPropertyPriceListRepository;

    public MarketVarietyPropertyPriceListService(MarketVarietyPropertyPriceListRepository marketVarietyPropertyPriceListRepository) {
        this.marketVarietyPropertyPriceListRepository = marketVarietyPropertyPriceListRepository;
    }

    /**
     * Save a marketVarietyPropertyPriceList.
     *
     * @param marketVarietyPropertyPriceList the entity to save
     * @return the persisted entity
     */
    public MarketVarietyPropertyPriceList save(MarketVarietyPropertyPriceList marketVarietyPropertyPriceList) {
        log.debug("Request to save MarketVarietyPropertyPriceList : {}", marketVarietyPropertyPriceList);
        return marketVarietyPropertyPriceListRepository.save(marketVarietyPropertyPriceList);
    }



    public void saveOrDelete(MarketVarietyPropertyPriceList marketVarietyPropertyPriceList) {
        log.debug("Request to save MarketVarietyPropertyPriceList : {}", marketVarietyPropertyPriceList);
        Long marketId = marketVarietyPropertyPriceList.getMarket().getId();
        Long varietyId = marketVarietyPropertyPriceList.getVariety().getId();
        Long priceListId = marketVarietyPropertyPriceList.getPriceList().getId();
        Long shippingPolicyId = marketVarietyPropertyPriceList.getShippingPolicy().getId();
        Length length = marketVarietyPropertyPriceList.getLength();
        BigDecimal price = marketVarietyPropertyPriceList.getPrice();
        Company currentCompany = SecurityUtils.getCurrentCompanyUser().getCompany();
        Long currentCompanyId = currentCompany.getId();

        Long checkMarketVarietyPropertyPriceListId = marketVarietyPropertyPriceListRepository
            .findMarketVarietyPropertyIdBy(marketId, varietyId, priceListId, shippingPolicyId, length, currentCompanyId);

        marketVarietyPropertyPriceList.setCompany(currentCompany);

        if (checkMarketVarietyPropertyPriceListId != null) {
            if (price.compareTo(BigDecimal.ZERO) == 0) {
                marketVarietyPropertyPriceListRepository.deleteByCompanyId(checkMarketVarietyPropertyPriceListId, currentCompanyId);
            } else {
                marketVarietyPropertyPriceList.setId(checkMarketVarietyPropertyPriceListId);
                marketVarietyPropertyPriceListRepository.save(marketVarietyPropertyPriceList);
            }
        } else {
            if (price.compareTo(BigDecimal.ZERO) > 0) {
                marketVarietyPropertyPriceListRepository.save(marketVarietyPropertyPriceList);
            }
        }
    }

    /**
     * Save a marketVarietyPropertyPriceLists.
     *
     * @param marketVarietyPropertyPriceLists the entity to save
     * @return the persisted entity
     */
    public void saveAll(List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceLists) {
        log.debug("Request to save MarketVarietyPropertyPriceList : {}", marketVarietyPropertyPriceLists);
        marketVarietyPropertyPriceListRepository.save(marketVarietyPropertyPriceLists);
    }

    /**
     * Get all the marketVarietyPropertyPriceList.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketVarietyPropertyPriceList> findAll() {
        log.debug("Request to get all MarketVarietyPropertyPriceLists");
        return marketVarietyPropertyPriceListRepository.findAll();
    }


    /**
     * Get one marketVarietyPropertyPriceList by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MarketVarietyPropertyPriceList findOne(Long id) {
        log.debug("Request to get MarketVarietyPropertyPriceList : {}", id);
        return marketVarietyPropertyPriceListRepository.findOne(id);
    }

    /**
     * Delete the  marketVarietyProperty by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MarketVarietyPropertyPriceList : {}", id);
        marketVarietyPropertyPriceListRepository.delete(id);
    }

    public void delete(List<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceLists) {
        log.debug("Request to delete MarketVarietyPropertyPriceList : {}", marketVarietyPropertyPriceLists);
        marketVarietyPropertyPriceListRepository.delete(marketVarietyPropertyPriceLists);
    }

    /**
     * Get all the marketVarietyPropertyPriceLists by market id and variety id.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketVarietyPropertyPriceList> findAllBy(Long marketId, Long varietyId, Long idShippingPolicy, String type) {
        log.debug("Request to get all MarketVarietyProperties by Market id and Variety id");
        PriceListType priceListType = PriceListType.valueOf(type.toUpperCase());
        Long currentCompanyId =  SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return marketVarietyPropertyPriceListRepository.findAllBy(marketId, varietyId, idShippingPolicy, priceListType, currentCompanyId);
    }

    /**
     * Get all the marketVarietyPropertyPriceLists by priceList type and current company id.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketVarietyPropertyPriceList> findAllBy(String type) {
        log.debug("Request to get  MarketVarietyPropertyPriceList");
        Long currentCompanyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        PriceListType priceListType = PriceListType.valueOf(type.toUpperCase());
        return marketVarietyPropertyPriceListRepository.findAllBy(currentCompanyId, priceListType);
    }


    /**
     * Get all the marketVarietyPropertyPriceLists by market id and priceList type and type of flowers id and shipping policy id.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketVarietyPropertyPriceList> findAllBy(Long marketId, String type, Long typeOfFlowersId, Long shippingPolicyId) {
        log.debug("Request to get  MarketVarietyPropertyPriceList");
        String typeOfPriceLIst = type.toUpperCase();
        return marketVarietyPropertyPriceListRepository.findAllBy(marketId, typeOfPriceLIst, typeOfFlowersId, shippingPolicyId);
    }

}
