package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.ClientEmployeePosition;
import com.impltech.repository.ClientEmployeePositionRepository;
import com.impltech.service.ClientEmployeePositionService;
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
 * Test class for the ClientEmployeePositionResource REST controller.
 *
 * @see ClientEmployeePositionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class ClientEmployeePositionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ClientEmployeePositionRepository clientEmployeePositionRepository;

    @Autowired
    private ClientEmployeePositionService clientEmployeePositionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClientEmployeePositionMockMvc;

    private ClientEmployeePosition clientEmployeePosition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientEmployeePositionResource clientEmployeePositionResource = new ClientEmployeePositionResource(clientEmployeePositionService);
        this.restClientEmployeePositionMockMvc = MockMvcBuilders.standaloneSetup(clientEmployeePositionResource)
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
    public static ClientEmployeePosition createEntity(EntityManager em) {
        ClientEmployeePosition clientEmployeePosition = new ClientEmployeePosition()
            .name(DEFAULT_NAME);
        return clientEmployeePosition;
    }

    @Before
    public void initTest() {
        clientEmployeePosition = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientEmployeePosition() throws Exception {
        int databaseSizeBeforeCreate = clientEmployeePositionRepository.findAll().size();

        // Create the ClientEmployeePosition
        restClientEmployeePositionMockMvc.perform(post("/api/client-employee-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmployeePosition)))
            .andExpect(status().isCreated());

        // Validate the ClientEmployeePosition in the database
        List<ClientEmployeePosition> clientEmployeePositionList = clientEmployeePositionRepository.findAll();
        assertThat(clientEmployeePositionList).hasSize(databaseSizeBeforeCreate + 1);
        ClientEmployeePosition testClientEmployeePosition = clientEmployeePositionList.get(clientEmployeePositionList.size() - 1);
        assertThat(testClientEmployeePosition.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createClientEmployeePositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientEmployeePositionRepository.findAll().size();

        // Create the ClientEmployeePosition with an existing ID
        clientEmployeePosition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientEmployeePositionMockMvc.perform(post("/api/client-employee-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmployeePosition)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ClientEmployeePosition> clientEmployeePositionList = clientEmployeePositionRepository.findAll();
        assertThat(clientEmployeePositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientEmployeePositions() throws Exception {
        // Initialize the database
        clientEmployeePositionRepository.saveAndFlush(clientEmployeePosition);

        // Get all the clientEmployeePositionList
        restClientEmployeePositionMockMvc.perform(get("/api/client-employee-positions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientEmployeePosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getClientEmployeePosition() throws Exception {
        // Initialize the database
        clientEmployeePositionRepository.saveAndFlush(clientEmployeePosition);

        // Get the clientEmployeePosition
        restClientEmployeePositionMockMvc.perform(get("/api/client-employee-positions/{id}", clientEmployeePosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientEmployeePosition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClientEmployeePosition() throws Exception {
        // Get the clientEmployeePosition
        restClientEmployeePositionMockMvc.perform(get("/api/client-employee-positions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientEmployeePosition() throws Exception {
        // Initialize the database
        clientEmployeePositionService.save(clientEmployeePosition);

        int databaseSizeBeforeUpdate = clientEmployeePositionRepository.findAll().size();

        // Update the clientEmployeePosition
        ClientEmployeePosition updatedClientEmployeePosition = clientEmployeePositionRepository.findOne(clientEmployeePosition.getId());
        updatedClientEmployeePosition
            .name(UPDATED_NAME);

        restClientEmployeePositionMockMvc.perform(put("/api/client-employee-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientEmployeePosition)))
            .andExpect(status().isOk());

        // Validate the ClientEmployeePosition in the database
        List<ClientEmployeePosition> clientEmployeePositionList = clientEmployeePositionRepository.findAll();
        assertThat(clientEmployeePositionList).hasSize(databaseSizeBeforeUpdate);
        ClientEmployeePosition testClientEmployeePosition = clientEmployeePositionList.get(clientEmployeePositionList.size() - 1);
        assertThat(testClientEmployeePosition.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingClientEmployeePosition() throws Exception {
        int databaseSizeBeforeUpdate = clientEmployeePositionRepository.findAll().size();

        // Create the ClientEmployeePosition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClientEmployeePositionMockMvc.perform(put("/api/client-employee-positions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientEmployeePosition)))
            .andExpect(status().isCreated());

        // Validate the ClientEmployeePosition in the database
        List<ClientEmployeePosition> clientEmployeePositionList = clientEmployeePositionRepository.findAll();
        assertThat(clientEmployeePositionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClientEmployeePosition() throws Exception {
        // Initialize the database
        clientEmployeePositionService.save(clientEmployeePosition);

        int databaseSizeBeforeDelete = clientEmployeePositionRepository.findAll().size();

        // Get the clientEmployeePosition
        restClientEmployeePositionMockMvc.perform(delete("/api/client-employee-positions/{id}", clientEmployeePosition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientEmployeePosition> clientEmployeePositionList = clientEmployeePositionRepository.findAll();
        assertThat(clientEmployeePositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientEmployeePosition.class);
        ClientEmployeePosition clientEmployeePosition1 = new ClientEmployeePosition();
        clientEmployeePosition1.setId(1L);
        ClientEmployeePosition clientEmployeePosition2 = new ClientEmployeePosition();
        clientEmployeePosition2.setId(clientEmployeePosition1.getId());
        assertThat(clientEmployeePosition1).isEqualTo(clientEmployeePosition2);
        clientEmployeePosition2.setId(2L);
        assertThat(clientEmployeePosition1).isNotEqualTo(clientEmployeePosition2);
        clientEmployeePosition1.setId(null);
        assertThat(clientEmployeePosition1).isNotEqualTo(clientEmployeePosition2);
    }
}
