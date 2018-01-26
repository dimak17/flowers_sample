package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.PinchVariety;
import com.impltech.repository.PinchVarietyRepository;
import com.impltech.service.PinchVarietyService;
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
 * Test class for the PinchVarietyResource REST controller.
 *
 * @see PinchVarietyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class PinchVarietyResourceIntTest {

    @Autowired
    private PinchVarietyRepository pinchVarietyRepository;

    @Autowired
    private PinchVarietyService pinchVarietyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPinchVarietyMockMvc;

    private PinchVariety pinchVariety;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PinchVarietyResource pinchVarietyResource = new PinchVarietyResource(pinchVarietyService);
        this.restPinchVarietyMockMvc = MockMvcBuilders.standaloneSetup(pinchVarietyResource)
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
    public static PinchVariety createEntity(EntityManager em) {
        PinchVariety pinchVariety = new PinchVariety();
        return pinchVariety;
    }

    @Before
    public void initTest() {
        pinchVariety = createEntity(em);
    }

    @Test
    @Transactional
    public void createPinchVariety() throws Exception {
        int databaseSizeBeforeCreate = pinchVarietyRepository.findAll().size();

        // Create the PinchVariety
        restPinchVarietyMockMvc.perform(post("/api/pinch-varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinchVariety)))
            .andExpect(status().isCreated());

        // Validate the PinchVariety in the database
        List<PinchVariety> pinchVarietyList = pinchVarietyRepository.findAll();
        assertThat(pinchVarietyList).hasSize(databaseSizeBeforeCreate + 1);
        PinchVariety testPinchVariety = pinchVarietyList.get(pinchVarietyList.size() - 1);
    }

    @Test
    @Transactional
    public void createPinchVarietyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pinchVarietyRepository.findAll().size();

        // Create the PinchVariety with an existing ID
        pinchVariety.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPinchVarietyMockMvc.perform(post("/api/pinch-varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinchVariety)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PinchVariety> pinchVarietyList = pinchVarietyRepository.findAll();
        assertThat(pinchVarietyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPinchVarieties() throws Exception {
        // Initialize the database
        pinchVarietyRepository.saveAndFlush(pinchVariety);

        // Get all the pinchVarietyList
        restPinchVarietyMockMvc.perform(get("/api/pinch-varieties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pinchVariety.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPinchVariety() throws Exception {
        // Initialize the database
        pinchVarietyRepository.saveAndFlush(pinchVariety);

        // Get the pinchVariety
        restPinchVarietyMockMvc.perform(get("/api/pinch-varieties/{id}", pinchVariety.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pinchVariety.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPinchVariety() throws Exception {
        // Get the pinchVariety
        restPinchVarietyMockMvc.perform(get("/api/pinch-varieties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePinchVariety() throws Exception {
        // Initialize the database
        pinchVarietyService.save(pinchVariety);

        int databaseSizeBeforeUpdate = pinchVarietyRepository.findAll().size();

        // Update the pinchVariety
        PinchVariety updatedPinchVariety = pinchVarietyRepository.findOne(pinchVariety.getId());

        restPinchVarietyMockMvc.perform(put("/api/pinch-varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPinchVariety)))
            .andExpect(status().isOk());

        // Validate the PinchVariety in the database
        List<PinchVariety> pinchVarietyList = pinchVarietyRepository.findAll();
        assertThat(pinchVarietyList).hasSize(databaseSizeBeforeUpdate);
        PinchVariety testPinchVariety = pinchVarietyList.get(pinchVarietyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPinchVariety() throws Exception {
        int databaseSizeBeforeUpdate = pinchVarietyRepository.findAll().size();

        // Create the PinchVariety

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPinchVarietyMockMvc.perform(put("/api/pinch-varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinchVariety)))
            .andExpect(status().isCreated());

        // Validate the PinchVariety in the database
        List<PinchVariety> pinchVarietyList = pinchVarietyRepository.findAll();
        assertThat(pinchVarietyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePinchVariety() throws Exception {
        // Initialize the database
        pinchVarietyService.save(pinchVariety);

        int databaseSizeBeforeDelete = pinchVarietyRepository.findAll().size();

        // Get the pinchVariety
        restPinchVarietyMockMvc.perform(delete("/api/pinch-varieties/{id}", pinchVariety.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PinchVariety> pinchVarietyList = pinchVarietyRepository.findAll();
        assertThat(pinchVarietyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PinchVariety.class);
        PinchVariety pinchVariety1 = new PinchVariety();
        pinchVariety1.setId(1L);
        PinchVariety pinchVariety2 = new PinchVariety();
        pinchVariety2.setId(pinchVariety1.getId());
        assertThat(pinchVariety1).isEqualTo(pinchVariety2);
        pinchVariety2.setId(2L);
        assertThat(pinchVariety1).isNotEqualTo(pinchVariety2);
        pinchVariety1.setId(null);
        assertThat(pinchVariety1).isNotEqualTo(pinchVariety2);
    }
}
