package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.Pinch;
import com.impltech.repository.PinchRepository;
import com.impltech.service.PinchService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PinchResource REST controller.
 *
 * @see PinchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class PinchResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_TOTAL_STEMS = 1L;
    private static final Long UPDATED_TOTAL_STEMS = 2L;

    private static final LocalDate DEFAULT_NOTIFY_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NOTIFY_START_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PinchRepository pinchRepository;

    @Autowired
    private PinchService pinchService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPinchMockMvc;

    private Pinch pinch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PinchResource pinchResource = new PinchResource(pinchService);
        this.restPinchMockMvc = MockMvcBuilders.standaloneSetup(pinchResource)
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
    public static Pinch createEntity(EntityManager em) {
        Pinch pinch = new Pinch()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .totalStems(DEFAULT_TOTAL_STEMS)
            .notifyStartDate(DEFAULT_NOTIFY_START_DATE);
        return pinch;
    }

    @Before
    public void initTest() {
        pinch = createEntity(em);
    }

    @Test
    @Transactional
    public void createPinch() throws Exception {
        int databaseSizeBeforeCreate = pinchRepository.findAll().size();

        // Create the Pinch
        restPinchMockMvc.perform(post("/api/pinches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinch)))
            .andExpect(status().isCreated());

        // Validate the Pinch in the database
        List<Pinch> pinchList = pinchRepository.findAll();
        assertThat(pinchList).hasSize(databaseSizeBeforeCreate + 1);
        Pinch testPinch = pinchList.get(pinchList.size() - 1);
        assertThat(testPinch.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPinch.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPinch.getTotalStems()).isEqualTo(DEFAULT_TOTAL_STEMS);
        assertThat(testPinch.getNotifyStartDate()).isEqualTo(DEFAULT_NOTIFY_START_DATE);
    }

    @Test
    @Transactional
    public void createPinchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pinchRepository.findAll().size();

        // Create the Pinch with an existing ID
        pinch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPinchMockMvc.perform(post("/api/pinches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinch)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pinch> pinchList = pinchRepository.findAll();
        assertThat(pinchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPinches() throws Exception {
        // Initialize the database
        pinchRepository.saveAndFlush(pinch);

        // Get all the pinchList
        restPinchMockMvc.perform(get("/api/pinches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pinch.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalStems").value(hasItem(DEFAULT_TOTAL_STEMS.intValue())))
            .andExpect(jsonPath("$.[*].notifyStartDate").value(hasItem(DEFAULT_NOTIFY_START_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPinch() throws Exception {
        // Initialize the database
        pinchRepository.saveAndFlush(pinch);

        // Get the pinch
        restPinchMockMvc.perform(get("/api/pinches/{id}", pinch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pinch.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.totalStems").value(DEFAULT_TOTAL_STEMS.intValue()))
            .andExpect(jsonPath("$.notifyStartDate").value(DEFAULT_NOTIFY_START_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPinch() throws Exception {
        // Get the pinch
        restPinchMockMvc.perform(get("/api/pinches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePinch() throws Exception {
        // Initialize the database
        pinchService.save(pinch);

        int databaseSizeBeforeUpdate = pinchRepository.findAll().size();

        // Update the pinch
        Pinch updatedPinch = pinchRepository.findOne(pinch.getId());
        updatedPinch
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .totalStems(UPDATED_TOTAL_STEMS)
            .notifyStartDate(UPDATED_NOTIFY_START_DATE);

        restPinchMockMvc.perform(put("/api/pinches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPinch)))
            .andExpect(status().isOk());

        // Validate the Pinch in the database
        List<Pinch> pinchList = pinchRepository.findAll();
        assertThat(pinchList).hasSize(databaseSizeBeforeUpdate);
        Pinch testPinch = pinchList.get(pinchList.size() - 1);
        assertThat(testPinch.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPinch.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPinch.getTotalStems()).isEqualTo(UPDATED_TOTAL_STEMS);
        assertThat(testPinch.getNotifyStartDate()).isEqualTo(UPDATED_NOTIFY_START_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPinch() throws Exception {
        int databaseSizeBeforeUpdate = pinchRepository.findAll().size();

        // Create the Pinch

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPinchMockMvc.perform(put("/api/pinches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinch)))
            .andExpect(status().isCreated());

        // Validate the Pinch in the database
        List<Pinch> pinchList = pinchRepository.findAll();
        assertThat(pinchList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePinch() throws Exception {
        // Initialize the database
        pinchService.save(pinch);

        int databaseSizeBeforeDelete = pinchRepository.findAll().size();

        // Get the pinch
        restPinchMockMvc.perform(delete("/api/pinches/{id}", pinch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pinch> pinchList = pinchRepository.findAll();
        assertThat(pinchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pinch.class);
        Pinch pinch1 = new Pinch();
        pinch1.setId(1L);
        Pinch pinch2 = new Pinch();
        pinch2.setId(pinch1.getId());
        assertThat(pinch1).isEqualTo(pinch2);
        pinch2.setId(2L);
        assertThat(pinch1).isNotEqualTo(pinch2);
        pinch1.setId(null);
        assertThat(pinch1).isNotEqualTo(pinch2);
    }
}
