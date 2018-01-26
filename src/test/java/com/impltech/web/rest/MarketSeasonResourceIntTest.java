package com.impltech.web.rest;

import com.impltech.FlowersApp;

import com.impltech.domain.MarketSeason;
import com.impltech.repository.MarketSeasonRepository;
import com.impltech.service.MarketSeasonService;
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
 * Test class for the MarketSeasonResource REST controller.
 *
 * @see MarketSeasonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowersApp.class)
public class MarketSeasonResourceIntTest {

    @Autowired
    private MarketSeasonRepository marketSeasonRepository;

    @Autowired
    private MarketSeasonService marketSeasonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarketSeasonMockMvc;

    private MarketSeason marketSeason;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MarketSeasonResource marketSeasonResource = new MarketSeasonResource(marketSeasonService);
        this.restMarketSeasonMockMvc = MockMvcBuilders.standaloneSetup(marketSeasonResource)
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
    public static MarketSeason createEntity(EntityManager em) {
        MarketSeason marketSeason = new MarketSeason();
        return marketSeason;
    }

    @Before
    public void initTest() {
        marketSeason = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarketSeason() throws Exception {
        int databaseSizeBeforeCreate = marketSeasonRepository.findAll().size();

        // Create the MarketSeason
        restMarketSeasonMockMvc.perform(post("/api/market-seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketSeason)))
            .andExpect(status().isCreated());

        // Validate the MarketSeason in the database
        List<MarketSeason> marketSeasonList = marketSeasonRepository.findAll();
        assertThat(marketSeasonList).hasSize(databaseSizeBeforeCreate + 1);
        MarketSeason testMarketSeason = marketSeasonList.get(marketSeasonList.size() - 1);
    }

    @Test
    @Transactional
    public void createMarketSeasonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marketSeasonRepository.findAll().size();

        // Create the MarketSeason with an existing ID
        marketSeason.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarketSeasonMockMvc.perform(post("/api/market-seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketSeason)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MarketSeason> marketSeasonList = marketSeasonRepository.findAll();
        assertThat(marketSeasonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarketSeasons() throws Exception {
        // Initialize the database
        marketSeasonRepository.saveAndFlush(marketSeason);

        // Get all the marketSeasonList
        restMarketSeasonMockMvc.perform(get("/api/market-seasons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marketSeason.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMarketSeason() throws Exception {
        // Initialize the database
        marketSeasonRepository.saveAndFlush(marketSeason);

        // Get the marketSeason
        restMarketSeasonMockMvc.perform(get("/api/market-seasons/{id}", marketSeason.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marketSeason.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMarketSeason() throws Exception {
        // Get the marketSeason
        restMarketSeasonMockMvc.perform(get("/api/market-seasons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarketSeason() throws Exception {
        // Initialize the database
        marketSeasonService.save(marketSeason);

        int databaseSizeBeforeUpdate = marketSeasonRepository.findAll().size();

        // Update the marketSeason
        MarketSeason updatedMarketSeason = marketSeasonRepository.findOne(marketSeason.getId());

        restMarketSeasonMockMvc.perform(put("/api/market-seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarketSeason)))
            .andExpect(status().isOk());

        // Validate the MarketSeason in the database
        List<MarketSeason> marketSeasonList = marketSeasonRepository.findAll();
        assertThat(marketSeasonList).hasSize(databaseSizeBeforeUpdate);
        MarketSeason testMarketSeason = marketSeasonList.get(marketSeasonList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMarketSeason() throws Exception {
        int databaseSizeBeforeUpdate = marketSeasonRepository.findAll().size();

        // Create the MarketSeason

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarketSeasonMockMvc.perform(put("/api/market-seasons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marketSeason)))
            .andExpect(status().isCreated());

        // Validate the MarketSeason in the database
        List<MarketSeason> marketSeasonList = marketSeasonRepository.findAll();
        assertThat(marketSeasonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMarketSeason() throws Exception {
        // Initialize the database
        marketSeasonService.save(marketSeason);

        int databaseSizeBeforeDelete = marketSeasonRepository.findAll().size();

        // Get the marketSeason
        restMarketSeasonMockMvc.perform(delete("/api/market-seasons/{id}", marketSeason.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarketSeason> marketSeasonList = marketSeasonRepository.findAll();
        assertThat(marketSeasonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarketSeason.class);
        MarketSeason marketSeason1 = new MarketSeason();
        marketSeason1.setId(1L);
        MarketSeason marketSeason2 = new MarketSeason();
        marketSeason2.setId(marketSeason1.getId());
        assertThat(marketSeason1).isEqualTo(marketSeason2);
        marketSeason2.setId(2L);
        assertThat(marketSeason1).isNotEqualTo(marketSeason2);
        marketSeason1.setId(null);
        assertThat(marketSeason1).isNotEqualTo(marketSeason2);
    }
}
