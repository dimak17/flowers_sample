package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.ClientEmployee;

import com.impltech.repository.ClientEmployeeRepository;
import com.impltech.web.rest.util.HeaderUtil;
import com.impltech.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * @author platon
 * REST controller for managing ClientEmployee.
 */
@RestController
@RequestMapping("/api")
public class ClientEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(ClientEmployeeResource.class);

    private static final String ENTITY_NAME = "clientEmployee";

    private final ClientEmployeeRepository clientEmployeeRepository;

    public ClientEmployeeResource(ClientEmployeeRepository clientEmployeeRepository) {
        this.clientEmployeeRepository = clientEmployeeRepository;
    }

    /**
     * POST  /client-employees : Create a new clientEmployee.
     *
     * @param clientEmployee the clientEmployee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientEmployee, or with status 400 (Bad Request) if the clientEmployee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-employees")
    @Timed
    public ResponseEntity<ClientEmployee> createClientEmployee(@RequestBody ClientEmployee clientEmployee) throws URISyntaxException {
        log.debug("REST request to save ClientEmployee : {}", clientEmployee);
        if (clientEmployee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new clientEmployee cannot already have an ID")).body(null);
        }
        ClientEmployee result = clientEmployeeRepository.save(clientEmployee);
        return ResponseEntity.created(new URI("/api/client-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-employees : Updates an existing clientEmployee.
     *
     * @param clientEmployee the clientEmployee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientEmployee,
     * or with status 400 (Bad Request) if the clientEmployee is not valid,
     * or with status 500 (Internal Server Error) if the clientEmployee couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-employees")
    @Timed
    public ResponseEntity<ClientEmployee> updateClientEmployee(@RequestBody ClientEmployee clientEmployee) throws URISyntaxException {
        log.debug("REST request to update ClientEmployee : {}", clientEmployee);
        if (clientEmployee.getId() == null) {
            return createClientEmployee(clientEmployee);
        }
        ClientEmployee result = clientEmployeeRepository.save(clientEmployee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientEmployee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-employees : get all the clientEmployees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientEmployees in body
     */
    @GetMapping("/client-employees")
    @Timed
    public ResponseEntity<List<ClientEmployee>> getAllClientEmployees(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ClientEmployees");
        Page<ClientEmployee> page = clientEmployeeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-employees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-employees/:id : get the "id" clientEmployee.
     *
     * @param id the id of the clientEmployee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientEmployee, or with status 404 (Not Found)
     */
    @GetMapping("/client-employees/{id}")
    @Timed
    public ResponseEntity<ClientEmployee> getClientEmployee(@PathVariable Long id) {
        log.debug("REST request to get ClientEmployee : {}", id);
        ClientEmployee clientEmployee = clientEmployeeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clientEmployee));
    }

    /**
     * DELETE  /client-employees/:id : delete the "id" clientEmployee.
     *
     * @param id the id of the clientEmployee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-employees/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientEmployee(@PathVariable Long id) {
        log.debug("REST request to delete ClientEmployee : {}", id);
        clientEmployeeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
