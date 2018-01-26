package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.PinchVarietyProperty;
import com.impltech.domain.enumeration.Length;
import com.impltech.repository.PinchVarietyPropertyRepository;
import com.impltech.service.PinchVarietyPropertyService;
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
 * Test class for the PinchVarietyPropertyResource REST controller.
 *
 * @see PinchVarietyPropertyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class PinchVarietyPropertyResourceIntTest {

    private static final Length DEFAULT_LENGTH = Length._40;
    private static final Length UPDATED_LENGTH =Length._50;

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    @Autowired
    private PinchVarietyPropertyRepository pinchVarietyPropertyRepository;

    @Autowired
    private PinchVarietyPropertyService pinchVarietyPropertyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPinchVarietyPropertyMockMvc;

    private PinchVarietyProperty pinchVarietyProperty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PinchVarietyPropertyResource pinchVarietyPropertyResource = new PinchVarietyPropertyResource(pinchVarietyPropertyService);
        this.restPinchVarietyPropertyMockMvc = MockMvcBuilders.standaloneSetup(pinchVarietyPropertyResource)
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
    public static PinchVarietyProperty createEntity(EntityManager em) {
        PinchVarietyProperty pinchVarietyProperty = new PinchVarietyProperty()
            .length(DEFAULT_LENGTH)
            .quantity(DEFAULT_QUANTITY);
        return pinchVarietyProperty;
    }

    @Before
    public void initTest() {
        pinchVarietyProperty = createEntity(em);
    }

    @Test
    @Transactional
    public void createPinchVarietyProperty() throws Exception {
        int databaseSizeBeforeCreate = pinchVarietyPropertyRepository.findAll().size();

        // Create the PinchVarietyProperty
        restPinchVarietyPropertyMockMvc.perform(post("/api/pinch-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinchVarietyProperty)))
            .andExpect(status().isCreated());

        // Validate the PinchVarietyProperty in the database
        List<PinchVarietyProperty> pinchVarietyPropertyList = pinchVarietyPropertyRepository.findAll();
        assertThat(pinchVarietyPropertyList).hasSize(databaseSizeBeforeCreate + 1);
        PinchVarietyProperty testPinchVarietyProperty = pinchVarietyPropertyList.get(pinchVarietyPropertyList.size() - 1);
        assertThat(testPinchVarietyProperty.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testPinchVarietyProperty.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createPinchVarietyPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pinchVarietyPropertyRepository.findAll().size();

        // Create the PinchVarietyProperty with an existing ID
        pinchVarietyProperty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPinchVarietyPropertyMockMvc.perform(post("/api/pinch-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinchVarietyProperty)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PinchVarietyProperty> pinchVarietyPropertyList = pinchVarietyPropertyRepository.findAll();
        assertThat(pinchVarietyPropertyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPinchVarietyProperties() throws Exception {
        // Initialize the database
        pinchVarietyPropertyRepository.saveAndFlush(pinchVarietyProperty);

        // Get all the pinchVarietyPropertyList
        restPinchVarietyPropertyMockMvc.perform(get("/api/pinch-variety-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pinchVarietyProperty.getId().intValue())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())));
    }

    @Test
    @Transactional
    public void getPinchVarietyProperty() throws Exception {
        // Initialize the database
        pinchVarietyPropertyRepository.saveAndFlush(pinchVarietyProperty);

        // Get the pinchVarietyProperty
        restPinchVarietyPropertyMockMvc.perform(get("/api/pinch-variety-properties/{id}", pinchVarietyProperty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pinchVarietyProperty.getId().intValue()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPinchVarietyProperty() throws Exception {
        // Get the pinchVarietyProperty
        restPinchVarietyPropertyMockMvc.perform(get("/api/pinch-variety-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePinchVarietyProperty() throws Exception {
        // Initialize the database
        pinchVarietyPropertyService.save(pinchVarietyProperty);

        int databaseSizeBeforeUpdate = pinchVarietyPropertyRepository.findAll().size();

        // Update the pinchVarietyProperty
        PinchVarietyProperty updatedPinchVarietyProperty = pinchVarietyPropertyRepository.findOne(pinchVarietyProperty.getId());
        updatedPinchVarietyProperty
            .length(UPDATED_LENGTH)
            .quantity(UPDATED_QUANTITY);

        restPinchVarietyPropertyMockMvc.perform(put("/api/pinch-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPinchVarietyProperty)))
            .andExpect(status().isOk());

        // Validate the PinchVarietyProperty in the database
        List<PinchVarietyProperty> pinchVarietyPropertyList = pinchVarietyPropertyRepository.findAll();
        assertThat(pinchVarietyPropertyList).hasSize(databaseSizeBeforeUpdate);
        PinchVarietyProperty testPinchVarietyProperty = pinchVarietyPropertyList.get(pinchVarietyPropertyList.size() - 1);
        assertThat(testPinchVarietyProperty.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testPinchVarietyProperty.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingPinchVarietyProperty() throws Exception {
        int databaseSizeBeforeUpdate = pinchVarietyPropertyRepository.findAll().size();

        // Create the PinchVarietyProperty

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPinchVarietyPropertyMockMvc.perform(put("/api/pinch-variety-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinchVarietyProperty)))
            .andExpect(status().isCreated());

        // Validate the PinchVarietyProperty in the database
        List<PinchVarietyProperty> pinchVarietyPropertyList = pinchVarietyPropertyRepository.findAll();
        assertThat(pinchVarietyPropertyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePinchVarietyProperty() throws Exception {
        // Initialize the database
        pinchVarietyPropertyService.save(pinchVarietyProperty);

        int databaseSizeBeforeDelete = pinchVarietyPropertyRepository.findAll().size();

        // Get the pinchVarietyProperty
        restPinchVarietyPropertyMockMvc.perform(delete("/api/pinch-variety-properties/{id}", pinchVarietyProperty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PinchVarietyProperty> pinchVarietyPropertyList = pinchVarietyPropertyRepository.findAll();
        assertThat(pinchVarietyPropertyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PinchVarietyProperty.class);
        PinchVarietyProperty pinchVarietyProperty1 = new PinchVarietyProperty();
        pinchVarietyProperty1.setId(1L);
        PinchVarietyProperty pinchVarietyProperty2 = new PinchVarietyProperty();
        pinchVarietyProperty2.setId(pinchVarietyProperty1.getId());
        assertThat(pinchVarietyProperty1).isEqualTo(pinchVarietyProperty2);
        pinchVarietyProperty2.setId(2L);
        assertThat(pinchVarietyProperty1).isNotEqualTo(pinchVarietyProperty2);
        pinchVarietyProperty1.setId(null);
        assertThat(pinchVarietyProperty1).isNotEqualTo(pinchVarietyProperty2);
    }
}
