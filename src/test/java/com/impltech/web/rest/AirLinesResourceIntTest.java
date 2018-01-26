package com.impltech.web.rest;

import com.impltech.FlowersApp;
import com.impltech.domain.AirLines;
import com.impltech.repository.AirLinesRepositorySH;
import com.impltech.service.AirLinesServiceSH;
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
 * Test class for the AirLinesResource REST controller.
 *
 * @see AirLinesResourceSH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class AirLinesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    @Autowired
    private AirLinesRepositorySH airLinesRepository;

    @Autowired
    private AirLinesServiceSH airLinesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAirLinesMockMvc;

    private AirLines airLines;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AirLinesResourceSH airLinesResource = new AirLinesResourceSH(airLinesService);
        this.restAirLinesMockMvc = MockMvcBuilders.standaloneSetup(airLinesResource)
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
    public static AirLines createEntity(EntityManager em) {
        AirLines airLines = new AirLines()
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER);
        return airLines;
    }

    @Before
    public void initTest() {
        airLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createAirLines() throws Exception {
        int databaseSizeBeforeCreate = airLinesRepository.findAll().size();

        // Create the AirLines
        restAirLinesMockMvc.perform(post("/api/air-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airLines)))
            .andExpect(status().isCreated());

        // Validate the AirLines in the database
        List<AirLines> airLinesList = airLinesRepository.findAll();
        assertThat(airLinesList).hasSize(databaseSizeBeforeCreate + 1);
        AirLines testAirLines = airLinesList.get(airLinesList.size() - 1);
        assertThat(testAirLines.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAirLines.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createAirLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = airLinesRepository.findAll().size();

        // Create the AirLines with an existing ID
        airLines.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirLinesMockMvc.perform(post("/api/air-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airLines)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AirLines> airLinesList = airLinesRepository.findAll();
        assertThat(airLinesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAirLines() throws Exception {
        // Initialize the database
        airLinesRepository.saveAndFlush(airLines);

        // Get all the airLinesList
        restAirLinesMockMvc.perform(get("/api/air-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }

    @Test
    @Transactional
    public void getAirLines() throws Exception {
        // Initialize the database
        airLinesRepository.saveAndFlush(airLines);

        // Get the airLines
        restAirLinesMockMvc.perform(get("/api/air-lines/{id}", airLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(airLines.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingAirLines() throws Exception {
        // Get the airLines
        restAirLinesMockMvc.perform(get("/api/air-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAirLines() throws Exception {
        // Initialize the database
        airLinesService.save(airLines);

        int databaseSizeBeforeUpdate = airLinesRepository.findAll().size();

        // Update the airLines
        AirLines updatedAirLines = airLinesRepository.findOne(airLines.getId());
        updatedAirLines
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER);

        restAirLinesMockMvc.perform(put("/api/air-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAirLines)))
            .andExpect(status().isOk());

        // Validate the AirLines in the database
        List<AirLines> airLinesList = airLinesRepository.findAll();
        assertThat(airLinesList).hasSize(databaseSizeBeforeUpdate);
        AirLines testAirLines = airLinesList.get(airLinesList.size() - 1);
        assertThat(testAirLines.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAirLines.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingAirLines() throws Exception {
        int databaseSizeBeforeUpdate = airLinesRepository.findAll().size();

        // Create the AirLines

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAirLinesMockMvc.perform(put("/api/air-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(airLines)))
            .andExpect(status().isCreated());

        // Validate the AirLines in the database
        List<AirLines> airLinesList = airLinesRepository.findAll();
        assertThat(airLinesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAirLines() throws Exception {
        // Initialize the database
        airLinesService.save(airLines);

        int databaseSizeBeforeDelete = airLinesRepository.findAll().size();

        // Get the airLines
        restAirLinesMockMvc.perform(delete("/api/air-lines/{id}", airLines.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AirLines> airLinesList = airLinesRepository.findAll();
        assertThat(airLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AirLines.class);
        AirLines airLines1 = new AirLines();
        airLines1.setId(1L);
        AirLines airLines2 = new AirLines();
        airLines2.setId(airLines1.getId());
        assertThat(airLines1).isEqualTo(airLines2);
        airLines2.setId(2L);
        assertThat(airLines1).isNotEqualTo(airLines2);
        airLines1.setId(null);
        assertThat(airLines1).isNotEqualTo(airLines2);
    }
}
