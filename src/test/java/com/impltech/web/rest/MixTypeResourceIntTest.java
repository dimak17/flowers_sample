package com.impltech.web.rest;

import com.impltech.FlowersApp;
import com.impltech.domain.MixType;
import com.impltech.repository.MixTypeRepositorySH;
import com.impltech.service.MixTypeServiceSH;
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
 * Test class for the MixTypeResource REST controller.
 *
 * @see MixTypeResourceSH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class MixTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MixTypeRepositorySH mixTypeRepository;

    @Autowired
    private MixTypeServiceSH mixTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMixTypeMockMvc;

    private MixType mixType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MixTypeResourceSH mixTypeResource = new MixTypeResourceSH(mixTypeService);
        this.restMixTypeMockMvc = MockMvcBuilders.standaloneSetup(mixTypeResource)
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
    public static MixType createEntity(EntityManager em) {
        MixType mixType = new MixType()
            .name(DEFAULT_NAME);
        return mixType;
    }

    @Before
    public void initTest() {
        mixType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMixType() throws Exception {
        int databaseSizeBeforeCreate = mixTypeRepository.findAll().size();

        // Create the MixType
        restMixTypeMockMvc.perform(post("/api/mix-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mixType)))
            .andExpect(status().isCreated());

        // Validate the MixType in the database
        List<MixType> mixTypeList = mixTypeRepository.findAll();
        assertThat(mixTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MixType testMixType = mixTypeList.get(mixTypeList.size() - 1);
        assertThat(testMixType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMixTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mixTypeRepository.findAll().size();

        // Create the MixType with an existing ID
        mixType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMixTypeMockMvc.perform(post("/api/mix-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mixType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MixType> mixTypeList = mixTypeRepository.findAll();
        assertThat(mixTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMixTypes() throws Exception {
        // Initialize the database
        mixTypeRepository.saveAndFlush(mixType);

        // Get all the mixTypeList
        restMixTypeMockMvc.perform(get("/api/mix-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mixType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMixType() throws Exception {
        // Initialize the database
        mixTypeRepository.saveAndFlush(mixType);

        // Get the mixType
        restMixTypeMockMvc.perform(get("/api/mix-types/{id}", mixType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mixType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMixType() throws Exception {
        // Get the mixType
        restMixTypeMockMvc.perform(get("/api/mix-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMixType() throws Exception {
        // Initialize the database
        mixTypeService.save(mixType);

        int databaseSizeBeforeUpdate = mixTypeRepository.findAll().size();

        // Update the mixType
        MixType updatedMixType = mixTypeRepository.findOne(mixType.getId());
        updatedMixType
            .name(UPDATED_NAME);

        restMixTypeMockMvc.perform(put("/api/mix-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMixType)))
            .andExpect(status().isOk());

        // Validate the MixType in the database
        List<MixType> mixTypeList = mixTypeRepository.findAll();
        assertThat(mixTypeList).hasSize(databaseSizeBeforeUpdate);
        MixType testMixType = mixTypeList.get(mixTypeList.size() - 1);
        assertThat(testMixType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMixType() throws Exception {
        int databaseSizeBeforeUpdate = mixTypeRepository.findAll().size();

        // Create the MixType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMixTypeMockMvc.perform(put("/api/mix-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mixType)))
            .andExpect(status().isCreated());

        // Validate the MixType in the database
        List<MixType> mixTypeList = mixTypeRepository.findAll();
        assertThat(mixTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMixType() throws Exception {
        // Initialize the database
        mixTypeService.save(mixType);

        int databaseSizeBeforeDelete = mixTypeRepository.findAll().size();

        // Get the mixType
        restMixTypeMockMvc.perform(delete("/api/mix-types/{id}", mixType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MixType> mixTypeList = mixTypeRepository.findAll();
        assertThat(mixTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MixType.class);
        MixType mixType1 = new MixType();
        mixType1.setId(1L);
        MixType mixType2 = new MixType();
        mixType2.setId(mixType1.getId());
        assertThat(mixType1).isEqualTo(mixType2);
        mixType2.setId(2L);
        assertThat(mixType1).isNotEqualTo(mixType2);
        mixType1.setId(null);
        assertThat(mixType1).isNotEqualTo(mixType2);
    }
}
