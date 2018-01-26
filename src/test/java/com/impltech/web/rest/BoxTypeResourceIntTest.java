package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.BoxType;
import com.impltech.repository.BoxTypeRepository;
import com.impltech.service.BoxTypeService;
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
 * Test class for the BoxTypeResource REST controller.
 *
 * @see BoxTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class BoxTypeResourceIntTest {

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BOX_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_BOX_SIZE = "BBBBBBBBBB";

    @Autowired
    private BoxTypeRepository boxTypeRepository;

    @Autowired
    private BoxTypeService boxTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBoxTypeMockMvc;

    private BoxType boxType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BoxTypeResource boxTypeResource = new BoxTypeResource(boxTypeService);
        this.restBoxTypeMockMvc = MockMvcBuilders.standaloneSetup(boxTypeResource)
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
    public static BoxType createEntity(EntityManager em) {
        BoxType boxType = new BoxType()
            .shortName(DEFAULT_SHORT_NAME)
            .fullName(DEFAULT_FULL_NAME)
            .boxSize(DEFAULT_BOX_SIZE);
        return boxType;
    }

    @Before
    public void initTest() {
        boxType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoxType() throws Exception {
        int databaseSizeBeforeCreate = boxTypeRepository.findAll().size();

        // Create the BoxType
        restBoxTypeMockMvc.perform(post("/api/box-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxType)))
            .andExpect(status().isCreated());

        // Validate the BoxType in the database
        List<BoxType> boxTypeList = boxTypeRepository.findAll();
        assertThat(boxTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BoxType testBoxType = boxTypeList.get(boxTypeList.size() - 1);
        assertThat(testBoxType.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testBoxType.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testBoxType.getBoxSize()).isEqualTo(DEFAULT_BOX_SIZE);
    }

    @Test
    @Transactional
    public void createBoxTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boxTypeRepository.findAll().size();

        // Create the BoxType with an existing ID
        boxType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoxTypeMockMvc.perform(post("/api/box-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BoxType> boxTypeList = boxTypeRepository.findAll();
        assertThat(boxTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBoxTypes() throws Exception {
        // Initialize the database
        boxTypeRepository.saveAndFlush(boxType);

        // Get all the boxTypeList
        restBoxTypeMockMvc.perform(get("/api/box-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boxType.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].boxSize").value(hasItem(DEFAULT_BOX_SIZE.toString())));
    }

    @Test
    @Transactional
    public void getBoxType() throws Exception {
        // Initialize the database
        boxTypeRepository.saveAndFlush(boxType);

        // Get the boxType
        restBoxTypeMockMvc.perform(get("/api/box-types/{id}", boxType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(boxType.getId().intValue()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.boxSize").value(DEFAULT_BOX_SIZE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoxType() throws Exception {
        // Get the boxType
        restBoxTypeMockMvc.perform(get("/api/box-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoxType() throws Exception {
        // Initialize the database
        boxTypeService.save(boxType);

        int databaseSizeBeforeUpdate = boxTypeRepository.findAll().size();

        // Update the boxType
        BoxType updatedBoxType = boxTypeRepository.findOne(boxType.getId());
        updatedBoxType
            .shortName(UPDATED_SHORT_NAME)
            .fullName(UPDATED_FULL_NAME)
            .boxSize(UPDATED_BOX_SIZE);

        restBoxTypeMockMvc.perform(put("/api/box-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBoxType)))
            .andExpect(status().isOk());

        // Validate the BoxType in the database
        List<BoxType> boxTypeList = boxTypeRepository.findAll();
        assertThat(boxTypeList).hasSize(databaseSizeBeforeUpdate);
        BoxType testBoxType = boxTypeList.get(boxTypeList.size() - 1);
        assertThat(testBoxType.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testBoxType.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testBoxType.getBoxSize()).isEqualTo(UPDATED_BOX_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingBoxType() throws Exception {
        int databaseSizeBeforeUpdate = boxTypeRepository.findAll().size();

        // Create the BoxType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBoxTypeMockMvc.perform(put("/api/box-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxType)))
            .andExpect(status().isCreated());

        // Validate the BoxType in the database
        List<BoxType> boxTypeList = boxTypeRepository.findAll();
        assertThat(boxTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBoxType() throws Exception {
        // Initialize the database
        boxTypeService.save(boxType);

        int databaseSizeBeforeDelete = boxTypeRepository.findAll().size();

        // Get the boxType
        restBoxTypeMockMvc.perform(delete("/api/box-types/{id}", boxType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BoxType> boxTypeList = boxTypeRepository.findAll();
        assertThat(boxTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoxType.class);
        BoxType boxType1 = new BoxType();
        boxType1.setId(1L);
        BoxType boxType2 = new BoxType();
        boxType2.setId(boxType1.getId());
        assertThat(boxType1).isEqualTo(boxType2);
        boxType2.setId(2L);
        assertThat(boxType1).isNotEqualTo(boxType2);
        boxType1.setId(null);
        assertThat(boxType1).isNotEqualTo(boxType2);
    }
}
