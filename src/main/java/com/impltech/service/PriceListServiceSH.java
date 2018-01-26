package com.impltech.service;

import com.impltech.domain.PriceList;
import com.impltech.repository.PriceListRepositorySH;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author alex
 * Service Implementation for managing PriceList.
 */
@Service
@Transactional
public class PriceListServiceSH {

    private final Logger log = LoggerFactory.getLogger(PriceListServiceSH.class);

    private final PriceListRepositorySH priceListRepository;

    public PriceListServiceSH(PriceListRepositorySH priceListRepository) {
        this.priceListRepository = priceListRepository;
    }

    /**
     * Save a priceList.
     *
     * @param priceList the entity to save
     * @return the persisted entity
     */
    public PriceList save(PriceList priceList) {
        log.debug("Request to save PriceList : {}", priceList);
        return priceListRepository.save(priceList);
    }

    /**
     *  Get all the priceLists.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PriceList> findAll() {
        log.debug("Request to get all PriceLists");
        return priceListRepository.findAll();
    }

    /**
     *  Get one priceList by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PriceList findOne(Long id) {
        log.debug("Request to get PriceList : {}", id);
        return priceListRepository.findOne(id);
    }

    /**
     *  Get one priceList by type.
     *
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PriceList findPriceListByPriceListTyeAndCurrentCompanyId(String type) {
        Long  id = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("Request to get PriceList : {}", type);
        if(type.equalsIgnoreCase("season")) {
            return priceListRepository.findByPriceListTypeAndCurrentCompanyId(id, type.toUpperCase());
        }
        return priceListRepository.findPriceListByPriceListTyeAndCurrentCompanyId(id ,type.toUpperCase());
    }

    /**
     *  Delete the  priceList by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PriceList : {}", id);
        priceListRepository.delete(id);
    }
}
