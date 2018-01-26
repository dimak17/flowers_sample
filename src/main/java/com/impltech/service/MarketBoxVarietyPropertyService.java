package com.impltech.service;

import com.impltech.domain.MarketBox;
import com.impltech.domain.MarketBoxVarietyProperty;
import com.impltech.repository.MarketBoxVarietyPropertyRepository;
import com.impltech.security.SecurityUtils;
import com.impltech.service.dto.MarketBoxVarietyPropertyDTO;
import com.impltech.service.util.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @alex
 * Service Implementation for managing MarketBoxVarietyProperty.
 */
@Service
@Transactional
public class MarketBoxVarietyPropertyService {

    private final Logger log = LoggerFactory.getLogger(MarketBoxVarietyPropertyService.class);

    private final MarketBoxVarietyPropertyRepository marketBoxVarietyPropertyRepository;

    public MarketBoxVarietyPropertyService(MarketBoxVarietyPropertyRepository marketBoxVarietyPropertyRepository) {
        this.marketBoxVarietyPropertyRepository = marketBoxVarietyPropertyRepository;
    }

    /**
     * Save a marketBoxVarietyProperty.
     *
     * @param marketBoxVarietyProperty the entity to save
     * @return the persisted entity
     */
    public MarketBoxVarietyProperty save(MarketBoxVarietyProperty marketBoxVarietyProperty) {
        log.debug("Request to save MarketBoxVarietyProperty : {}", marketBoxVarietyProperty);
        return marketBoxVarietyPropertyRepository.save(marketBoxVarietyProperty);
    }


    /**
     *  Get all the marketBoxVarietyProperties.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MarketBoxVarietyProperty> findAll() {
        log.debug("Request to get all MarketBoxVarietyProperties");
        return marketBoxVarietyPropertyRepository.findAll();
    }


    /**
     *  Get all the marketBoxVarietyProperties.
     *
     *  @return the list of entities by marketbox
     */
    @Transactional(readOnly = true)
    public List<MarketBoxVarietyPropertyDTO> findAllByMarketBox(Long marketBoxId) {
        log.debug("Request to get all MarketBoxVarietyProperties");
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        List<MarketBoxVarietyProperty> marketBoxVarietyProperties = marketBoxVarietyPropertyRepository
            .findAllByCompanyAndMarketBox(companyId, marketBoxId);
        return ConvertUtil.marketBoxVarietyPropertiesToDTO(marketBoxVarietyProperties);
    }

    /**
     *  Get all the marketBoxVarietyProperties.
     *
     *  @return the list of entities by market
     */
    @Transactional(readOnly = true)
    public List<MarketBoxVarietyPropertyDTO> findAllByMarket(Long marketId) {
        log.debug("Request to get all MarketBoxVarietyProperties");
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        List<MarketBoxVarietyProperty> marketBoxVarietyProperties = marketBoxVarietyPropertyRepository
            .findAllByCompanyAndMarket(companyId, marketId);
        Map<MarketBox, List<MarketBoxVarietyProperty>> marketBoxListMap = marketBoxVarietyProperties
            .stream()
            .collect(groupingBy(MarketBoxVarietyProperty::getMarketBox));
        return marketBoxListMap.keySet()
            .stream()
            .flatMap(k -> ConvertUtil.marketBoxVarietyPropertiesToDTO(marketBoxListMap.get(k)).stream().flatMap(Stream::of))
            .collect(toList());
    }

    /**
     *  Get one marketBoxVarietyProperty by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MarketBoxVarietyProperty findOne(Long id) {
        log.debug("Request to get MarketBoxVarietyProperty : {}", id);
        return marketBoxVarietyPropertyRepository.findOne(id);
    }

    /**
     *  Delete the  marketBoxVarietyProperty by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MarketBoxVarietyProperty : {}", id);
        marketBoxVarietyPropertyRepository.delete(id);
    }

    /**
     * Save a marketBoxVarietyProperty.
     *
     * @param properties the entity to save
     * @return the persisted entity
     */
    public void save(List<MarketBoxVarietyPropertyDTO> properties) {
        log.debug("Request to save MarketBoxVarietyProperty : {}", properties);
        Long id = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        if(properties.size() > 0) {
            Long marketBoxId = properties.get(0).getMarketBox().getId();
            List<MarketBoxVarietyProperty> list = ConvertUtil.dtoToMarketBoxVarietyProperties(properties);
            List<MarketBoxVarietyProperty> propsToRemove = list.stream()
                .filter(p -> p.getId() != null && p.getId() != 0 && p.getCapacity() == 0).collect(toList());
            if (propsToRemove.size() > 0) {
                marketBoxVarietyPropertyRepository.delete(propsToRemove);
            }
            List<MarketBoxVarietyProperty> propsToSave = list.stream()
                .filter(p -> (p.getId() == null || p.getId() == 0 && p.getCapacity() != 0)
                    || (p.getId() != null && p.getId() != 0 && p.getCapacity() != 0))
                .collect(toList());
            List<MarketBoxVarietyProperty> propsToUpdate = propsToSave.stream()
                .filter(p -> p.getId() != 0 && p.getId() != null)
                .collect(toList());
            List<MarketBoxVarietyProperty> currentProps = marketBoxVarietyPropertyRepository
                .findAllByCompanyAndMarketBox(id, marketBoxId);
            List<Long> currentIds = currentProps.stream()
                .map(MarketBoxVarietyProperty::getId)
                .collect(toList());
            boolean idsValid = propsToUpdate.stream().allMatch(p -> currentIds.contains(p.getId()));
            if (idsValid && propsToSave.size() > 0) {
                propsToSave.forEach(p -> p.getMarketBox().setMarket(properties.get(0).getMarket()));
                marketBoxVarietyPropertyRepository.save(propsToSave);
            }
        }
    }

}
