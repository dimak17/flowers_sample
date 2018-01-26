package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.ShippingPolicy;
import com.impltech.service.ClaimsPolicyService;
import com.impltech.service.ShippingPolicyService;
import com.impltech.validator.util.PolicyValidator;
import com.impltech.web.rest.util.HeaderUtil;
import com.impltech.web.rest.util.ShippingPolicyUtils;
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
 * @author alex
 * REST controller for managing ShippingPolicy.
 */
@RestController
@RequestMapping("/api")
public class ShippingPolicyResource {

    private final Logger log = LoggerFactory.getLogger(ShippingPolicyResource.class);

    private static final String ENTITY_NAME = "shippingPolicy";
    private static final long one = 1l;
    private final ShippingPolicyService shippingPolicyService;
    private final ClaimsPolicyService claimsPolicyService;

    public ShippingPolicyResource(ShippingPolicyService shippingPolicyService,
                                  ClaimsPolicyService claimsPolicyService) {
        this.shippingPolicyService = shippingPolicyService;
        this.claimsPolicyService = claimsPolicyService;
    }

    /**
     * POST  /shipping-policies : Create a new shippingPolicy.
     *
     * @param shippingPolicy the shippingPolicy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shippingPolicy, or with status 400 (Bad Request) if the shippingPolicy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shipping-policies")
    @Timed
    public ResponseEntity<ShippingPolicy> createShippingPolicy(@RequestBody ShippingPolicy shippingPolicy) throws URISyntaxException {
        log.debug("REST request to save ShippingPolicy : {}", shippingPolicy);
        if (this.shippingPolicyService.checkDuplicate(shippingPolicy).equals(one)) {
            return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (shippingPolicy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shipping policy cannot already have an ID")).body(null);
        }

        if (PolicyValidator.checkLengthValidationShippingPolicy(shippingPolicy)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }
        ShippingPolicy result = shippingPolicyService.save(shippingPolicy);
        if (shippingPolicy != null) {
            claimsPolicyService.save(ShippingPolicyUtils.convertShippingToClaim(shippingPolicy));
        }
        return ResponseEntity.created(new URI("/api/shipping-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shipping-policies : Updates an existing shippingPolicy.
     *
     * @param shippingPolicy the shippingPolicy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shippingPolicy,
     * or with status 400 (Bad Request) if the shippingPolicy is not valid,
     * or with status 500 (Internal Server Error) if the shippingPolicy couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shipping-policies")
    @Timed
    public ResponseEntity<ShippingPolicy> updateShippingPolicy(@RequestBody ShippingPolicy shippingPolicy) throws URISyntaxException {
        log.debug("REST request to update ShippingPolicy : {}", shippingPolicy);
        if (this.shippingPolicyService.checkDuplicate(shippingPolicy).equals(one)) {
            return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (shippingPolicy.getId() == null) {
            return createShippingPolicy(shippingPolicy);
        }

        if (PolicyValidator.checkLengthValidationShippingPolicy(shippingPolicy)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }

        ShippingPolicy result = shippingPolicyService.save(shippingPolicy);
        if (shippingPolicy != null) {
            claimsPolicyService.save(ShippingPolicyUtils.convertShippingToClaim(shippingPolicy));
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shippingPolicy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shipping-policies : get all the shippingPolicies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shippingPolicies in body
     */
    @GetMapping("/shipping-policies/company")
    @Timed
    public ResponseEntity<List<ShippingPolicy>> getAllShippingPolicies() {
        log.debug("REST request to get a page of ShippingPolicies");
        List<ShippingPolicy> shippingPolicies = shippingPolicyService.findAllShippingPolicyByCurrentCompany();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shippingPolicies));
    }

    /**
     * GET  /shipping-policies/:id : get the "id" shippingPolicy.
     *
     * @param id the id of the shippingPolicy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shippingPolicy, or with status 404 (Not Found)
     */
    @GetMapping("/shipping-policies/{id}")
    @Timed
    public ResponseEntity<ShippingPolicy> getShippingPolicy(@PathVariable Long id) {
        log.debug("REST request to get ShippingPolicy : {}", id);
        ShippingPolicy shippingPolicy = shippingPolicyService.findOneByCompanyId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shippingPolicy));
    }

    /**
     * DELETE  /shipping-policies/:id : delete the "id" shippingPolicy.
     *
     * @param id the id of the shippingPolicy to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shipping-policies/{id}")
    @Timed
    public ResponseEntity<Void> deleteShippingPolicy(@PathVariable Long  id) {
        log.debug("REST request to delete ShippingPolicy : {}", id);
        shippingPolicyService.delete(id);
        claimsPolicyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
