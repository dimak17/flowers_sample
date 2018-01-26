package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.ClaimsPolicy;
import com.impltech.service.ClaimsPolicyService;
import com.impltech.service.ShippingPolicyService;
import com.impltech.validator.util.PolicyValidator;
import com.impltech.web.rest.util.ClaimsPolicyUtils;
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
 * @author dima
 * REST controller for managing ClaimsPolicy.
 */
@RestController
@RequestMapping("/api")
public class ClaimsPolicyResource {

    private final Logger log = LoggerFactory.getLogger(ClaimsPolicyResource.class);

    private static final String ENTITY_NAME = "claimsPolicy";
    private static final long one = 1l;
    private final ClaimsPolicyService claimsPolicyService;
    private final ShippingPolicyService shippingPolicyService;

    public ClaimsPolicyResource(ClaimsPolicyService claimsPolicyService,
                                ShippingPolicyService shippingPolicyService) {
        this.claimsPolicyService = claimsPolicyService;
        this.shippingPolicyService = shippingPolicyService;
    }

    /**
     * POST  /claims-policies : Create a new claimsPolicy.
     *
     * @param claimsPolicy the claimsPolicy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new claimsPolicy, or with status 400 (Bad Request) if the claimsPolicy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/claims-policies")
    @Timed
    public ResponseEntity<ClaimsPolicy> createClaimsPolicy(@RequestBody ClaimsPolicy claimsPolicy) throws URISyntaxException {
        log.debug("REST request to save ClaimsPolicy : {}", claimsPolicy);
        if (this.claimsPolicyService.checkDuplicate(claimsPolicy).equals(one)) {
                return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (claimsPolicy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new claims policy cannot already have an ID")).body(null);
        }

        if (PolicyValidator.checkLengthValidationClaimsPolicy(claimsPolicy)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }

        ClaimsPolicy result = claimsPolicyService.save(claimsPolicy);
        if(claimsPolicy != null) {
            shippingPolicyService.save(ClaimsPolicyUtils.convertClaimToShipping(claimsPolicy));
        }
        return ResponseEntity.created(new URI("/api/claims-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /claims-policies : Updates an existing claimsPolicy.
     *
     * @param claimsPolicy the claimsPolicy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated claimsPolicy,
     * or with status 400 (Bad Request) if the claimsPolicy is not valid,
     * or with status 500 (Internal Server Error) if the claimsPolicy couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/claims-policies")
    @Timed
    public ResponseEntity<ClaimsPolicy> updateClaimsPolicy(@RequestBody ClaimsPolicy claimsPolicy) throws URISyntaxException {
        log.debug("REST request to update ClaimsPolicy : {}", claimsPolicy);
        if (this.claimsPolicyService.checkDuplicate(claimsPolicy).equals(one)) {
            return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (claimsPolicy.getId() == null) {
            return createClaimsPolicy(claimsPolicy);
        }

        if (PolicyValidator.checkLengthValidationClaimsPolicy(claimsPolicy)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }

        ClaimsPolicy result = claimsPolicyService.save(claimsPolicy);
        if(claimsPolicy !=null) {
                shippingPolicyService.save(ClaimsPolicyUtils.convertClaimToShipping(claimsPolicy));
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, claimsPolicy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /claims-policies : get all the claimsPolicies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of claimsPolicies in body
     */
    @GetMapping("/claims-policies/company")
    @Timed
    public ResponseEntity<List<ClaimsPolicy>> getAllClaimsPolicies() {
        log.debug("REST request to get a page of Claims Policies");
        List<ClaimsPolicy> claimsPolicies = claimsPolicyService.findAllClaimsPolicyByCurrentCompany();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(claimsPolicies));
    }

    /**
     * GET  /claims-policies/:id : get the "id" claimsPolicy.
     *
     * @param id the id of the claimsPolicy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the claimsPolicy, or with status 404 (Not Found)
     */
    @GetMapping("/claims-policies/{id}")
    @Timed
    public ResponseEntity<ClaimsPolicy> getClaimsPolicy(@PathVariable Long id) {
        log.debug("REST request to get ClaimsPolicy : {}", id);
        ClaimsPolicy claimsPolicy = claimsPolicyService.findOneByCurrentCompany(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(claimsPolicy));
    }

    /**
     * DELETE  /claims-policies/:id : delete the "id" claimsPolicy.
     *
     * @param id the id of the claimsPolicy to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/claims-policies/{id}")
    @Timed
    public ResponseEntity<Void> deleteClaimsPolicy(@PathVariable  Long id) {
        log.debug("REST request to delete ClaimsPolicy : {}", id);
        claimsPolicyService.delete(id);
        shippingPolicyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
