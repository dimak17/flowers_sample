package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.LabelCountry;
import com.impltech.repository.LabelCountryRepository;
import com.impltech.service.LabelCountryService;
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
 * Test class for the LabelCountryResource REST controller.
 *
 * @see LabelCountryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class LabelCountryResourceIntTest {

    @Autowired
    private LabelCountryRepository labelCountryRepository;

    @Autowired
    private LabelCountryService labelCountryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLabelCountryMockMvc;

    private LabelCountry labelCountry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LabelCountryResource labelCountryResource = new LabelCountryResource(labelCountryService);
        this.restLabelCountryMockMvc = MockMvcBuilders.standaloneSetup(labelCountryResource)
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
    public static LabelCountry createEntity(EntityManager em) {
        LabelCountry labelCountry = new LabelCountry();
        return labelCountry;
    }

    @Before
    public void initTest() {
        labelCountry = createEntity(em);
    }

    @Test
    @Transactional
    public void createLabelCountry() throws Exception {
        int databaseSizeBeforeCreate = labelCountryRepository.findAll().size();

        // Create the LabelCountry
        restLabelCountryMockMvc.perform(post("/api/label-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(labelCountry)))
            .andExpect(status().isCreated());

        // Validate the LabelCountry in the database
        List<LabelCountry> labelCountryList = labelCountryRepository.findAll();
        assertThat(labelCountryList).hasSize(databaseSizeBeforeCreate + 1);
        LabelCountry testLabelCountry = labelCountryList.get(labelCountryList.size() - 1);
    }

    @Test
    @Transactional
    public void createLabelCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = labelCountryRepository.findAll().size();

        // Create the LabelCountry with an existing ID
        labelCountry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLabelCountryMockMvc.perform(post("/api/label-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(labelCountry)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LabelCountry> labelCountryList = labelCountryRepository.findAll();
        assertThat(labelCountryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLabelCountries() throws Exception {
        // Initialize the database
        labelCountryRepository.saveAndFlush(labelCountry);

        // Get all the labelCountryList
        restLabelCountryMockMvc.perform(get("/api/label-countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(labelCountry.getId().intValue())));
    }

    @Test
    @Transactional
    public void getLabelCountry() throws Exception {
        // Initialize the database
        labelCountryRepository.saveAndFlush(labelCountry);

        // Get the labelCountry
        restLabelCountryMockMvc.perform(get("/api/label-countries/{id}", labelCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(labelCountry.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLabelCountry() throws Exception {
        // Get the labelCountry
        restLabelCountryMockMvc.perform(get("/api/label-countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLabelCountry() throws Exception {
        // Initialize the database
        labelCountryService.save(labelCountry);

        int databaseSizeBeforeUpdate = labelCountryRepository.findAll().size();

        // Update the labelCountry
        LabelCountry updatedLabelCountry = labelCountryRepository.findOne(labelCountry.getId());

        restLabelCountryMockMvc.perform(put("/api/label-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLabelCountry)))
            .andExpect(status().isOk());

        // Validate the LabelCountry in the database
        List<LabelCountry> labelCountryList = labelCountryRepository.findAll();
        assertThat(labelCountryList).hasSize(databaseSizeBeforeUpdate);
        LabelCountry testLabelCountry = labelCountryList.get(labelCountryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingLabelCountry() throws Exception {
        int databaseSizeBeforeUpdate = labelCountryRepository.findAll().size();

        // Create the LabelCountry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLabelCountryMockMvc.perform(put("/api/label-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(labelCountry)))
            .andExpect(status().isCreated());

        // Validate the LabelCountry in the database
        List<LabelCountry> labelCountryList = labelCountryRepository.findAll();
        assertThat(labelCountryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLabelCountry() throws Exception {
        // Initialize the database
        labelCountryService.save(labelCountry);

        int databaseSizeBeforeDelete = labelCountryRepository.findAll().size();

        // Get the labelCountry
        restLabelCountryMockMvc.perform(delete("/api/label-countries/{id}", labelCountry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LabelCountry> labelCountryList = labelCountryRepository.findAll();
        assertThat(labelCountryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LabelCountry.class);
        LabelCountry labelCountry1 = new LabelCountry();
        labelCountry1.setId(1L);
        LabelCountry labelCountry2 = new LabelCountry();
        labelCountry2.setId(labelCountry1.getId());
        assertThat(labelCountry1).isEqualTo(labelCountry2);
        labelCountry2.setId(2L);
        assertThat(labelCountry1).isNotEqualTo(labelCountry2);
        labelCountry1.setId(null);
        assertThat(labelCountry1).isNotEqualTo(labelCountry2);
    }
}
