package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.CargoAgency;
import com.impltech.repository.CargoAgencyRepositorySH;
import com.impltech.service.CargoAgencyServiceSH;
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
 * Test class for the CargoAgencyResource REST controller.
 *
 * @see CargoAgencyResourceSH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class CargoAgencyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDITIONAL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_PAGE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LIST_OF_CARGO_EMPLOYEES = "AAAAAAAAAA";
    private static final String UPDATED_LIST_OF_CARGO_EMPLOYEES = "BBBBBBBBBB";

    @Autowired
    private CargoAgencyRepositorySH cargoAgencyRepository;

    @Autowired
    private CargoAgencyServiceSH cargoAgencyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCargoAgencyMockMvc;

    private CargoAgency cargoAgency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CargoAgencyResourceSH cargoAgencyResource = new CargoAgencyResourceSH(cargoAgencyService);
        this.restCargoAgencyMockMvc = MockMvcBuilders.standaloneSetup(cargoAgencyResource)
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
    public static CargoAgency createEntity(EntityManager em) {
        CargoAgency cargoAgency = new CargoAgency()
            .name(DEFAULT_NAME)
            .mainAddress(DEFAULT_MAIN_ADDRESS)
            .additionalAddress(DEFAULT_ADDITIONAL_ADDRESS)
            .officePhone(DEFAULT_PHONE)
            .webPage(DEFAULT_WEB_PAGE)
            .email(DEFAULT_EMAIL);

        return cargoAgency;
    }

    @Before
    public void initTest() {
        cargoAgency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCargoAgency() throws Exception {
        int databaseSizeBeforeCreate = cargoAgencyRepository.findAll().size();

        // Create the CargoAgency
        restCargoAgencyMockMvc.perform(post("/api/cargo-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoAgency)))
            .andExpect(status().isCreated());

        // Validate the CargoAgency in the database
        List<CargoAgency> cargoAgencyList = cargoAgencyRepository.findAll();
        assertThat(cargoAgencyList).hasSize(databaseSizeBeforeCreate + 1);
        CargoAgency testCargoAgency = cargoAgencyList.get(cargoAgencyList.size() - 1);
        assertThat(testCargoAgency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCargoAgency.getMainAddress()).isEqualTo(DEFAULT_MAIN_ADDRESS);
        assertThat(testCargoAgency.getAdditionalAddress()).isEqualTo(DEFAULT_ADDITIONAL_ADDRESS);
        assertThat(testCargoAgency.getOfficePhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCargoAgency.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCargoAgency.getWebPage()).isEqualTo(DEFAULT_WEB_PAGE);

    }

    @Test
    @Transactional
    public void createCargoAgencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cargoAgencyRepository.findAll().size();

        // Create the CargoAgency with an existing ID
        cargoAgency.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCargoAgencyMockMvc.perform(post("/api/cargo-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoAgency)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CargoAgency> cargoAgencyList = cargoAgencyRepository.findAll();
        assertThat(cargoAgencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCargoAgencies() throws Exception {
        // Initialize the database
        cargoAgencyRepository.saveAndFlush(cargoAgency);

        // Get all the cargoAgencyList
        restCargoAgencyMockMvc.perform(get("/api/cargo-agencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cargoAgency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].mainAddress").value(hasItem(DEFAULT_MAIN_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].additionalAddress").value(hasItem(DEFAULT_ADDITIONAL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].webPage").value(hasItem(DEFAULT_WEB_PAGE.toString())))
            .andExpect(jsonPath("$.[*].listOfCargoEmployees").value(hasItem(DEFAULT_LIST_OF_CARGO_EMPLOYEES.toString())));
    }

    @Test
    @Transactional
    public void getCargoAgency() throws Exception {
        // Initialize the database
        cargoAgencyRepository.saveAndFlush(cargoAgency);

        // Get the cargoAgency
        restCargoAgencyMockMvc.perform(get("/api/cargo-agencies/{id}", cargoAgency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cargoAgency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.mainAddress").value(DEFAULT_MAIN_ADDRESS.toString()))
            .andExpect(jsonPath("$.additionalAddress").value(DEFAULT_ADDITIONAL_ADDRESS.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.webPage").value(DEFAULT_WEB_PAGE.toString()))
            .andExpect(jsonPath("$.listOfCargoEmployees").value(DEFAULT_LIST_OF_CARGO_EMPLOYEES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCargoAgency() throws Exception {
        // Get the cargoAgency
        restCargoAgencyMockMvc.perform(get("/api/cargo-agencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCargoAgency() throws Exception {
        // Initialize the database
        cargoAgencyService.save(cargoAgency);

        int databaseSizeBeforeUpdate = cargoAgencyRepository.findAll().size();

        // Update the cargoAgency
        CargoAgency updatedCargoAgency = cargoAgencyRepository.findOne(cargoAgency.getId());
        updatedCargoAgency
            .name(UPDATED_NAME)
            .mainAddress(UPDATED_MAIN_ADDRESS)
            .additionalAddress(UPDATED_ADDITIONAL_ADDRESS)
            .officePhone(UPDATED_PHONE)
            .webPage(UPDATED_WEB_PAGE)
            .email(UPDATED_EMAIL);

        restCargoAgencyMockMvc.perform(put("/api/cargo-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCargoAgency)))
            .andExpect(status().isOk());

        // Validate the CargoAgency in the database
        List<CargoAgency> cargoAgencyList = cargoAgencyRepository.findAll();
        assertThat(cargoAgencyList).hasSize(databaseSizeBeforeUpdate);
        CargoAgency testCargoAgency = cargoAgencyList.get(cargoAgencyList.size() - 1);
        assertThat(testCargoAgency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCargoAgency.getMainAddress()).isEqualTo(UPDATED_MAIN_ADDRESS);
        assertThat(testCargoAgency.getAdditionalAddress()).isEqualTo(UPDATED_ADDITIONAL_ADDRESS);
        assertThat(testCargoAgency.getOfficePhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCargoAgency.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCargoAgency.getWebPage()).isEqualTo(UPDATED_WEB_PAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCargoAgency() throws Exception {
        int databaseSizeBeforeUpdate = cargoAgencyRepository.findAll().size();

        // Create the CargoAgency

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCargoAgencyMockMvc.perform(put("/api/cargo-agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoAgency)))
            .andExpect(status().isCreated());

        // Validate the CargoAgency in the database
        List<CargoAgency> cargoAgencyList = cargoAgencyRepository.findAll();
        assertThat(cargoAgencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCargoAgency() throws Exception {
        // Initialize the database
        cargoAgencyService.save(cargoAgency);

        int databaseSizeBeforeDelete = cargoAgencyRepository.findAll().size();

        // Get the cargoAgency
        restCargoAgencyMockMvc.perform(delete("/api/cargo-agencies/{id}", cargoAgency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CargoAgency> cargoAgencyList = cargoAgencyRepository.findAll();
        assertThat(cargoAgencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CargoAgency.class);
        CargoAgency cargoAgency1 = new CargoAgency();
        cargoAgency1.setId(1L);
        CargoAgency cargoAgency2 = new CargoAgency();
        cargoAgency2.setId(cargoAgency1.getId());
        assertThat(cargoAgency1).isEqualTo(cargoAgency2);
        cargoAgency2.setId(2L);
        assertThat(cargoAgency1).isNotEqualTo(cargoAgency2);
        cargoAgency1.setId(null);
        assertThat(cargoAgency1).isNotEqualTo(cargoAgency2);
    }
}
