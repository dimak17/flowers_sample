package com.impltech.service;

import com.impltech.domain.Company;
import com.impltech.domain.MarketSeasonVarietyProperty;
import com.impltech.domain.enumeration.Length;
import com.impltech.domain.enumeration.PriceListType;
import com.impltech.repository.MarketSeasonVarietyPropertyRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author alex
 * Service Implementation for managing MarketSeasonVarietyProperty.
 */
@Service
@Transactional
public class MarketSeasonVarietyPropertyService {

    private final Logger log = LoggerFactory.getLogger(MarketSeasonVarietyPropertyService.class);

    private final MarketSeasonVarietyPropertyRepository marketSeasonVarietyPropertyRepository;

    public MarketSeasonVarietyPropertyService(MarketSeasonVarietyPropertyRepository marketSeasonVarietyPropertyRepository) {
        this.marketSeasonVarietyPropertyRepository = marketSeasonVarietyPropertyRepository;
    }

    /**
     * Save a marketSeasonVarietyProperty.
     *
     * @param marketSeasonVarietyProperty the entity to save
     * @return the persisted entity
     */
    public MarketSeasonVarietyProperty save(MarketSeasonVarietyProperty marketSeasonVarietyProperty) {
        log.debug("Request to save MarketSeasonVarietyProperty : {}", marketSeasonVarietyProperty);
        return marketSeasonVarietyPropertyRepository.save(marketSeasonVarietyProperty);
    }


    public void saveOrDeleteOne(MarketSeasonVarietyProperty marketSeasonVarietyProperty) {
        log.debug("Request to save MarketSeasonVarietyProperty : {}", marketSeasonVarietyProperty);

        Long marketSeasonId = marketSeasonVarietyProperty.getMarketSeason().getId();
        Long varietyId = marketSeasonVarietyProperty.getVariety().getId();
        Long priceListId = marketSeasonVarietyProperty.getPriceList().getId();
        Long shippingPolicyId = marketSeasonVarietyProperty.getShippingPolicy().getId();
        Length length = marketSeasonVarietyProperty.getLength();
        BigDecimal price = marketSeasonVarietyProperty.getPrice();

        Company currentCompany = SecurityUtils.getCurrentCompanyUser().getCompany();
        Long currentCompanyId = currentCompany.getId();

        Long marketSeasonVarietyPropertyIdBy = marketSeasonVarietyPropertyRepository
            .findMarketSeasonVarietyPropertyIdBy(marketSeasonId, varietyId, priceListId, shippingPolicyId, length, currentCompanyId);

        marketSeasonVarietyProperty.setCompany(currentCompany);

        if (marketSeasonVarietyPropertyIdBy !=  null) {
           if (price.compareTo(BigDecimal.ZERO) == 0) {
               marketSeasonVarietyPropertyRepository.deleteByCompanyId(currentCompanyId, marketSeasonVarietyPropertyIdBy);
           } else {
               marketSeasonVarietyProperty.setId(marketSeasonVarietyPropertyIdBy);
               marketSeasonVarietyPropertyRepository.save(marketSeasonVarietyProperty);
           }
        } else {
            if(price.compareTo(BigDecimal.ZERO) > 0) {
                marketSeasonVarietyPropertyRepository.save(marketSeasonVarietyProperty);
            }
        }
    }

    /**
     *  Get all the marketSeasonVarietyProperties.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketSeasonVarietyProperty> findAll() {
        log.debug("Request to get all MarketSeasonVarietyProperties");
        return marketSeasonVarietyPropertyRepository.findAll();
    }

    /**
     *  Get one marketSeasonVarietyProperty by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MarketSeasonVarietyProperty findOne(Long id) {
        log.debug("Request to get MarketSeasonVarietyProperty : {}", id);
        return marketSeasonVarietyPropertyRepository.findOne(id);
    }

    /**
     *  Delete the  marketSeasonVarietyProperty by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MarketSeasonVarietyProperty : {}", id);
        marketSeasonVarietyPropertyRepository.delete(id);
    }


    /**
     *  Get all the MarketSeasonVarietyProperty by MarketSeason id and Variety id.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketSeasonVarietyProperty> findAllBy(Long idMarketSeason, Long idVariety, Long idShippingPolicy) {
        log.debug("Request to get all MarketSeasonVarietyProperty by MarketSeason id and Variety id");
        Long currentCompanyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return marketSeasonVarietyPropertyRepository.findAllBy(idMarketSeason, idVariety, idShippingPolicy,currentCompanyId);
    }

    @Transactional(readOnly = true)
    public List<MarketSeasonVarietyProperty> findAllBy(String type) {
        log.debug("Request to get  MarketSeasonVarietyProperty");
        Long currentCompanyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        PriceListType priceListType = PriceListType.valueOf(type.toUpperCase());
        return marketSeasonVarietyPropertyRepository.findAllBy(currentCompanyId, priceListType);
    }

    @Transactional(readOnly = true)
    public List<MarketSeasonVarietyProperty> findAllBy(Long marketSeasonId, String type, Long typeOfFlowersId, Long shippingPolicyId) {
        log.debug("Request to get  MarketSeasonVarietyProperty");

        String typeOfPriceLIst =  type.toUpperCase();
        return marketSeasonVarietyPropertyRepository
            .findAllBy(marketSeasonId, typeOfPriceLIst, typeOfFlowersId, shippingPolicyId);
    }


}
