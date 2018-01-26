package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.CargoEmployee;
import com.impltech.repository.CargoEmployeeRepositorySH;
import com.impltech.service.CargoEmployeeServiceSH;
import com.impltech.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CargoEmployeeResource REST controller.
 *
 * @see CargoEmployeeResourceSH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class CargoEmployeeResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SKYPE = "AAAAAAAAAA";
    private static final String UPDATED_SKYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LIST_OF_MARKETS = "AAAAAAAAAA";
    private static final String UPDATED_LIST_OF_MARKETS = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO_EMPLOYEES_POSITIONS = "AAAAAAAAAA";
    private static final String UPDATED_CARGO_EMPLOYEES_POSITIONS = "BBBBBBBBBB";

    @Autowired
    private CargoEmployeeRepositorySH cargoEmployeeRepository;

    @Autowired
    private CargoEmployeeServiceSH cargoEmployeeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCargoEmployeeMockMvc;

    private CargoEmployee cargoEmployee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CargoEmployeeResourceSH cargoEmployeeResource = new CargoEmployeeResourceSH(cargoEmployeeService);
        this.restCargoEmployeeMockMvc = MockMvcBuilders.standaloneSetup(cargoEmployeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CargoEmployee createEntity(EntityManager em) {
        CargoEmployee cargoEmployee = new CargoEmployee()
            .fullName(DEFAULT_FULL_NAME)
            .email(DEFAULT_EMAIL)
            .officePhone(DEFAULT_OFFICE_PHONE)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .skype(DEFAULT_SKYPE);
        return cargoEmployee;
    }

    @Before
    public void initTest() {
        cargoEmployee = createEntity(em);
    }

    @Test
    @Transactional
    public void createCargoEmployee() throws Exception {
        int databaseSizeBeforeCreate = cargoEmployeeRepository.findAll().size();

        // Create the CargoEmployee
        restCargoEmployeeMockMvc.perform(post("/api/cargo-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoEmployee)))
            .andExpect(status().isCreated());

        // Validate the CargoEmployee in the database
        List<CargoEmployee> cargoEmployeeList = cargoEmployeeRepository.findAll();
        assertThat(cargoEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        CargoEmployee testCargoEmployee = cargoEmployeeList.get(cargoEmployeeList.size() - 1);
        assertThat(testCargoEmployee.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testCargoEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCargoEmployee.getOfficePhone()).isEqualTo(DEFAULT_OFFICE_PHONE);
        assertThat(testCargoEmployee.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testCargoEmployee.getSkype()).isEqualTo(DEFAULT_SKYPE);
    }

    @Test
    @Transactional
    public void createCargoEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cargoEmployeeRepository.findAll().size();

        // Create the CargoEmployee with an existing ID
        cargoEmployee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCargoEmployeeMockMvc.perform(post("/api/cargo-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoEmployee)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CargoEmployee> cargoEmployeeList = cargoEmployeeRepository.findAll();
        assertThat(cargoEmployeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCargoEmployees() throws Exception {
        // Initialize the database
        cargoEmployeeRepository.saveAndFlush(cargoEmployee);

        // Get all the cargoEmployeeList
        restCargoEmployeeMockMvc.perform(get("/api/cargo-employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cargoEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].officePhone").value(hasItem(DEFAULT_OFFICE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].skype").value(hasItem(DEFAULT_SKYPE.toString())))
            .andExpect(jsonPath("$.[*].listOfMarkets").value(hasItem(DEFAULT_LIST_OF_MARKETS.toString())))
            .andExpect(jsonPath("$.[*].cargoEmployeesPositions").value(hasItem(DEFAULT_CARGO_EMPLOYEES_POSITIONS.toString())));
    }

    @Test
    @Transactional
    public void getCargoEmployee() throws Exception {
        // Initialize the database
        cargoEmployeeRepository.saveAndFlush(cargoEmployee);

        // Get the cargoEmployee
        restCargoEmployeeMockMvc.perform(get("/api/cargo-employees/{id}", cargoEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cargoEmployee.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.officePhone").value(DEFAULT_OFFICE_PHONE.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.skype").value(DEFAULT_SKYPE.toString()))
            .andExpect(jsonPath("$.listOfMarkets").value(DEFAULT_LIST_OF_MARKETS.toString()))
            .andExpect(jsonPath("$.cargoEmployeesPositions").value(DEFAULT_CARGO_EMPLOYEES_POSITIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCargoEmployee() throws Exception {
        // Get the cargoEmployee
        restCargoEmployeeMockMvc.perform(get("/api/cargo-employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCargoEmployee() throws Exception {
        // Initialize the database
        cargoEmployeeService.save(cargoEmployee);

        int databaseSizeBeforeUpdate = cargoEmployeeRepository.findAll().size();

        // Update the cargoEmployee
        CargoEmployee updatedCargoEmployee = cargoEmployeeRepository.findOne(cargoEmployee.getId());
        updatedCargoEmployee
            .fullName(UPDATED_FULL_NAME)
            .email(UPDATED_EMAIL)
            .officePhone(UPDATED_OFFICE_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .skype(UPDATED_SKYPE);

        restCargoEmployeeMockMvc.perform(put("/api/cargo-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCargoEmployee)))
            .andExpect(status().isOk());

        // Validate the CargoEmployee in the database
        List<CargoEmployee> cargoEmployeeList = cargoEmployeeRepository.findAll();
        assertThat(cargoEmployeeList).hasSize(databaseSizeBeforeUpdate);
        CargoEmployee testCargoEmployee = cargoEmployeeList.get(cargoEmployeeList.size() - 1);
        assertThat(testCargoEmployee.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testCargoEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCargoEmployee.getOfficePhone()).isEqualTo(UPDATED_OFFICE_PHONE);
        assertThat(testCargoEmployee.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testCargoEmployee.getSkype()).isEqualTo(UPDATED_SKYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCargoEmployee() throws Exception {
        int databaseSizeBeforeUpdate = cargoEmployeeRepository.findAll().size();

        // Create the CargoEmployee

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCargoEmployeeMockMvc.perform(put("/api/cargo-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoEmployee)))
            .andExpect(status().isCreated());

        // Validate the CargoEmployee in the database
        List<CargoEmployee> cargoEmployeeList = cargoEmployeeRepository.findAll();
        assertThat(cargoEmployeeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCargoEmployee() throws Exception {
        // Initialize the database
        cargoEmployeeService.save(cargoEmployee);

        int databaseSizeBeforeDelete = cargoEmployeeRepository.findAll().size();

        // Get the cargoEmployee
        restCargoEmployeeMockMvc.perform(delete("/api/cargo-employees/{id}", cargoEmployee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CargoEmployee> cargoEmployeeList = cargoEmployeeRepository.findAll();
        assertThat(cargoEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CargoEmployee.class);
        CargoEmployee cargoEmployee1 = new CargoEmployee();
        cargoEmployee1.setId(1L);
        CargoEmployee cargoEmployee2 = new CargoEmployee();
        cargoEmployee2.setId(cargoEmployee1.getId());
        assertThat(cargoEmployee1).isEqualTo(cargoEmployee2);
        cargoEmployee2.setId(2L);
        assertThat(cargoEmployee1).isNotEqualTo(cargoEmployee2);
        cargoEmployee1.setId(null);
        assertThat(cargoEmployee1).isNotEqualTo(cargoEmployee2);
    }
}
