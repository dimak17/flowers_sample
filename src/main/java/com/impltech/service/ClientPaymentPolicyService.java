package com.impltech.service;

import com.impltech.domain.ClientPaymentPolicy;
import com.impltech.repository.ClientPaymentPolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dima
 * Service Implementation for managing ClientPaymentPolicy.
 */
@Service
@Transactional
public class ClientPaymentPolicyService {

    private final Logger log = LoggerFactory.getLogger(ClientPaymentPolicyService.class);

    private final ClientPaymentPolicyRepository clientPaymentPolicyRepository;

    public ClientPaymentPolicyService(ClientPaymentPolicyRepository clientPaymentPolicyRepository) {
        this.clientPaymentPolicyRepository = clientPaymentPolicyRepository;
    }

    /**
     * Save a clientPaymentPolicy.
     *
     * @param clientPaymentPolicy the entity to save
     * @return the persisted entity
     */
    public ClientPaymentPolicy save(ClientPaymentPolicy clientPaymentPolicy) {
        log.debug("Request to save ClientPaymentPolicy : {}", clientPaymentPolicy);
        return clientPaymentPolicyRepository.save(clientPaymentPolicy);
    }

    /**
     *  Get all the clientPaymentPolicies.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ClientPaymentPolicy> findAll() {
        log.debug("Request to get all ClientPaymentPolicies");
        return clientPaymentPolicyRepository.findAll();
    }

    /**
     *  Get one clientPaymentPolicy by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ClientPaymentPolicy findOne(Long id) {
        log.debug("Request to get ClientPaymentPolicy : {}", id);
        return clientPaymentPolicyRepository.findOne(id);
    }

    /**
     *  Delete the  clientPaymentPolicy by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientPaymentPolicy : {}", id);
        clientPaymentPolicyRepository.delete(id);
    }
}
