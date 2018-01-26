package com.impltech.service;

import com.impltech.domain.ClaimsPolicy;
import com.impltech.repository.ClaimsPolicyRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dima
 * Service Implementation for managing ClaimsPolicy.
 */
@Service
@Transactional
public class ClaimsPolicyService {

    private final Logger log = LoggerFactory.getLogger(ClaimsPolicyService.class);

    private final ClaimsPolicyRepository claimsPolicyRepository;

    public ClaimsPolicyService(ClaimsPolicyRepository claimsPolicyRepository) {
        this.claimsPolicyRepository = claimsPolicyRepository;
    }

    /**
     * Save a claimsPolicy.
     *
     * @param claimsPolicy the entity to save
     * @return the persisted entity
     */
    public ClaimsPolicy save(ClaimsPolicy claimsPolicy) {
        log.debug("Request to save ClaimsPolicy : {}", claimsPolicy);
        claimsPolicy.setCompany(SecurityUtils.getCurrentCompanyUser().getCompany());
        return claimsPolicyRepository.save(claimsPolicy);
    }

    /**
     *  Get all the claimsPolicies.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ClaimsPolicy> findAllClaimsPolicyByCurrentCompany() {
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return claimsPolicyRepository.findAllByCompanyId(idCompany);

    }

    @Transactional(readOnly = true)
    public Long checkDuplicate(ClaimsPolicy claimsPolicy){
        Long result;
        String name = claimsPolicy.getShortName();
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        if (claimsPolicy.getId() == null){
            result =  claimsPolicyRepository.checkDuplicateIfCreateClaimsPolicy(name,idCompany);
        } else {
            Long id = claimsPolicy.getId();
            result = claimsPolicyRepository.checkDuplicateIfUpdateClaimsPolicy(id, name, idCompany);
        }
        return result;
    }

    /**
     *  Get one claimsPolicy by id.
     *
     *  @param companyId the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ClaimsPolicy findOneByCompanyId(Long companyId) {
        log.debug("Request to get ClaimsPolicy : {}", companyId);
        return claimsPolicyRepository.findOne(companyId);
    }


    @Transactional(readOnly = true)
    public ClaimsPolicy findOneByCurrentCompany(Long id) {
        log.debug("Request to get ClaimsPolicy : {} by current company", id);
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return claimsPolicyRepository.findOneByCompanyId(id, companyId);
    }
    /**
     *  Delete the  claimsPolicy by companyId.
     *
     *  @param id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClaimsPolicy : {}", id);
        Long idCompany = SecurityUtils.getCurrentCompanyUser().getId();
        claimsPolicyRepository.deleteClaimsPolicyByCompanyId(idCompany, id);
    }
}
