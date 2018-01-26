package com.impltech.service;

import com.impltech.domain.PaymentPolicy;
import com.impltech.repository.PaymentPolicyRepository;
import com.impltech.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author platon
 * Service Implementation for managing PaymentPolicy.
 */
@Service
@Transactional
public class PaymentPolicyService {

    private final Logger log = LoggerFactory.getLogger(PaymentPolicyService.class);

    private final PaymentPolicyRepository paymentPolicyRepository;

    public PaymentPolicyService(PaymentPolicyRepository paymentPolicyRepository) {
        this.paymentPolicyRepository = paymentPolicyRepository;
    }

    /**
     * Save a paymentPolicy.
     *
     * @param paymentPolicy the entity to save
     * @return the persisted entity
     */
    public PaymentPolicy save(PaymentPolicy paymentPolicy) {
        log.debug("Request to save PaymentPolicy : {}", paymentPolicy);
        return paymentPolicyRepository.save(paymentPolicy);
    }

    /**
     *  Get all the paymentPolicies.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PaymentPolicy> findAllByCompanyId() {
        log.debug("Request to get all PaymentPolicies");
        Long companyId = SecurityUtils.getCurrentCompanyUser().getCompany().getId();
        return paymentPolicyRepository.findAllByComapnyId(companyId);
    }

    /**
     *  Get one paymentPolicy by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PaymentPolicy findOne(Long id) {
        log.debug("Request to get PaymentPolicy : {}", id);
        return paymentPolicyRepository.findOne(id);
    }
}
