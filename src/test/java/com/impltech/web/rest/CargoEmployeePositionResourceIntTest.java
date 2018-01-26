package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.CargoEmployeePosition;
import com.impltech.repository.CargoEmployeePositionRepositorySH;
import com.impltech.service.CargoEmployeePositionServiceSH;
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
 * Test class for the CargoEmployeePositionResource REST controller.
 *
 * @see CargoEmployeePositionResourceSH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class CargoEmployeePositionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CargoEmployeePositionRepositorySH cargoEmployeePositionRepository;

    @Autowired
    private CargoEmployeePositionServiceSH cargoEmployeePositionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCargoEmployeePositionMockMvc;

    private CargoEmployeePosition cargoEmployeePosition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CargoEmployeePositionResourceSH cargoEmployeePositionResource = new CargoEmployeePositionResourceSH(cargoEmployeePositionService);
        this.restCargoEmployeePositionMockMvc = MockMvcBuilders.standaloneSetup(cargoEmployeePositionResource)
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
    public static CargoEmployeePosition createEntity(EntityManager em) {
        CargoEmployeePosition cargoEmployeePosition = new CargoEmployeePosition()
            .name(DEFAULT_NAME);
        return cargoEmployeePosition;
    }

    @Before
    public void initTest() {
        cargoEmployeePosition = createEntity(em);
    }

    @Test
    @Transactional
    public void createCargoEmployeePosition() throws Exception {
        int databaseSizeBeforeCreate = cargoEmployeePositionRepository.findAll().size();

        // Create the CargoEmployeePosition
        restCargoEmployeePositionMockMvc.perform(post("/api/cargo-employee-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoEmployeePosition)))
            .andExpect(status().isCreated());

        // Validate the CargoEmployeePosition in the database
        List<CargoEmployeePosition> cargoEmployeePositionList = cargoEmployeePositionRepository.findAll();
        assertThat(cargoEmployeePositionList).hasSize(databaseSizeBeforeCreate + 1);
        CargoEmployeePosition testCargoEmployeePosition = cargoEmployeePositionList.get(cargoEmployeePositionList.size() - 1);
        assertThat(testCargoEmployeePosition.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCargoEmployeePositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cargoEmployeePositionRepository.findAll().size();

        // Create the CargoEmployeePosition with an existing ID
        cargoEmployeePosition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCargoEmployeePositionMockMvc.perform(post("/api/cargo-employee-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoEmployeePosition)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CargoEmployeePosition> cargoEmployeePositionList = cargoEmployeePositionRepository.findAll();
        assertThat(cargoEmployeePositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCargoEmployeePositions() throws Exception {
        // Initialize the database
        cargoEmployeePositionRepository.saveAndFlush(cargoEmployeePosition);

        // Get all the cargoEmployeePositionList
        restCargoEmployeePositionMockMvc.perform(get("/api/cargo-employee-positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cargoEmployeePosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCargoEmployeePosition() throws Exception {
        // Initialize the database
        cargoEmployeePositionRepository.saveAndFlush(cargoEmployeePosition);

        // Get the cargoEmployeePosition
        restCargoEmployeePositionMockMvc.perform(get("/api/cargo-employee-positions/{id}", cargoEmployeePosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cargoEmployeePosition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCargoEmployeePosition() throws Exception {
        // Get the cargoEmployeePosition
        restCargoEmployeePositionMockMvc.perform(get("/api/cargo-employee-positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCargoEmployeePosition() throws Exception {
        // Initialize the database
        cargoEmployeePositionService.save(cargoEmployeePosition, true);

        int databaseSizeBeforeUpdate = cargoEmployeePositionRepository.findAll().size();

        // Update the cargoEmployeePosition
        CargoEmployeePosition updatedCargoEmployeePosition = cargoEmployeePositionRepository.findOne(cargoEmployeePosition.getId());
        updatedCargoEmployeePosition
            .name(UPDATED_NAME);

        restCargoEmployeePositionMockMvc.perform(put("/api/cargo-employee-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCargoEmployeePosition)))
            .andExpect(status().isOk());

        // Validate the CargoEmployeePosition in the database
        List<CargoEmployeePosition> cargoEmployeePositionList = cargoEmployeePositionRepository.findAll();
        assertThat(cargoEmployeePositionList).hasSize(databaseSizeBeforeUpdate);
        CargoEmployeePosition testCargoEmployeePosition = cargoEmployeePositionList.get(cargoEmployeePositionList.size() - 1);
        assertThat(testCargoEmployeePosition.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCargoEmployeePosition() throws Exception {
        int databaseSizeBeforeUpdate = cargoEmployeePositionRepository.findAll().size();

        // Create the CargoEmployeePosition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCargoEmployeePositionMockMvc.perform(put("/api/cargo-employee-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoEmployeePosition)))
            .andExpect(status().isCreated());

        // Validate the CargoEmployeePosition in the database
        List<CargoEmployeePosition> cargoEmployeePositionList = cargoEmployeePositionRepository.findAll();
        assertThat(cargoEmployeePositionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCargoEmployeePosition() throws Exception {
        // Initialize the database
        cargoEmployeePositionService.save(cargoEmployeePosition, true);

        int databaseSizeBeforeDelete = cargoEmployeePositionRepository.findAll().size();

        // Get the cargoEmployeePosition
        restCargoEmployeePositionMockMvc.perform(delete("/api/cargo-employee-positions/{id}", cargoEmployeePosition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CargoEmployeePosition> cargoEmployeePositionList = cargoEmployeePositionRepository.findAll();
        assertThat(cargoEmployeePositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CargoEmployeePosition.class);
        CargoEmployeePosition cargoEmployeePosition1 = new CargoEmployeePosition();
        cargoEmployeePosition1.setId(1L);
        CargoEmployeePosition cargoEmployeePosition2 = new CargoEmployeePosition();
        cargoEmployeePosition2.setId(cargoEmployeePosition1.getId());
        assertThat(cargoEmployeePosition1).isEqualTo(cargoEmployeePosition2);
        cargoEmployeePosition2.setId(2L);
        assertThat(cargoEmployeePosition1).isNotEqualTo(cargoEmployeePosition2);
        cargoEmployeePosition1.setId(null);
        assertThat(cargoEmployeePosition1).isNotEqualTo(cargoEmployeePosition2);
    }
}
