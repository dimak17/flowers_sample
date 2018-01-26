package com.impltech.service;

import com.impltech.domain.ShippingPolicy;
import com.impltech.repository.ShippingPolicyRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author alex
 * Service Implementation for managing ShippingPolicy.
 */
@Service
@Transactional
public class ShippingPolicyService {

    private final Logger log = LoggerFactory.getLogger(ShippingPolicyService.class);

    private final ShippingPolicyRepository shippingPolicyRepository;

    public ShippingPolicyService(ShippingPolicyRepository shippingPolicyRepository) {
        this.shippingPolicyRepository = shippingPolicyRepository;
    }

    /**
     * Save a shippingPolicy.
     *
     * @param shippingPolicy the entity to save
     * @return the persisted entity
     */
    public ShippingPolicy save(ShippingPolicy shippingPolicy) {
        log.debug("Request to save ShippingPolicy : {}", shippingPolicy);
        shippingPolicy.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        return shippingPolicyRepository.save(shippingPolicy);
    }

    /**
     *  Get all the shippingPolicies.
     *  @return the list of entities
     */
   @Transactional(readOnly = true)
    public List<ShippingPolicy> findAllShippingPolicyByCurrentCompany() {
    Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
    return shippingPolicyRepository.findAllByCompanyId(idCompany);

   }
    @Transactional(readOnly = true)
    public Long checkDuplicate(ShippingPolicy shippingPolicy){
        Long result;
        String name = shippingPolicy.getShortName();
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        if (shippingPolicy.getId() == null){
            result =  shippingPolicyRepository.checkDuplicateIfCreateShippingPolicy(name,idCompany);
        } else {
            Long id = shippingPolicy.getId();
            result = shippingPolicyRepository.checkDuplicateIfUpdateShippingPolicy(id, name, idCompany);
        }
        return result;
    }


    @Transactional(readOnly = true)
    public ShippingPolicy findOneByCompanyId(Long id) {
        log.debug("Request to get ShippingPolicy : {} by current company", id);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return shippingPolicyRepository.findOneByCompanyId(id, companyId);
    }

    /**
     *  Get one shippingPolicy by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ShippingPolicy findOne(Long id) {
        log.debug("Request to get ShippingPolicy : {}", id);
        return shippingPolicyRepository.findOne(id);
    }

    /**
     *  Delete the  shippingPolicy by companyId.
     *
     *  @param id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ShippingPolicy : {}", id);
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getId();
        shippingPolicyRepository.deleteShippingPolicyByCompanyId(idCompany, id);
    }
}
