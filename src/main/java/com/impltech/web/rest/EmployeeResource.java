package com.impltech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.impltech.domain.Position;
import com.impltech.domain.User;
import com.impltech.repository.UserRepository;
import com.impltech.service.EmployeeService;
import com.impltech.service.dto.EmployeeDTO;
import com.impltech.web.rest.util.HeaderUtil;
import com.impltech.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
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
 * Created by platon
 * REST controller for managing Employees.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {


    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "employee";

    private final EmployeeService employeeService;

    private final UserRepository userRepository;

    public EmployeeResource(EmployeeService employeeService, UserRepository userRepository) {
        this.employeeService = employeeService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /employees : Create a new employee.
     *
     * @param employeeDTO the employee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employee, or with status 400 (Bad Request) if we have the employee with such email
     */
    @PostMapping("/employees")
    @Timed
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employeeDTO);
        Optional<User> userByEmail = userRepository.findOneByEmail(employeeDTO.getEmail());
        if(userByEmail.isPresent()) {
            return ResponseEntity
                .badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "A new employee cannot has an email that already exist")).body(null);
        }
        EmployeeDTO result = employeeService.save(employeeDTO);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);

    }

    /**
     * PUT  /employees : Updates an existing companyUser.
     *
     * @param employeeDTO the Employee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated Employee,
     * or with status 400 (Bad Request) if the companyUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the companyUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employees")
    @Timed
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        //TODO validation
        log.debug("REST request to update Employee : {}", employeeDTO);
        if (employeeDTO.getId() == null) {
            return createEmployee(employeeDTO);
        }
        EmployeeDTO result = employeeService.update(employeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employees : get all the companyUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of Employee in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/employees")
    @Timed
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Employees");
        Page<EmployeeDTO> page = employeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employees/positions : get all the Positions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of positions in body
     */
    @GetMapping("/employees/positions")
    @Timed
    public ResponseEntity<List<Position>> getAllPositions() {
        log.debug("REST request to get a page of Positions");
        List<Position> data = employeeService.findAllPositions();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * GET  /employees/:id : get the "id" companyUser.
     *
     * @param id the id of the companyUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the Employee, or with status 404 (Not Found)
     */
    @GetMapping("/employees/{id}")
    @Timed
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        EmployeeDTO employeeDTO = employeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeDTO));
    }

    /**
     * DELETE  /employees/:id : delete the "id" companyUser.
     *
     * @param id the id of the companyUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employees/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
