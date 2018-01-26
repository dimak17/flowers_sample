package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.MarketClient;
import com.impltech.repository.MarketClientRepository;
import com.impltech.service.MarketClientService;
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
 * Test class for the MarketClientResource REST controller.
 *
 * @see MarketClientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class MarketClientResourceIntTest {

    @Autowired
    private MarketClientRepository marketClientRepository;

    @Autowired
    private MarketClientService marketClientService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketClientMockMvc;

    private MarketClient marketClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketClientResource marketClientResource = new MarketClientResource(marketClientService);
        this.restMarketClientMockMvc = MockMvcBuilders.standaloneSetup(marketClientResource)
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
    public static MarketClient createEntity(EntityManager em) {
        MarketClient marketClient = new MarketClient();
        return marketClient;
    }

    @Before
    public void initTest() {
        marketClient = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketClient() throws Exception {
        int databaseSizeBeforeCreate = marketClientRepository.findAll().size();

        // Create the MarketClient
        restMarketClientMockMvc.perform(post("/api/market-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketClient)))
            .andExpect(status().isCreated());

        // Validate the MarketClient in the database
        List<MarketClient> marketClientList = marketClientRepository.findAll();
        assertThat(marketClientList).hasSize(databaseSizeBeforeCreate + 1);
        MarketClient testMarketClient = marketClientList.get(marketClientList.size() - 1);
    }

    @Test
    @Transactional
    public void createMarketClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketClientRepository.findAll().size();

        // Create the MarketClient with an existing ID
        marketClient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketClientMockMvc.perform(post("/api/market-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketClient)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketClient> marketClientList = marketClientRepository.findAll();
        assertThat(marketClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketClients() throws Exception {
        // Initialize the database
        marketClientRepository.saveAndFlush(marketClient);

        // Get all the marketClientList
        restMarketClientMockMvc.perform(get("/api/market-clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketClient.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMarketClient() throws Exception {
        // Initialize the database
        marketClientRepository.saveAndFlush(marketClient);

        // Get the marketClient
        restMarketClientMockMvc.perform(get("/api/market-clients/{id}", marketClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketClient.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketClient() throws Exception {
        // Get the marketClient
        restMarketClientMockMvc.perform(get("/api/market-clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketClient() throws Exception {
        // Initialize the database
        marketClientService.save(marketClient);

        int databaseSizeBeforeUpdate = marketClientRepository.findAll().size();

        // Update the marketClient
        MarketClient updatedMarketClient = marketClientRepository.findOne(marketClient.getId());

        restMarketClientMockMvc.perform(put("/api/market-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketClient)))
            .andExpect(status().isOk());

        // Validate the MarketClient in the database
        List<MarketClient> marketClientList = marketClientRepository.findAll();
        assertThat(marketClientList).hasSize(databaseSizeBeforeUpdate);
        MarketClient testMarketClient = marketClientList.get(marketClientList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketClient() throws Exception {
        int databaseSizeBeforeUpdate = marketClientRepository.findAll().size();

        // Create the MarketClient

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketClientMockMvc.perform(put("/api/market-clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketClient)))
            .andExpect(status().isCreated());

        // Validate the MarketClient in the database
        List<MarketClient> marketClientList = marketClientRepository.findAll();
        assertThat(marketClientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketClient() throws Exception {
        // Initialize the database
        marketClientService.save(marketClient);

        int databaseSizeBeforeDelete = marketClientRepository.findAll().size();

        // Get the marketClient
        restMarketClientMockMvc.perform(delete("/api/market-clients/{id}", marketClient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketClient> marketClientList = marketClientRepository.findAll();
        assertThat(marketClientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketClient.class);
        MarketClient marketClient1 = new MarketClient();
        marketClient1.setId(1L);
        MarketClient marketClient2 = new MarketClient();
        marketClient2.setId(marketClient1.getId());
        assertThat(marketClient1).isEqualTo(marketClient2);
        marketClient2.setId(2L);
        assertThat(marketClient1).isNotEqualTo(marketClient2);
        marketClient1.setId(null);
        assertThat(marketClient1).isNotEqualTo(marketClient2);
    }
}
