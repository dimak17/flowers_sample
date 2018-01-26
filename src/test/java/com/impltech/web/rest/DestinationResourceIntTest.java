package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.Destination;
import com.impltech.repository.DestinationRepository;
import com.impltech.service.DestinationService;
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
 * Test class for the DestinationResource REST controller.
 *
 * @see DestinationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class DestinationResourceIntTest {

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDestinationMockMvc;

    private Destination destination;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DestinationResource destinationResource = new DestinationResource(destinationService);
        this.restDestinationMockMvc = MockMvcBuilders.standaloneSetup(destinationResource)
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
    public static Destination createEntity(EntityManager em) {
        Destination destination = new Destination()
            .city(DEFAULT_CITY)
            .info(DEFAULT_INFO);
        return destination;
    }

    @Before
    public void initTest() {
        destination = createEntity(em);
    }

    @Test
    @Transactional
    public void createDestination() throws Exception {
        int databaseSizeBeforeCreate = destinationRepository.findAll().size();

        // Create the Destination
        restDestinationMockMvc.perform(post("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(destination)))
            .andExpect(status().isCreated());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeCreate + 1);
        Destination testDestination = destinationList.get(destinationList.size() - 1);
        assertThat(testDestination.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDestination.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createDestinationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = destinationRepository.findAll().size();

        // Create the Destination with an existing ID
        destination.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDestinationMockMvc.perform(post("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(destination)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDestinations() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        // Get all the destinationList
        restDestinationMockMvc.perform(get("/api/destinations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(destination.getId().intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())));
    }

    @Test
    @Transactional
    public void getDestination() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        // Get the destination
        restDestinationMockMvc.perform(get("/api/destinations/{id}", destination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(destination.getId().intValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDestination() throws Exception {
        // Get the destination
        restDestinationMockMvc.perform(get("/api/destinations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDestination() throws Exception {
        // Initialize the database
        destinationService.save(destination);

        int databaseSizeBeforeUpdate = destinationRepository.findAll().size();

        // Update the destination
        Destination updatedDestination = destinationRepository.findOne(destination.getId());
        updatedDestination
            .city(UPDATED_CITY)
            .info(UPDATED_INFO);

        restDestinationMockMvc.perform(put("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDestination)))
            .andExpect(status().isOk());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeUpdate);
        Destination testDestination = destinationList.get(destinationList.size() - 1);
        assertThat(testDestination.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDestination.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingDestination() throws Exception {
        int databaseSizeBeforeUpdate = destinationRepository.findAll().size();

        // Create the Destination

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDestinationMockMvc.perform(put("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(destination)))
            .andExpect(status().isCreated());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDestination() throws Exception {
        // Initialize the database
        destinationService.save(destination);

        int databaseSizeBeforeDelete = destinationRepository.findAll().size();

        // Get the destination
        restDestinationMockMvc.perform(delete("/api/destinations/{id}", destination.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Destination.class);
        Destination destination1 = new Destination();
        destination1.setId(1L);
        Destination destination2 = new Destination();
        destination2.setId(destination1.getId());
        assertThat(destination1).isEqualTo(destination2);
        destination2.setId(2L);
        assertThat(destination1).isNotEqualTo(destination2);
        destination1.setId(null);
        assertThat(destination1).isNotEqualTo(destination2);
    }
}
