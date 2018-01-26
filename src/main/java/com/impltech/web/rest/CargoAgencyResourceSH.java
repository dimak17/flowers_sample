package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.CargoAgency;
import com.impltech.service.CargoAgencyServiceSH;
import com.impltech.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.impltech.validator.util.CargoAgencyValidator;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * @author dima
 * REST controller for managing CargoAgency.
 */

@RestController
@RequestMapping("/api")
public class CargoAgencyResourceSH {

    private final Logger log = LoggerFactory.getLogger(CargoAgencyResourceSH.class);

    private static final String ENTITY_NAME = "cargoAgency";

    private final CargoAgencyServiceSH cargoAgencyService;

    private static final long CHECK_DUPLICATE = 1l;

    public CargoAgencyResourceSH(CargoAgencyServiceSH cargoAgencyService) {
        this.cargoAgencyService = cargoAgencyService;
    }

    /**
     * POST  /cargo-agencies : Create a new cargoAgency.
     *
     * @param cargoAgency the cargoAgency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cargoAgency, or with status 400 (Bad Request) if the cargoAgency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PostMapping("/cargo-agencies")
    @Timed
    public ResponseEntity<CargoAgency> createCargoAgency(@RequestBody CargoAgency cargoAgency) throws URISyntaxException {
        log.debug("REST request to save CargoAgency : {}", cargoAgency);

        if (this.cargoAgencyService.checkDuplicate(cargoAgency).equals(CHECK_DUPLICATE)) {
            return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (cargoAgency.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cargoAgency cannot already have an ID")).body(null);
        }

        if (CargoAgencyValidator.checkLengthValidation(cargoAgency)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }

        CargoAgency result = cargoAgencyService.save(cargoAgency);
        return ResponseEntity.created(new URI("/api/cargo-agencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cargo-agencies : Updates an existing cargoAgency.
     *
     * @param cargoAgency the cargoAgency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cargoAgency,
     * or with status 400 (Bad Request) if the cargoAgency is not valid,
     * or with status 500 (Internal Server Error) if the cargoAgency couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PutMapping("/cargo-agencies")
    @Timed
    public ResponseEntity<CargoAgency> updateCargoAgency(@RequestBody CargoAgency cargoAgency) throws URISyntaxException {
        log.debug("REST request to update CargoAgency : {}", cargoAgency);

        if (this.cargoAgencyService.checkDuplicate(cargoAgency).equals(CHECK_DUPLICATE)) {
            return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (cargoAgency.getId() == null) {
            return createCargoAgency(cargoAgency);
        }

        if (CargoAgencyValidator.checkLengthValidation(cargoAgency)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }

        CargoAgency result = cargoAgencyService.save(cargoAgency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cargoAgency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cargo-agencies : get all the cargoAgencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cargoAgencies in body
     */

    @GetMapping("/cargo-agencies")
    @Timed
    public ResponseEntity <List<CargoAgency>> getAllCargoAgencies() {
        log.debug("REST request to get all CargoAgencies");
        List<CargoAgency> cargoAgencies = cargoAgencyService.findAllByCurrentCompany();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cargoAgencies));
    }

    /**
     * GET  /cargo-agencies/:id : get the "id" cargoAgency.
     *
     * @param id the id of the cargoAgency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cargoAgency, or with status 404 (Not Found)
     */

    @GetMapping("/cargo-agencies/{id}")
    @Timed
    public ResponseEntity<CargoAgency> getCargoAgency(@PathVariable Long id) {
        log.debug("REST request to get CargoAgency : {}", id);
        CargoAgency cargoAgency = cargoAgencyService.findOneByCompanyId(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cargoAgency));
    }

    /**
     * DELETE  /cargo-agencies/:id : delete the "id" cargoAgency.
     *
     * @param id the id of the cargoAgency to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @DeleteMapping("/cargo-agencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteCargoAgencyByCurrentCompany(@PathVariable Long id) {
        log.debug("REST request to delete CargoAgency : {}", id);
        cargoAgencyService.deleteByCurrentCompanyId(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
