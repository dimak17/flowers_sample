package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.Variety;
import com.impltech.repository.VarietyRepository;
import com.impltech.service.VarietyService;
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
 * Test class for the VarietyResource REST controller.
 *
 * @see VarietyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class VarietyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_BREEDER = "AAAAAAAAAA";
    private static final String UPDATED_BREEDER = "BBBBBBBBBB";

    private static final Integer DEFAULT_MIN_LENGTH = 1;
    private static final Integer UPDATED_MIN_LENGTH = 2;

    private static final Integer DEFAULT_MAX_LENGTH = 1;
    private static final Integer UPDATED_MAX_LENGTH = 2;

    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private VarietyService varietyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVarietyMockMvc;

    private Variety variety;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VarietyResource varietyResource = new VarietyResource(varietyService);
        this.restVarietyMockMvc = MockMvcBuilders.standaloneSetup(varietyResource)
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
    public static Variety createEntity(EntityManager em) {
        Variety variety = new Variety()
            .name(DEFAULT_NAME)
            .color(DEFAULT_COLOR)
            .breeder(DEFAULT_BREEDER)
            .minLength(DEFAULT_MIN_LENGTH)
            .maxLength(DEFAULT_MAX_LENGTH);
        return variety;
    }

    @Before
    public void initTest() {
        variety = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariety() throws Exception {
        int databaseSizeBeforeCreate = varietyRepository.findAll().size();

        // Create the Variety
        restVarietyMockMvc.perform(post("/api/varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variety)))
            .andExpect(status().isCreated());

        // Validate the Variety in the database
        List<Variety> varietyList = varietyRepository.findAll();
        assertThat(varietyList).hasSize(databaseSizeBeforeCreate + 1);
        Variety testVariety = varietyList.get(varietyList.size() - 1);
        assertThat(testVariety.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVariety.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testVariety.getBreeder()).isEqualTo(DEFAULT_BREEDER);
        assertThat(testVariety.getMinLength()).isEqualTo(DEFAULT_MIN_LENGTH);
        assertThat(testVariety.getMaxLength()).isEqualTo(DEFAULT_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void createVarietyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = varietyRepository.findAll().size();

        // Create the Variety with an existing ID
        variety.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVarietyMockMvc.perform(post("/api/varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variety)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Variety> varietyList = varietyRepository.findAll();
        assertThat(varietyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVarieties() throws Exception {
        // Initialize the database
        varietyRepository.saveAndFlush(variety);

        // Get all the varietyList
        restVarietyMockMvc.perform(get("/api/varieties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variety.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].breeder").value(hasItem(DEFAULT_BREEDER.toString())))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)));
    }

    @Test
    @Transactional
    public void getVariety() throws Exception {
        // Initialize the database
        varietyRepository.saveAndFlush(variety);

        // Get the variety
        restVarietyMockMvc.perform(get("/api/varieties/{id}", variety.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(variety.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.breeder").value(DEFAULT_BREEDER.toString()))
            .andExpect(jsonPath("$.minLength").value(DEFAULT_MIN_LENGTH))
            .andExpect(jsonPath("$.maxLength").value(DEFAULT_MAX_LENGTH));
    }

    @Test
    @Transactional
    public void getNonExistingVariety() throws Exception {
        // Get the variety
        restVarietyMockMvc.perform(get("/api/varieties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariety() throws Exception {
        // Initialize the database
        varietyService.save(variety);

        int databaseSizeBeforeUpdate = varietyRepository.findAll().size();

        // Update the variety
        Variety updatedVariety = varietyRepository.findOne(variety.getId());
        updatedVariety
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR)
            .breeder(UPDATED_BREEDER)
            .minLength(UPDATED_MIN_LENGTH)
            .maxLength(UPDATED_MAX_LENGTH);

        restVarietyMockMvc.perform(put("/api/varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVariety)))
            .andExpect(status().isOk());

        // Validate the Variety in the database
        List<Variety> varietyList = varietyRepository.findAll();
        assertThat(varietyList).hasSize(databaseSizeBeforeUpdate);
        Variety testVariety = varietyList.get(varietyList.size() - 1);
        assertThat(testVariety.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVariety.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVariety.getBreeder()).isEqualTo(UPDATED_BREEDER);
        assertThat(testVariety.getMinLength()).isEqualTo(UPDATED_MIN_LENGTH);
        assertThat(testVariety.getMaxLength()).isEqualTo(UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void updateNonExistingVariety() throws Exception {
        int databaseSizeBeforeUpdate = varietyRepository.findAll().size();

        // Create the Variety

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVarietyMockMvc.perform(put("/api/varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variety)))
            .andExpect(status().isCreated());

        // Validate the Variety in the database
        List<Variety> varietyList = varietyRepository.findAll();
        assertThat(varietyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVariety() throws Exception {
        // Initialize the database
        varietyService.save(variety);

        int databaseSizeBeforeDelete = varietyRepository.findAll().size();

        // Get the variety
        restVarietyMockMvc.perform(delete("/api/varieties/{id}", variety.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Variety> varietyList = varietyRepository.findAll();
        assertThat(varietyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Variety.class);
        Variety variety1 = new Variety();
        variety1.setId(1L);
        Variety variety2 = new Variety();
        variety2.setId(variety1.getId());
        assertThat(variety1).isEqualTo(variety2);
        variety2.setId(2L);
        assertThat(variety1).isNotEqualTo(variety2);
        variety1.setId(null);
        assertThat(variety1).isNotEqualTo(variety2);
    }
}
