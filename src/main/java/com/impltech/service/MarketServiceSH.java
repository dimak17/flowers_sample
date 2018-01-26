package com.impltech.service;

import com.impltech.domain.Company;
import com.impltech.domain.Market;
import com.impltech.domain.MarketVariety;
import com.impltech.repository.MarketBoxRepository;
import com.impltech.repository.MarketRepositorySH;
import com.impltech.repository.MarketVarietyRepositorySH;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author platon
 * Service Implementation for managing Market.
 */
@Service
@Transactional
public class MarketServiceSH {
    private final Logger log = LoggerFactory.getLogger(MarketService.class);

    private final MarketRepositorySH marketRepository;
    private final MarketVarietyRepositorySH marketVarietyRepository;
    private final MarketBoxRepository marketBoxRepository;

    public MarketServiceSH(MarketRepositorySH marketRepository, MarketVarietyRepositorySH marketVarietyRepository, MarketBoxRepository marketBoxRepository) {
        this.marketRepository = marketRepository;
        this.marketVarietyRepository = marketVarietyRepository;
        this.marketBoxRepository = marketBoxRepository;
    }

    /**
     * Save a market.
     *
     * @param market the entity to save
     * @return the persisted entity
     */
    public Market save(Market market) {
        log.debug("Request to save Market : {}", market);
        Market marketCreated = null;
        Long marketId = market.getId();
        Company company = SecurityUtils.getCurrentCompanyUser().getCompany();
        Long companyId = company.getId();
        if(marketId == null || marketId == 0) {
            market.setCompany(company);
            marketCreated = saveMarketInternal(market);
        } else if (marketRepository.checkMarketByCompany(marketId, companyId).longValue() > 0) {
            marketCreated = saveMarketInternal(market);
        }
        return marketCreated;
    }

    private Market saveMarketInternal(Market market) {
        Market marketCreated;
        market.getMarketBoxes().forEach(mb -> mb.setMarket(market));
        marketVarietyRepository.deleteByMarketId(market.getId());

        Set<MarketVariety> marketVarieties = market.getMarketVarieties();
        marketVarieties.forEach(mv -> mv.setMarket(market));
        market.setMarketVarieties(null);

        marketCreated = marketRepository.save(market);
        marketVarietyRepository.save(marketVarieties);
        return marketCreated;
    }

    /**
     *  Get all the markets.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Market> findAll() {
        log.debug("Request to get all Markets");
        return marketRepository.findAllByCompanyId(
            SecurityUtils.getCurrentCompanyUser().getCompany().getId()
        );
    }

    /**
     *  Get one market by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Market findOne(Long id) {
        log.debug("Request to get Market : {}", id);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return marketRepository.findOneWithEagerRelationships(id, companyId);
    }

    /**
     *  Delete the  market by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Market : {}", id);
        marketRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<Market> findAllByCurrentCompany() {
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        log.debug("Request to get all Markets by Current Company Id");
        return marketRepository.findAllByCurrentCompanyId(companyId);
    }

    @Transactional(readOnly = true)
    public boolean isNameAlreadyExist(Market market) {
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return marketRepository.isNameAlreadyExists(
            Optional.ofNullable(market.getId()).orElse(0L),
            market.getName(),
            companyId
        ).longValue() < 1;
    }
}
