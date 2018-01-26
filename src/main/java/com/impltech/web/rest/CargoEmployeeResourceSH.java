 package com.impltech.web.rest;

 import com.codahale.metrics.annotation.Timed;
 import com.impltech.domain.CargoEmployee;
 import com.impltech.service.CargoEmployeeServiceSH;
 import com.impltech.validator.util.CargoEmployeeValidator;
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
 * REST controller for managing CargoEmployee.
 */

@RestController
@RequestMapping("/api")
public class CargoEmployeeResourceSH {

    private final Logger log = LoggerFactory.getLogger(CargoEmployeeResourceSH.class);

    private static final String ENTITY_NAME = "cargoEmployee";

    private final CargoEmployeeServiceSH cargoEmployeeService;

    public CargoEmployeeResourceSH(CargoEmployeeServiceSH cargoEmployeeService) {
        this.cargoEmployeeService = cargoEmployeeService;
    }

    /**
     * POST  /cargo-employees : Create a new cargoEmployee.
     *
     * @param cargoEmployee the cargoEmployee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cargoEmployee, or with status 400 (Bad Request) if the cargoEmployee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PostMapping("/cargo-employees")
    @Timed
    public ResponseEntity<CargoEmployee> createCargoEmployee(@RequestBody CargoEmployee cargoEmployee) throws URISyntaxException {
        log.debug("REST request to save CargoEmployee : {}", cargoEmployee);

        if (cargoEmployee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cargoEmployee cannot already have an ID")).body(null);
        }

        if (CargoEmployeeValidator.checkLengthValidation(cargoEmployee)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }

        CargoEmployee result = cargoEmployeeService.save(cargoEmployee);
        return ResponseEntity.created(new URI("/api/cargo-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cargo-employees : Updates an existing cargoEmployee.
     *
     * @param cargoEmployee the cargoEmployee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cargoEmployee,
     * or with status 400 (Bad Request) if the cargoEmployee is not valid,
     * or with status 500 (Internal Server Error) if the cargoEmployee couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PutMapping("/cargo-employees")
    @Timed
    public ResponseEntity<CargoEmployee> updateCargoEmployee(@RequestBody CargoEmployee cargoEmployee) throws URISyntaxException {
        log.debug("REST request to update CargoEmployee : {}", cargoEmployee);

        if (cargoEmployee.getId() == null) {
            return createCargoEmployee(cargoEmployee);
        }

        if (CargoEmployeeValidator.checkLengthValidation(cargoEmployee)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "validationFailure", "Validation Failure")).body(null);
        }

        CargoEmployee result = cargoEmployeeService.save(cargoEmployee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cargoEmployee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cargo-employees : get all the cargoEmployees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cargoEmployees in body
     */

    @GetMapping("/cargo-employees")
    @Timed
    public ResponseEntity<List<CargoEmployee>> getAllCargoEmployees() {
        log.debug("REST request to get all CargoEmployees");
        List<CargoEmployee> cargoEmployees = cargoEmployeeService.findAllByCompanyId();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cargoEmployees));
    }

    /**
     * GET  /cargo-employees/:id : get the "id" cargoEmployee.
     *
     * @param cargoEmployeeId the id of the cargoAgecy to retrieve
     * @param cargoAgencyId the id of the cargoEmployee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cargoEmployee, or with status 404 (Not Found)
     */

    @GetMapping("/cargo-employees/{cargoEmployeeId}/cargo-agency/{cargoAgencyId}")
    @Timed
    public ResponseEntity<CargoEmployee> getCargoEmployee(@PathVariable Long cargoEmployeeId, @PathVariable Long cargoAgencyId) {
        log.debug("REST request to get CargoEmployee : {}", cargoEmployeeId);
        CargoEmployee cargoEmployee = cargoEmployeeService.findOneByCargoEmployeeIdAndCargoAgencyId(cargoEmployeeId, cargoAgencyId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cargoEmployee));
    }

    /**
     * DELETE  /cargo-employees/:id : delete the "id" cargoEmployee.
     *
     * @param cargoEmployeeId the id of the cargoEmployee to delete
     * @return the ResponseEntity with status 200 (OK)
     */

    @DeleteMapping("/cargo-employees/{cargoEmployeeId}/cargo-agency/{cargoAgencyId}")
    @Timed
    public ResponseEntity<Void> deleteCargoEmployee(@PathVariable Long  cargoEmployeeId, @PathVariable Long  cargoAgencyId) {
        log.debug("REST request to delete CargoEmployee : {}", cargoEmployeeId);
        cargoEmployeeService.deleteCargoEmployeeByCurrentCompanyIdAndCargoAgencyId(cargoEmployeeId, cargoAgencyId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, cargoEmployeeId.toString())).build();
    }
}
