package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.CargoEmployeePosition;
import com.impltech.service.CargoEmployeePositionServiceSH;
import com.impltech.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.impltech.validator.util.CargoEmployeePositionValidator;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * @author dima
 * REST controller for managing CargoEmployeePosition.
 */

@RestController
@RequestMapping("/api")
public class CargoEmployeePositionResourceSH {

    private final Logger log = LoggerFactory.getLogger(CargoEmployeePositionResourceSH.class);

    private static final String ENTITY_NAME = "cargoEmployeePosition";

    private final CargoEmployeePositionServiceSH cargoEmployeePositionService;

    private static final long CHECK_DUPLICATE = 1l;

    public CargoEmployeePositionResourceSH(CargoEmployeePositionServiceSH cargoEmployeePositionService) {
        this.cargoEmployeePositionService = cargoEmployeePositionService;
    }

    /**
     * POST  /cargo-employee-positions : Create a new cargoEmployeePosition.
     *
     * @param cargoEmployeePosition the cargoEmployeePosition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cargoEmployeePosition, or with status 400 (Bad Request) if the cargoEmployeePosition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PostMapping("/cargo-employee-positions")
    @Timed
    public ResponseEntity<CargoEmployeePosition> createCargoEmployeePosition(@RequestBody CargoEmployeePosition cargoEmployeePosition) throws URISyntaxException {
        log.debug("REST request to save CargoEmployeePosition : {}", cargoEmployeePosition);

        if (this.cargoEmployeePositionService.checkDuplicate(cargoEmployeePosition).equals(CHECK_DUPLICATE)) {
            return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (cargoEmployeePosition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cargoEmployeePosition cannot already have an ID")).body(null);
        }

        if (CargoEmployeePositionValidator.checkLengthValidation(cargoEmployeePosition)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }

        CargoEmployeePosition result = cargoEmployeePositionService.save(cargoEmployeePosition, false);
        return ResponseEntity.created(new URI("/api/cargo-employee-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cargo-employee-positions : Updates an existing cargoEmployeePosition.
     *
     * @param cargoEmployeePosition the cargoEmployeePosition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cargoEmployeePosition,
     * or with status 400 (Bad Request) if the cargoEmployeePosition is not valid,
     * or with status 500 (Internal Server Error) if the cargoEmployeePosition couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PutMapping("/cargo-employee-positions")
    @Timed
    public ResponseEntity<CargoEmployeePosition> updateCargoEmployeePosition(@RequestBody CargoEmployeePosition cargoEmployeePosition) throws URISyntaxException {
        log.debug("REST request to update CargoEmployeePosition : {}", cargoEmployeePosition);

        if (this.cargoEmployeePositionService.checkDuplicate(cargoEmployeePosition).equals(CHECK_DUPLICATE)) {
            return ResponseEntity.status(400).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "DuplicateName", "DuplicateName")).build();
        }

        if (cargoEmployeePosition.getId() == null) {
            return createCargoEmployeePosition(cargoEmployeePosition);
        }

        if (CargoEmployeePositionValidator.checkLengthValidation(cargoEmployeePosition)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }

        CargoEmployeePosition result = cargoEmployeePositionService.save(cargoEmployeePosition, true);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cargoEmployeePosition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cargo-employee-positions : get all the cargoEmployeePositions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cargoEmployeePositions in body
     */

    @GetMapping("/cargo-employee-positions")
    @Timed
    public ResponseEntity <List<CargoEmployeePosition>> getAllCargoEmployeePositions() {
        log.debug("REST request to get all CargoEmployeePositions");
        List<CargoEmployeePosition> cargoEmployeePositions = cargoEmployeePositionService.findAllByCurrentCompany();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cargoEmployeePositions));
    }

    /**
     * GET  /cargo-employee-positions/:id : get the "id" cargoEmployeePosition.
     *
     * @param id the id of the cargoEmployeePosition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cargoEmployeePosition, or with status 404 (Not Found)
     */

    @GetMapping("/cargo-employee-positions/{id}")
    @Timed
    public ResponseEntity<CargoEmployeePosition> getCargoEmployeePositionByCurrentCompany(@PathVariable Long id) {
        log.debug("REST request to get CargoEmployeePosition : {}", id);
        CargoEmployeePosition cargoEmployeePosition = cargoEmployeePositionService.findOneByCurrentCompany(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cargoEmployeePosition));
    }

    /**
     * DELETE  /cargo-employee-positions/:id : delete the "id" cargoEmployeePosition.
     *
     * @param id the id of the cargoEmployeePosition to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @DeleteMapping("/cargo-employee-positions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCargoEmployeePositionByCurrentCompany(@PathVariable Long id) {
        log.debug("REST request to delete CargoEmployeePosition : {}", id);
        cargoEmployeePositionService.deleteCargoEmployeePositionByCurrentCompany(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
