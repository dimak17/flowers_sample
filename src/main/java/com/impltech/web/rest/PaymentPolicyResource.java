package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.PaymentPolicy;
import com.impltech.service.PaymentPolicyService;
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
 * @author platon
 * REST controller for managing PaymentPolicy.
 */
@RestController
@RequestMapping("/api")
public class PaymentPolicyResource {

    private final Logger log = LoggerFactory.getLogger(PaymentPolicyResource.class);

    private static final String ENTITY_NAME = "paymentPolicy";

    private final PaymentPolicyService paymentPolicyService;

    public PaymentPolicyResource(PaymentPolicyService paymentPolicyService) {
        this.paymentPolicyService = paymentPolicyService;
    }

    /**
     * POST  /payment-policies : Create a new paymentPolicy.
     *
     * @param paymentPolicy the paymentPolicy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentPolicy, or with status 400 (Bad Request) if the paymentPolicy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-policies")
    @Timed
    public ResponseEntity<PaymentPolicy> createPaymentPolicy(@RequestBody PaymentPolicy paymentPolicy) throws URISyntaxException {
        log.debug("REST request to save PaymentPolicy : {}", paymentPolicy);
        if (paymentPolicy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new PaymentPolicy cannot already have an ID")).body(null);
        }
        PaymentPolicy result = paymentPolicyService.save(paymentPolicy);
        return ResponseEntity.created(new URI("/api/payment-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-policies : Updates an existing paymentPolicy.
     *
     * @param paymentPolicy the paymentPolicy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentPolicy,
     * or with status 400 (Bad Request) if the paymentPolicy is not valid,
     * or with status 500 (Internal Server Error) if the paymentPolicy couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-policies")
    @Timed
    public ResponseEntity<PaymentPolicy> updatePaymentPolicy(@RequestBody PaymentPolicy paymentPolicy) throws URISyntaxException {
        log.debug("REST request to update PaymentPolicy : {}", paymentPolicy);
        if (paymentPolicy.getId() == null) {
            return createPaymentPolicy(paymentPolicy);
        }
        PaymentPolicy result = paymentPolicyService.save(paymentPolicy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentPolicy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-policies : get all the paymentPolicies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paymentPolicies in body
     */
    @GetMapping("/payment-policies")
    @Timed
    public List<PaymentPolicy> getAllPaymentPolicies() {
        log.debug("REST request to get all PaymentPolicies");
        return paymentPolicyService.findAllByCompanyId();
    }

    /**
     * GET  /payment-policies/:id : get the "id" paymentPolicy.
     *
     * @param id the id of the paymentPolicy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentPolicy, or with status 404 (Not Found)
     */
    @GetMapping("/payment-policies/{id}")
    @Timed
    public ResponseEntity<PaymentPolicy> getPaymentPolicy(@PathVariable Long id) {
        log.debug("REST request to get PaymentPolicy : {}", id);
        PaymentPolicy paymentPolicy = paymentPolicyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paymentPolicy));
    }
}
