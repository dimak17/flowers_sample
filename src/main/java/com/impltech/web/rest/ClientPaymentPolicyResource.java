package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.ClientPaymentPolicy;
import com.impltech.service.ClientPaymentPolicyService;
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
 * @author alex
 * REST controller for managing ClientPaymentPolicy.
 */
@RestController
@RequestMapping("/api")
public class ClientPaymentPolicyResource {

    private final Logger log = LoggerFactory.getLogger(ClientPaymentPolicyResource.class);

    private static final String ENTITY_NAME = "clientPaymentPolicy";

    private final ClientPaymentPolicyService clientPaymentPolicyService;

    public ClientPaymentPolicyResource(ClientPaymentPolicyService clientPaymentPolicyService) {
        this.clientPaymentPolicyService = clientPaymentPolicyService;
    }

    /**
     * POST  /client-payment-policies : Create a new clientPaymentPolicy.
     *
     * @param clientPaymentPolicy the clientPaymentPolicy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientPaymentPolicy, or with status 400 (Bad Request) if the clientPaymentPolicy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-payment-policies")
    @Timed
    public ResponseEntity<ClientPaymentPolicy> createClientPaymentPolicy(@RequestBody ClientPaymentPolicy clientPaymentPolicy) throws URISyntaxException {
        log.debug("REST request to save ClientPaymentPolicy : {}", clientPaymentPolicy);
        if (clientPaymentPolicy.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientPaymentPolicy cannot already have an ID")).body(null);
        }
        ClientPaymentPolicy result = clientPaymentPolicyService.save(clientPaymentPolicy);
        return ResponseEntity.created(new URI("/api/client-payment-policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-payment-policies : Updates an existing clientPaymentPolicy.
     *
     * @param clientPaymentPolicy the clientPaymentPolicy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientPaymentPolicy,
     * or with status 400 (Bad Request) if the clientPaymentPolicy is not valid,
     * or with status 500 (Internal Server Error) if the clientPaymentPolicy couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-payment-policies")
    @Timed
    public ResponseEntity<ClientPaymentPolicy> updateClientPaymentPolicy(@RequestBody ClientPaymentPolicy clientPaymentPolicy) throws URISyntaxException {
        log.debug("REST request to update ClientPaymentPolicy : {}", clientPaymentPolicy);
        if (clientPaymentPolicy.getId() == null) {
            return createClientPaymentPolicy(clientPaymentPolicy);
        }
        ClientPaymentPolicy result = clientPaymentPolicyService.save(clientPaymentPolicy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientPaymentPolicy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-payment-policies : get all the clientPaymentPolicies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clientPaymentPolicies in body
     */
    @GetMapping("/client-payment-policies")
    @Timed
    public List<ClientPaymentPolicy> getAllClientPaymentPolicies() {
        log.debug("REST request to get all ClientPaymentPolicies");
        return clientPaymentPolicyService.findAll();
    }

    /**
     * GET  /client-payment-policies/:id : get the "id" clientPaymentPolicy.
     *
     * @param id the id of the clientPaymentPolicy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientPaymentPolicy, or with status 404 (Not Found)
     */
    @GetMapping("/client-payment-policies/{id}")
    @Timed
    public ResponseEntity<ClientPaymentPolicy> getClientPaymentPolicy(@PathVariable Long id) {
        log.debug("REST request to get ClientPaymentPolicy : {}", id);
        ClientPaymentPolicy clientPaymentPolicy = clientPaymentPolicyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientPaymentPolicy));
    }

    /**
     * DELETE  /client-payment-policies/:id : delete the "id" clientPaymentPolicy.
     *
     * @param id the id of the clientPaymentPolicy to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-payment-policies/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientPaymentPolicy(@PathVariable Long id) {
        log.debug("REST request to delete ClientPaymentPolicy : {}", id);
        clientPaymentPolicyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
